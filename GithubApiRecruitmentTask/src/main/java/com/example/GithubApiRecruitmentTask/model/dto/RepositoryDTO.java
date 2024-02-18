package com.example.GithubApiRecruitmentTask.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

@JsonPropertyOrder({ "repository_name", "owner_login", "branchList" })
public record RepositoryDTO(
        @JsonProperty("repository_name") String repositoryName,
        @JsonProperty("owner_login") String ownerLogin,
        Set<BranchDTO> branchList
) {
}