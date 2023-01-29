package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.request.ReportRequestDto;
import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.NestedComment;
import com.example.umc3_teamproject.domain.item.Report;
import com.example.umc3_teamproject.domain.item.ReportType;
import com.example.umc3_teamproject.exception.CustomException;
import com.example.umc3_teamproject.exception.ErrorCode;
import com.example.umc3_teamproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;
    private final ForumRepository forumRepository;
    private final CommentRepository commentRepository;
    private final NestedCommentRepository nestedCommentRepository;

    @Transactional
    public ResponseTemplate<String> createReport(String isSuccessEmail,ReportRequestDto.createReport request){
        Report report = new Report();
        Member member = memberRepository.getUser(request.getReporter_id());
        report.createReport(request.getTitle(), request.getContent(),request.getReportType(),member);

        if(request.getReportType().equals(ReportType.Forum)){
            report.setForum(forumRepository.findOne(request.getReport_type_id()));
        }else if(request.getReportType().equals(ReportType.Comment)){
            Comment findComment = commentRepository.findById(request.getReport_type_id()).orElseThrow(
                    () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
            );
            report.setComment(findComment);
        }else if(request.getReportType().equals(ReportType.NestedComment)){
            NestedComment findNestedComment = nestedCommentRepository.findById(request.getReport_type_id()).orElseThrow(
                    () -> new CustomException(ErrorCode.NESTED_COMMENT_NOT_FOUND)
            );
            report.setNestedComment(findNestedComment);
        }else{
            throw new CustomException(ErrorCode.REPORT_TYPE_ERROR);
        }

        reportRepository.save(report);

        return new ResponseTemplate<>(isSuccessEmail + " /" + "신고 완료");
    }

}
