package com.example.umc3_teamproject.Forum.service;

import com.example.umc3_teamproject.Forum.domain.Forum;
import com.example.umc3_teamproject.Forum.domain.ForumImage;
import com.example.umc3_teamproject.Forum.domain.ForumScript;
import com.example.umc3_teamproject.Forum.repository.*;
import com.example.umc3_teamproject.Forum.repository.Dto.ForumSearchById;
import com.example.umc3_teamproject.Forum.repository.Dto.request.ScriptIdsToRequest;
import com.example.umc3_teamproject.domain.*;
import com.example.umc3_teamproject.Forum.repository.Dto.request.createForumRequest;
import com.example.umc3_teamproject.Forum.repository.Dto.request.updateForumRequest;
import com.example.umc3_teamproject.Forum.repository.Dto.response.createForumResponse;
import com.example.umc3_teamproject.service.ScriptService;
import com.example.umc3_teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final ScriptService scriptService;

    private final ForumScriptRepository forumScriptRepository;

    private final UserService userService;

    private final ForumImageRepository forumImageRepository;

    private final S3Uploader s3Uploader;

//    private final FileUploadService fileUploadService;

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
    public List<Forum> findAllById(ForumSearchById forumSearchById){return forumRepository.findAllByUserId(forumSearchById);}


    // forum 생성
    @Transactional
    public createForumResponse createForum(Long user_id,createForumRequest request)throws IOException{
        // jwt 사용할 시에는 User user = userUtil.findCurrent(); 이런 식으로 처리해서
        // httpheader에 access 토큰을 받아서 사용하니까 request body에 id를 받아올 필요 없음
        Forum forum = new Forum();
        User findUser = userService.findOne(user_id);
        List<ScriptIdsToRequest> scriptIdToRequests = new ArrayList<>();
        List<String> postImages = new ArrayList<>();
        forum.createForum(findUser,request.getTitle(), request.getContent());

        // script가 있다면 실행
        if(request.getScriptIdToRequests() != null && !request.getScriptIdToRequests().isEmpty()){
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

    private List<String> uploadImageToForum(createForumRequest postRequest, Forum forum)  {
        return postRequest.getImageFiles().stream()
                .map(image -> {
                    try {
                        return s3Uploader.upload(image, "forumImage");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(url -> createForumImage(forum, url))
                .map(forumImage -> forumImage.getImageUrl())
                .collect(Collectors.toList());
    }

    private ForumImage createForumImage(Forum forum, String url) {
        return forumImageRepository.save(ForumImage.builder()
                .imageUrl(url)
                .storeFilename(StringUtils.getFilename(url))
                .forum(forum)
                .build());
    }

    private List<ScriptIdsToRequest> uploadScriptToForum(createForumRequest request, Forum forum) {
        forum.setScript_status_true();
        List<ForumScript> scripts = request.getScriptIdToRequests().stream()
                .map(ids -> scriptService.findOne(ids.getScript_id()))
                .map(script -> createForumScript(forum,script)).collect(Collectors.toList());

        return scripts.stream()
                .map(script_one -> new ScriptIdsToRequest(script_one.getScript().getId()))
                .collect(Collectors.toList());
    }


    private ForumScript createForumScript(Forum forum, Script script) {
        return forumScriptRepository.save(ForumScript.builder()
                .script(script)
                .forum(forum)
                .build());
    }

    // update 수정
    @Transactional
    public Forum updateForum(Long forum_id,updateForumRequest request){
        // User user = userUtill.findCurrent();
        Forum findForum = findOne(forum_id);
        findForum.getForumScripts().removeAll(findForum.getForumScripts());
        validateDeletedImages(request);

        List<Script> scripts = new ArrayList<>();
        for(ScriptIdsToRequest script_id: request.getScriptIdToRequests()){
            Script script = scriptService.findOne(script_id.getScript_id());
            scripts.add(script);
        }
        findForum.updateForum(request.getTitle(),request.getContent(),scripts);
        return findForum;
    }

    private void validateDeletedImages(updateForumRequest request) {

    }

    // forum 삭제
    @Transactional
    public String deleteForum(Long forum_id){
        Forum findForum = forumRepository.findOne(forum_id);
        findForum.changeDeleted(true);
        findForum.getForumScripts().stream()
                .forEach(script -> script.deleteScript());
        return "삭제 성공";
    }

    // sciprt가 들어있는 forum 조회
    public List<Forum> findAllByScript(){
        return forumRepository.findAllByScript();
    }

    // interview가 있는 forum 조회
    public List<Forum> findAllByInterview() {
        return forumRepository.findAllByInterview();
    }

    // script와 interview가 없는 forum 조회
    public List<Forum> findAllByFree() {
        return forumRepository.findAllByFree();
    }
}
