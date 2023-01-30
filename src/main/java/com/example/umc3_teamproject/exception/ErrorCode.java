package com.example.umc3_teamproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    Member_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 user, 찾을 수 없습니다."),
    Forum_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 forum을 찾을 수 없습니다."),

    SCRIPT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 script를 찾을 수 없습니다."),

    INTERVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 interview를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 댓글이 찾을 수 없습니다."),
    NESTED_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 대댓글을 찾을 수 없습니다."),

    INTERVIEW_PARAGRAPH_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 interview 질문을 찾을 수 없습니다."),


    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),

    FIREBASE_INITIAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "firebase 초기화에 실패하였습니다."),

    REPORT_TYPE_ERROR(HttpStatus.NO_CONTENT, "Report 타입은 Forum, Comment, NestedComment 세개 입니다."),

    ;


    private final HttpStatus httpStatus;
    private final String message;
}
