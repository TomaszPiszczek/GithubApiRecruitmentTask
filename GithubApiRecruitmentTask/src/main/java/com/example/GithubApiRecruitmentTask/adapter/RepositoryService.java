package com.example.GithubApiRecruitmentTask.adapter;

        import com.example.GithubApiRecruitmentTask.WebClient.GithubApiConnection;
        import com.example.GithubApiRecruitmentTask.mapper.RepositoryMapper;
        import com.example.GithubApiRecruitmentTask.model.dto.RepositoryDTO;
        import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.Repository;
        import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Branch;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.stereotype.Component;

        import java.util.Set;
        import java.util.concurrent.CompletableFuture;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;
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


        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            CompletableFuture<Void> future = new CompletableFuture<>();
            for (Repository repo : repositoryList) {
                 future = CompletableFuture.runAsync(
                        ()-> repo.setBranchList(getBranches(repo.getName(), username)),executorService);
            }
            future.join();

            executorService.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return repositoryList;
    }
}
