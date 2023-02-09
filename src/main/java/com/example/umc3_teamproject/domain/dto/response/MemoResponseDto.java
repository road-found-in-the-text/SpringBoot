package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.Memo;
import com.example.umc3_teamproject.domain.item.Script;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class MemoResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createBody {
        private int result_count;
        private String memo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateBody {
        private Long memo_id;
        private String memo;
    }
}
