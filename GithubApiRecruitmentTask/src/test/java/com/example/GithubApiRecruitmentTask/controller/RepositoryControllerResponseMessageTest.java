package com.example.GithubApiRecruitmentTask.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(WireMockExtension.class)
public class RepositoryControllerResponseMessageTest {

    @Autowired
    private WebTestClient webTestClient;

    private static WireMockServer wireMockServer;

    @DynamicPropertySource
    static void overrideWebClientBaseUrl(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("github_base_url", wireMockServer::baseUrl);
    }
    @BeforeAll
     static void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @Test
    public void repositoryControllerShouldReturnUserNotFoundBodyWithStatus404WhileUserDontExist() {
       wireMockServer
               .stubFor(get(urlEqualTo("/users/UserName/repos"))
                        .withHeader("accept", equalTo("*/*"))
                       .willReturn(aResponse()
                                .withStatus(404)
                        )
                );
        this.webTestClient
                .get()
                .uri("/api/repositories/UserName")
                .header("accept", "application/json")
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody()
                .json("{ \"status\": \"404 NOT_FOUND\", \"message\": \"User not found\" }");

    }
    @Test
    public void repositoryControllerShouldReturn406WhenHeaderIsNotProvided() {
        this.webTestClient
                .get()
                .uri("/api/repositories/UserName")
                .exchange()
                .expectStatus().isEqualTo(406)
                .expectBody()
                .json("{ \"status\": \"406 NOT_ACCEPTABLE\", \"message\": \"Unsupported media type. Please specify Accept: application/json in your request header.\" }");

    }


}
