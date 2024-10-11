package com.example.keycloak.resource.KeycloakResourceServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/list")
    public ResponseEntity<?> getUsersFromResourceServer(@AuthenticationPrincipal Jwt jwt) {

        String accessToken = jwt.getTokenValue();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        //Break Point

        String uri = "http://localhost:9091/users";

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, String>>> users = restTemplate.exchange(uri, HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<>() {});

        return ResponseEntity.ok(users.getBody());
    }

}
