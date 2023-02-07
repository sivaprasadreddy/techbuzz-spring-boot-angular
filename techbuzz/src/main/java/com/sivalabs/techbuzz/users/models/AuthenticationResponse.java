package com.sivalabs.techbuzz.users.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_at")
    private Instant expiresAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private String role;
}
