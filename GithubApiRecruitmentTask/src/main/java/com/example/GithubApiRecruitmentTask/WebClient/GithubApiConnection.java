package com.example.GithubApiRecruitmentTask.WebClient;

import com.example.GithubApiRecruitmentTask.exception.UserNotFoundException;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.Repository;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Branch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;


import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GithubApiConnection {

    private final WebClient gitubWebClient;

    public GithubApiConnection(WebClient.Builder webClientBuilder, @Value("${github_base_url}") String githubBaseUrl) {
        this.gitubWebClient = webClientBuilder.baseUrl(githubBaseUrl).build();
    }


    public Set<Repository> getRepositoriesForUser(String username) {
        String url = String.format("/users/%s/repos", username);
        return gitubWebClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND),
                        clientResponse -> Mono.error(new UserNotFoundException("User not found")))
                .bodyToFlux(Repository.class)
                .filter(repository -> !repository.isFork())
                .collect(Collectors.toSet())
                .block();
    }
    public Set<Branch> getBranchesForRepository(String username, String repositoryName) {
        String url = String.format("/repos/%s/%s/branches", username, repositoryName);
        return gitubWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Branch.class)
                .collect(Collectors.toSet())
                .block();
    }




}
