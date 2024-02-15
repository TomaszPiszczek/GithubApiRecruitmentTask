package com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch;

import lombok.Data;

@Data
public class Branch {
   private String name;

   private Commit commit;
}
