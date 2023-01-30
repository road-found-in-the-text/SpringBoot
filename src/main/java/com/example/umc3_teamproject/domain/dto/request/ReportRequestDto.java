package com.example.umc3_teamproject.domain.dto.request;

import com.example.umc3_teamproject.domain.item.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReportRequestDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createReport {
        private Long reporter_id;
        private Long report_type_id;
        private ReportType reportType;
        private String title;
        private String content;
    }
}