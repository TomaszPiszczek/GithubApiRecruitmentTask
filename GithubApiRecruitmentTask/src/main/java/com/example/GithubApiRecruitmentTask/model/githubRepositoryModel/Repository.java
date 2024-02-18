package com.example.GithubApiRecruitmentTask.model.githubRepositoryModel;

import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Branch;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Repository {
    @SerializedName("name")
    private String name;
    private boolean fork;
    private Owner owner;
    private Set<Branch> branchList;
}