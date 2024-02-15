package com.example.GithubApiRecruitmentTask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private final String status;
    private final String message;

}

