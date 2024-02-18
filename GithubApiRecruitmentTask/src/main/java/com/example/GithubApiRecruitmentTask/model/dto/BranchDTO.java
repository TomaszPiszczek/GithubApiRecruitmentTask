package com.example.GithubApiRecruitmentTask.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchDTO(String name, @JsonProperty("last_commit_sha") String lastCommitSHA) {
}
