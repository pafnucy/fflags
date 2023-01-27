package dev.apanufnik.featureflagservice;


import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("permission")
@Value
class Permission {

    @Id
    String id;

    @Indexed(unique = true)
    String username;

    List<String> featurePermissions;


    PermissionDto toDto() {
        return new PermissionDto(featurePermissions);
    }

}

