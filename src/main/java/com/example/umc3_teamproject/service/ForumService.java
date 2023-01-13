package com.example.umc3_teamproject.service;



import com.example.umc3_teamproject.config.SecurityConfig;
import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
import com.example.umc3_teamproject.domain.item.*;
import com.example.umc3_teamproject.exception.MemberNotFoundException;
import com.example.umc3_teamproject.exception.ScriptNotFoundException;
import com.example.umc3_teamproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
//    private final ScriptService scriptService;

    private final ScriptRepository scriptRepository;

    private final ForumScriptRepository forumScriptRepository;

    private final MemberService memberService;

    private final ForumImageRepository forumImageRepository;

    private final S3Uploader s3Uploader;

    private final CommentRepository commentRepository;

    private final JwtService jwtService;

    // forum 생성
    @Transactional
    public Long join(Forum forum){
        forumRepository.save(forum);
        return forum.getId();
    }

    //forum 하나 조회
    public Forum findOne(Long id){
        return forumRepository.findOne(id);
    }

    //모든 forum 조회
    public List<Forum> findAll(){
        return forumRepository.findAll();
    }

    // 유저로 조회
    public List<Forum> findAllById(ForumResponseDto.ForumSearchByUserId forumSearchByUserId){return forumRepository.findAllByUserId(forumSearchByUserId);}

    // forum create
    @Transactional
    public ResponseTemplate<ForumResponseDto.ForumDataToGetResult> createForum(Long user_id, ForumRequestDto.createForumRequest request) throws IOException, ResponseException {
        // jwt 사용할 시에는 User user = userUtil.findCurrent(); 이런 식으로 처리해서
        // httpheader에 access 토큰을 받아서 사용하니까 request body에 id를 받아올 필요 없음
        Forum forum = new Forum();
        Member findMember = memberService.findById(user_id);
        List<ForumRequestDto.ScriptIdsToRequest> scriptIdToRequests = new ArrayList<>();
        List<String> postImages = new ArrayList<>();
        forum.createForum(findMember,request.getTitle(), request.getContent());

        // script가 있다면 실행
        if(request.getScriptIds() != null && !request.getScriptIds().isEmpty()){
            scriptIdToRequests = uploadScriptToForum(request,forum);
        }

        // 이미지가 있다면 실행
        if(request.getImageFiles() != null && !request.getImageFiles().isEmpty()){
            postImages = uploadImageToForum(request, forum) ;
        }

        forumRepository.save(forum);
        return new ResponseTemplate<>(new ForumResponseDto.ForumDataToGetResult(forum.getMember().getId(),forum.getId(),
                forum.getTitle(),forum.getContent(),forum.getLike_num(),
                scriptIdToRequests,postImages));
//                new createForumResponse(forum.getId(),forum.getTitle(),forum.getContent(),
//                scriptIdToRequests,postImages);
    }

    @Transactional
    public List<String> uploadImageToForum(ForumRequestDto.createForumRequest postRequest, Forum forum)  {
        forum.setImage_video_status_true();
        return postRequest.getImageFiles().stream()
                .map(image -> {
                    try {
                        return s3Uploader.upload(image, "forumImage");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(url -> {
                    try {
                        return createForumImage(forum, url);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(forumImage -> forumImage.getImageUrl())
                .collect(Collectors.toList());
    }

    @Transactional
    public ForumImage createForumImage(Forum forum, String url) throws UnsupportedEncodingException {
        return forumImageRepository.save(ForumImage.builder()
                .imageUrl(url)
                .storeFilename(StringUtils.getFilename(url))
                .forum(forum)
                .build());
    }

    private List<ForumRequestDto.ScriptIdsToRequest> uploadScriptToForum(ForumRequestDto.createForumRequest request, Forum forum) {
        forum.setScript_status_true();
        List<ForumScript> scripts = request.getScriptIds().stream()
                .map(ids ->
                    scriptRepository.findById(ids.getScript_id()).orElseThrow(
                            () ->  new ScriptNotFoundException("아이디 " + ids.getScript_id()+"의 script가 존재하지 않습니다.")
                    )
                )
                .map(script -> createForumScript(forum,script)).collect(Collectors.toList());

        return scripts.stream()
                .map(script_one -> {
                    try{
                        return new ForumRequestDto.ScriptIdsToRequest(script_one.getScript().getScriptId());
                    }catch (RuntimeException runtimeException){
                        throw  new ScriptNotFoundException("아이디 " + script_one.getId()+"의 script가 존재하지 않습니다.");
                    }
                })
                .collect(Collectors.toList());
    }

    private ForumScript createForumScript(Forum forum, Script script) {
        return forumScriptRepository.save(ForumScript.builder()
                .script(script)
                .forum(forum)
                .build());
    }

    @Transactional
    public ResponseTemplate<ForumResponseDto.ForumDataToGetResult>  updateForum(Long forum_id, ForumRequestDto.updateForumRequest request){
        // User user = userUtill.findCurrent();
        Forum findForum = findOne(forum_id);
        if(request.getTitle() == null){
            request.setTitle(findForum.getTitle());
        }

        if(request.getContent() == null){
            request.setContent(findForum.getContent());
        }


        forumScriptRepository.deleteAllByIdInQuery(findForum.getForumScripts().stream().map(
                id -> id.getId()
        ).collect(Collectors.toList()));

        ForumRequestDto.createForumRequest createForumRequest = new ForumRequestDto.createForumRequest(
                request.getTitle(),
                request.getContent(),
                request.getScriptIds(),
                request.getImageFiles()
        );

        // 기존에 있던 script id들을 전부 delete하고 수정 다 끝나고 최종적으로 남아 있는 script id들을 모두 받아서 다시 insert 한다.
        List<ForumRequestDto.ScriptIdsToRequest> scriptIdToRequests = new ArrayList<>();

        if(request.getScriptIds() != null && !request.getScriptIds().isEmpty()){
            scriptIdToRequests = uploadScriptToForum(createForumRequest,findForum);
        }else{
            findForum.setScript_status_false();
        }

        // 기존에 게시물에 있던 이미지들 중 삭제 다 하고 남아 있는 이미지 url을 제외한 모든 이미지를 삭제한다.
        validateDeleteImages(forum_id,request);

        // 새로 들어온 이미지 파일을 insert한다.
        List<String> postImages = new ArrayList<>();
        if(request.getImageFiles() != null && !request.getImageFiles().isEmpty()){
            postImages = uploadImageToForum(createForumRequest, findForum) ;
        }else{
            if(request.getImageFiles() != null && !request.getImageFiles().isEmpty() ){
            }else{
                findForum.setImage_video_status_false();
            }
        }

        findForum.updateForum(request.getTitle(),request.getContent());
        findForum = forumRepository.save(findForum);
        ForumResponseDto.ForumDataToGetResult forumDataToGetResult = new ForumResponseDto.ForumDataToGetResult(findForum.getMember().getId(),
                findForum.getId(),findForum.getTitle(),findForum.getContent(),findForum.getLike_num(),
                request.getScriptIds(),
                findForum.getForumImages().stream().map(
                        forumImage -> forumImage.getImageUrl()
                ).collect(Collectors.toList()));
        return new ResponseTemplate<>(forumDataToGetResult);
//        return findForum;
    }

    // 여러 파일을 삭제할 수 있도록 만들어야 한다.
    private void validateDeleteImages(Long forum_id,ForumRequestDto.updateForumRequest request) {
        forumImageRepository.findAllByForumId(forum_id).stream()
                .filter(image -> !request.getSaveImageUrls().stream().anyMatch(Predicate.isEqual(image.getImageUrl())))
                .forEach(url -> {
                    forumImageRepository.deleteByIdInQuery(url.getId());
                });
    }

    // forum delete
    @Transactional
    public String deleteForum(Long forum_id){
        Forum findForum = forumRepository.findOne(forum_id);
        findForum.changeDeleted(true);
        findForum.getForumScripts().stream()
                .forEach(script -> script.deleteScript());
        commentRepository.deleteAllByForumId(forum_id);
        return "삭제 성공";
    }

    // forum read

    // forum 모두 조회
    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumAll(){
        List<Forum> forums= findAll();
        List<ForumResponseDto.ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumResponseDto.ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(forumDataToGetResultRespons);
    }

    // user_id로 조회
    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumByUserId(Long user_id){
        Member member = memberService.findById(user_id);
        if(member == null){
            throw new MemberNotFoundException("해당 유저는 존재하지 않습니다.");
        }
        List<Forum> forums= findAllById(new ForumResponseDto.ForumSearchByUserId(user_id));
        List<ForumResponseDto.ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumResponseDto.ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(forumDataToGetResultRespons);
//                new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    //forum_id로 조회
    public ResponseTemplate<ForumResponseDto.ForumDataToGetResult> getForumByForumId(Long forum_id){
        Forum forum= forumRepository.findOne(forum_id);
        ForumResponseDto.ForumDataToGetResult forumDataToGetResult = new ForumResponseDto.ForumDataToGetResult(
                forum.getMember().getId(),
                forum.getId(),
                forum.getTitle(),
                forum.getContent(),
                forum.getLike_num(),
                forum.getForumScripts().stream().map(
                        i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())
                ).collect(Collectors.toList()),
                forum.getForumImages().stream().map(
                        i -> i.getImageUrl()
                ).collect(Collectors.toList()));
        return new ResponseTemplate<>(forumDataToGetResult);
//                new GetResult(1, forumDataToGetResult);


    }

    // path parameter를 받아와서 type 조회
//    public ResponseEntity<?> getForumByType(String type){
//        List<Forum> forums= forumRepository.findAllByType(type);
//        List<ForumResponseDto.ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
//                        s -> new ForumResponseDto.ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
//                                s.getForumScripts().stream().map(i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
//                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons));
////                new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
//    }

    // sciprt가 들어있는 forum 조회
    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumByScript(){
        List<Forum> forums= forumRepository.findAllByScript();
        List<ForumResponseDto.ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumResponseDto.ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(forumDataToGetResultRespons);
//                new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // interview가 있는 forum 조회
    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumByInterview(){
        List<Forum> forums= forumRepository.findAllByInterview();
        List<ForumResponseDto.ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumResponseDto.ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(forumDataToGetResultRespons);
//                new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // script와 interview가 없는 forum 조회
    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForum_No_script_No_interview(){
        List<Forum> forums= forumRepository.findAllByFree();
        List<ForumResponseDto.ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumResponseDto.ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(forumDataToGetResultRespons);
//                new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // title로 forum 검색


    @Transactional
    public ResponseTemplate<ForumResponseDto.LikeResponseDto> likePlus(Long forum_id){
        Forum findForum = forumRepository.findOne(forum_id);
        findForum.likePlus();
        return new ResponseTemplate<>(new ForumResponseDto.LikeResponseDto(findForum.getId(),findForum.getLike_num())) ;
//                new LikeResponseDto(findForum.getId(),findForum.getLike_num());
    }

    public ResponseTemplate<ForumResponseDto.LikeResponseDto> getLike(Long forum_id) {
        Forum findForum = forumRepository.findOne(forum_id);
        return new ResponseTemplate<>(new ForumResponseDto.LikeResponseDto(findForum.getId(),findForum.getLike_num()));
//                new LikeResponseDto(findForum.getId(),findForum.getLike_num());
    }

    @Transactional
    public ResponseTemplate<ForumResponseDto.LikeResponseDto> likeMinus(Long forum_id) {
        Forum findForum = forumRepository.findOne(forum_id);
        findForum.likeMinus();
        return new ResponseTemplate<>(new ForumResponseDto.LikeResponseDto(findForum.getId(),findForum.getLike_num()));
//                new LikeResponseDto(findForum.getId(),findForum.getLike_num());
    }

    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> SearchAllByKeyword(String search_keyword){
        List<Forum> searchedForum= forumRepository.SearchAllByKeyword(search_keyword);
        List<ForumResponseDto.ForumDataToGetResult> forumDataToGetResultRespons = searchedForum.stream().map(
                        s -> new ForumResponseDto.ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ForumRequestDto.ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(forumDataToGetResultRespons);
//                new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }
}
