package dev.apanufnik.featureflagservice;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@Value
@Builder
class User {
    @Id
    String id;

    String username;

    String password;

    String role;
}