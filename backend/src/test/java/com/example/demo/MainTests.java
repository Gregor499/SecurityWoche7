package com.example.demo;

import com.example.demo.user.LoginData;
import com.example.demo.user.LoginResponse;
import com.example.demo.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        var newUser = new User();
        newUser.setUsername("klausi");
        newUser.setPassword("manta");
        ResponseEntity<Void> createUserResponse = restTemplate.postForEntity("/api/user", newUser, Void.class);
        Assertions.assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //failed login
        ResponseEntity<LoginResponse> failedLoginResponse = restTemplate.postForEntity("/api/login", new LoginData("sdiufb", "test"), LoginResponse.class);
        Assertions.assertThat(failedLoginResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        //login
        ResponseEntity<LoginResponse> succededLoginResponse = restTemplate.postForEntity("/api/login", new LoginData("klausi", "manta"), LoginResponse.class);
        String jwt = succededLoginResponse.getBody().getToken();
        Assertions.assertThat(succededLoginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(jwt).isNotBlank();

        ResponseEntity<String> getResponse = restTemplate.exchange(
                "/api/user",
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(jwt)),
                String.class
        );
        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getResponse.getBody()).isEqualTo("klausi");
    }

    private HttpHeaders createHeaders(String jwt) {
        String authHeaderValue = "Bearer " + jwt;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeaderValue);
        return headers;
    }
}
