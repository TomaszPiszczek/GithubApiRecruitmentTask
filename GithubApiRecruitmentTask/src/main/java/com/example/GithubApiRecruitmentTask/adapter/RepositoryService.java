package com.example.GithubApiRecruitmentTask.adapter;

import com.example.GithubApiRecruitmentTask.WebClient.GithubApiConnection;
import com.example.GithubApiRecruitmentTask.mapper.RepositoryMapper;
import com.example.GithubApiRecruitmentTask.model.dto.RepositoryDTO;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.Repository;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Branch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RepositoryService {
    GithubApiConnection githubApiConnection;

    public RepositoryService(GithubApiConnection githubApiConnection) {
        this.githubApiConnection = githubApiConnection;
    }

    public Set<RepositoryDTO> getRepositories(String username) {

        Set<Repository> repositories = assignBranchesToRepository(githubApiConnection.getRepositoriesForUser(username),username);

        return repositories.stream()
                .map(RepositoryMapper::mapToRepositoryDTO)
                .collect(Collectors.toSet());
    }

    private Set<Branch> getBranches(String repositoryName, String username) {
        return githubApiConnection.getBranchesForRepository(username, repositoryName);
    }
    private Set<Repository> assignBranchesToRepository(Set<Repository> repositoryList,String username){

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (Repository repo : repositoryList) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                    repo.setBranchList(getBranches(repo.getName(), username)));
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return repositoryList;
    }
}
