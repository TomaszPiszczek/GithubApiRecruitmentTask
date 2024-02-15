package com.example.GithubApiRecruitmentTask.controller;

import com.example.GithubApiRecruitmentTask.adapter.RepositoryService;
import com.example.GithubApiRecruitmentTask.exception.RequestHeaderException;
import com.example.GithubApiRecruitmentTask.model.dto.RepositoryDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class RepositoryController {
    RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<Set<RepositoryDTO>> getRepositories(
            @PathVariable String username,
            @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader
    ) {
        if (MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)) {
            return ResponseEntity.ok(repositoryService.getRepositories(username));
        } else {
            throw new RequestHeaderException("Unsupported media type. Please specify Accept: application/json in your request header.");
        }
    }
}