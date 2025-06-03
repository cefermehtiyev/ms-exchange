package com.example.msexchange.model.repsone.coin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {
    private String timestamp;
    private int error_code;
    private String error_message;
    private int elapsed;
    private int credit_count;
    private String notice;
    private int total_count;
}