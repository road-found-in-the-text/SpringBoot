package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.dto.ForumSearchByUserId;
import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.ScriptIdsToRequest;
import com.example.umc3_teamproject.domain.dto.request.createForumRequest;
import com.example.umc3_teamproject.domain.dto.request.updateForumRequest;
import com.example.umc3_teamproject.domain.dto.response.ForumDataToGetResult;
import com.example.umc3_teamproject.domain.dto.response.LikeResponseDto;
import com.example.umc3_teamproject.domain.dto.response.createForumResponse;
import com.example.umc3_teamproject.domain.item.*;
import com.example.umc3_teamproject.exception.NoScriptId;
import com.example.umc3_teamproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Forum> findAllById(ForumSearchByUserId forumSearchByUserId){return forumRepository.findAllByUserId(forumSearchByUserId);}


    // forum create
    @Transactional
    public createForumResponse createForum(Long user_id, createForumRequest request)throws IOException{
        // jwt 사용할 시에는 User user = userUtil.findCurrent(); 이런 식으로 처리해서
        // httpheader에 access 토큰을 받아서 사용하니까 request body에 id를 받아올 필요 없음
        Forum forum = new Forum();
        Member findMember = memberService.findById(user_id);
        List<ScriptIdsToRequest> scriptIdToRequests = new ArrayList<>();
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
        return new createForumResponse(forum.getId(),forum.getTitle(),forum.getContent(),
                scriptIdToRequests,postImages);
    }

    @Transactional
    public List<String> uploadImageToForum(createForumRequest postRequest, Forum forum)  {
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

    private List<ScriptIdsToRequest> uploadScriptToForum(createForumRequest request, Forum forum) {
        forum.setScript_status_true();

        List<ForumScript> scripts = request.getScriptIds().stream()
                .map(ids -> {try {
                    return scriptRepository.findById(ids.getScript_id()).get();
                }catch (RuntimeException runtimeException){
                    throw new NoScriptId("아이디 " + ids.getScript_id()+"의 script가 존재하지 않습니다.",runtimeException);
                }
                })

                .map(script -> createForumScript(forum,script)).collect(Collectors.toList());

        return scripts.stream()
                .map(script_one -> {
                    try{
                        return new ScriptIdsToRequest(script_one.getScript().getScriptId());
                    }catch (RuntimeException runtimeException){
                        throw  new NoScriptId("아이디 " + script_one.getId()+"의 script가 존재하지 않습니다.");
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

    // forum update
    public GetResult updateForumResult(Long user_id, Long forum_id, createForumRequest request){
        Forum updateForum = updateForum(forum_id, request);
        forumRepository.save(updateForum);
        ForumDataToGetResult forumDataToGetResult = new ForumDataToGetResult(updateForum.getMember().getId(),
                updateForum.getId(),updateForum.getTitle(),updateForum.getContent(),updateForum.getLike_num(),
                request.getScriptIds(),
                updateForum.getForumImages().stream().map(
                        image -> image.getImageUrl()
                ).collect(Collectors.toList()));
        return new GetResult(1,forumDataToGetResult);
    }

    @Transactional
    public Forum updateForum(Long forum_id, createForumRequest request){
        // User user = userUtill.findCurrent();
        Forum findForum = findOne(forum_id);
        forumScriptRepository.deleteAllByIdInQuery(findForum.getForumScripts().stream().map(
                id -> id.getId()
        ).collect(Collectors.toList()));

        List<ScriptIdsToRequest> scriptIdToRequests = new ArrayList<>();
        if(request.getScriptIds() != null && !request.getScriptIds().isEmpty()){
            scriptIdToRequests = uploadScriptToForum(request,findForum);
        }
        forumImageRepository.deleteAllByIdInQuery(findForum.getForumImages().stream().map(
                id -> id.getId()
        ).collect(Collectors.toList()));

        List<String> postImages = new ArrayList<>();
        if(request.getImageFiles() != null && !request.getImageFiles().isEmpty()){
            postImages = uploadImageToForum(request, findForum) ;
        }
        findForum.updateForum(request.getTitle(),request.getContent());
        return findForum;
    }

    private void validateDeletedImages(updateForumRequest request) {

    }

    // forum delete
    @Transactional
    public String deleteForum(Long forum_id){
        Forum findForum = forumRepository.findOne(forum_id);
        findForum.changeDeleted(true);
        findForum.getForumScripts().stream()
                .forEach(script -> script.deleteScript());
        return "삭제 성공";
    }

    // forum read

    // forum 모두 조회
    public GetResult getForumAll(){
        List<Forum> forums= findAll();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // user_id로 조회
    public GetResult getForumByUserId(Long user_id){
        List<Forum> forums= findAllById(new ForumSearchByUserId(user_id));
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    //forum_id로 조회
    public GetResult getForumByForumId(Long forum_id){
        Forum forum= forumRepository.findOne(forum_id);
        ForumDataToGetResult forumDataToGetResult = new ForumDataToGetResult(
                forum.getMember().getId(),
                forum.getId(),
                forum.getTitle(),
                forum.getContent(),
                forum.getLike_num(),
                forum.getForumScripts().stream().map(
                        i -> new ScriptIdsToRequest(i.getScript().getScriptId())
                ).collect(Collectors.toList()),
                forum.getForumImages().stream().map(
                        i -> i.getImageUrl()
                ).collect(Collectors.toList()));
        return new GetResult(1, forumDataToGetResult);



    }

    // path parameter를 받아와서 type 조회
    public GetResult getForumByType(String type){
        List<Forum> forums= forumRepository.findAllByType(type);
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // sciprt가 들어있는 forum 조회
    public GetResult getForumByScript(){
        List<Forum> forums= forumRepository.findAllByScript();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // interview가 있는 forum 조회
    public GetResult getForumByInterview(){
        List<Forum> forums= forumRepository.findAllByInterview();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // script와 interview가 없는 forum 조회
    public GetResult getForum_No_script_No_interview(){
        List<Forum> forums= forumRepository.findAllByFree();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // title로 forum 검색
    public GetResult SearchAllByKeyword(String search_keyword){
        List<Forum> searchedForum= forumRepository.SearchAllByKeyword(search_keyword);
        List<ForumDataToGetResult> forumDataToGetResultRespons = searchedForum.stream().map(
                        s -> new ForumDataToGetResult(s.getMember().getId(),s.getId(),s.getTitle(),s.getContent(),s.getLike_num(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getScriptId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    @Transactional
    public LikeResponseDto likePlus(Long forum_id){
        Forum findForum = forumRepository.findOne(forum_id);
        findForum.likePlus();
        return new LikeResponseDto(findForum.getId(),findForum.getLike_num());
    }

    public LikeResponseDto getLike(Long forum_id) {
        Forum findForum = forumRepository.findOne(forum_id);
        return new LikeResponseDto(findForum.getId(),findForum.getLike_num());
    }

    @Transactional
    public LikeResponseDto likeMinus(Long forum_id) {
        Forum findForum = forumRepository.findOne(forum_id);
        findForum.likeMinus();
        return new LikeResponseDto(findForum.getId(),findForum.getLike_num());
    }
}
