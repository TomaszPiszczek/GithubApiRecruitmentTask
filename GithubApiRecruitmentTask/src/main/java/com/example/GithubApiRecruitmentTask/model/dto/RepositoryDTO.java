package com.example.GithubApiRecruitmentTask.model.dto;

import lombok.Data;

import java.util.Set;
@Data
public class RepositoryDTO {

    private String repositoryName;
    private String ownerLogin;
    private Set<BranchDTO> branchList;

}
