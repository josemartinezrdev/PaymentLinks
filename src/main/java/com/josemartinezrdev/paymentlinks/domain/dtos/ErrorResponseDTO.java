package com.josemartinezrdev.paymentlinks.domain.dtos;

import java.util.Map;

public class ErrorResponseDTO {

    private String type;
    private String title;
    private int status;
    private String detail;
    private String code;
    private Map<String, Object> errors;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String type, String title, int status, String detail, String code,
            Map<String, Object> errors) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.code = code;
        this.errors = errors;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getErrors() {
        return this.errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

}
