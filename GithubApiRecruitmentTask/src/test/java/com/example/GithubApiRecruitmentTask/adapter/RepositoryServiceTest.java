package com.example.GithubApiRecruitmentTask.adapter;

import com.example.GithubApiRecruitmentTask.WebClient.GithubApiConnection;
import com.example.GithubApiRecruitmentTask.model.dto.RepositoryDTO;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.Owner;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.Repository;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Branch;
import com.example.GithubApiRecruitmentTask.model.githubRepositoryModel.branch.Commit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RepositoryServiceTest {
    private AutoCloseable closeable;

    @Mock
    private GithubApiConnection githubApiConnection;

    @InjectMocks
    private RepositoryService repositoryService;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void destroy() throws Exception {
        closeable.close();
    }
    @Test
    void testGetRepositories() {
        String username = "testUser";
        Set<Repository> repositories = new HashSet<>();
        Owner owner = new Owner("login");

        Repository repository = new Repository("repo1", false, owner, new HashSet<>());
        repositories.add(repository);

        when(githubApiConnection.getRepositoriesForUser(username)).thenReturn(repositories);
        when(githubApiConnection.getBranchesForRepository(anyString(), anyString())).thenReturn(new HashSet<>());

        Set<RepositoryDTO> result = repositoryService.getRepositories(username);

        assertEquals(1, result.size());
    }

    @Test
    void testAssignBranchesToRepositories() {
        String username = "testUser";
        Set<Repository> repositories = new HashSet<>();
        Owner owner = new Owner("login");

        Repository repository = new Repository("repo1", false, owner, new HashSet<>());
        repositories.add(repository);

        Branch branch = new Branch("Name", new Commit("commitSha"));

        when(githubApiConnection.getRepositoriesForUser(username)).thenReturn(repositories);
        when(githubApiConnection.getBranchesForRepository(anyString(), anyString())).thenReturn(Set.of(branch));

        Set<RepositoryDTO> result = repositoryService.getRepositories(username);

        assertEquals(1, result.size());
        RepositoryDTO repoDTO = result.iterator().next();
        assertEquals(1, repoDTO.branchList().size());
    }

    @Test
    void testAssignBranchesToRepository() {
        String username = "testUser";
        Set<Branch> branches = new HashSet<>();
        Owner owner = new Owner("login");
        new Repository("testRepo", true, owner, new HashSet<>());
        Commit commit1 = new Commit("commitSha");
        Branch branch1 = new Branch("main", commit1);
        Commit commit2 = new Commit("commitSha");
        Branch branch2 = new Branch("main", commit2);
        branches.add(branch1);
        branches.add(branch2);

        when(githubApiConnection.getBranchesForRepository(username, "testRepo"))
                .thenReturn(branches);



        Set<RepositoryDTO> resultRepositories = repositoryService.getRepositories(username);

        for (RepositoryDTO repo : resultRepositories) {
            assertEquals(2, repo.branchList().size());
        }
    }
}
