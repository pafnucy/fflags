package dev.apanufnik.featureflagservice;


import lombok.Value;

import java.util.List;

@Value
class PermissionDto {

    List<String> permissions;


    static PermissionDto empty() {
        return new PermissionDto(List.of());
    }
}
