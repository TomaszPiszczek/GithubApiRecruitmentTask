package com.example.GithubApiRecruitmentTask.mapper;


import com.example.GithubApiRecruitmentTask.model.dto.BranchDTO;
import com.example.GithubApiRecruitmentTask.model.dto.RepositoryDTO;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.Repository;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Branch;

import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryMapper {

    public static RepositoryDTO mapToRepositoryDTO(Repository repository) {
        return new RepositoryDTO(repository.getName(), repository.getOwner().login(), mapToBranchDTOList(repository.getBranchList()));
    }

    public static Set<BranchDTO> mapToBranchDTOList(Set<Branch> branches) {
        return branches.stream().map(RepositoryMapper::mapToBranchDTO).collect(Collectors.toSet());
    }

    public static BranchDTO mapToBranchDTO(Branch branch) {
        return new BranchDTO(branch.name(), branch.commit().sha());
    }
}