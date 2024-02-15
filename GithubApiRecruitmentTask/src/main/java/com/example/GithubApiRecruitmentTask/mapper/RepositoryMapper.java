package com.example.GithubApiRecruitmentTask.mapper;


import com.example.GithubApiRecruitmentTask.model.dto.BranchDTO;
import com.example.GithubApiRecruitmentTask.model.dto.RepositoryDTO;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.Repository;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Branch;

import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryMapper {

    public static RepositoryDTO mapToRepositoryDTO(Repository repository) {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setRepositoryName(repository.getName());
        repositoryDTO.setOwnerLogin(repository.getOwner().getLogin());
        repositoryDTO.setBranchList(mapToBranchDTOList(repository.getBranchList()));
        return repositoryDTO;
    }

    public static Set<BranchDTO> mapToBranchDTOList(Set<Branch> branches) {
        return branches.stream()
                .map(RepositoryMapper::mapToBranchDTO)
                .collect(Collectors.toSet());
    }

    public static BranchDTO mapToBranchDTO(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setName(branch.getName());
        branchDTO.setLastCommitSHA(branch.getCommit().getSha());
        return branchDTO;
    }
}