package dev.apanufnik.featureflagservice;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/features")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class FeaturePermissionController {

    PermissionRepository permissionRepository;

    TokenFacade tokenFacade;


    @GetMapping
    ResponseEntity<PermissionDto> get(Authentication authentication) {

        var username = ((User) authentication.getPrincipal()).getUsername();

        var permissionOptional = permissionRepository.findByUsername(username);

        return permissionOptional
                .map(permission -> ResponseEntity
                        .status(200)
                        .header(
                                "X-feature-permissions",
                                tokenFacade.createFeatureFlagPermissionToken(username, permission.getFeaturePermissions()))
                        .body(permission.toDto()))
                .orElseGet(() -> ResponseEntity
                        .status(200)
                        .body(PermissionDto.empty()));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    ResponseEntity<Void> addFeaturePermission(@RequestBody PermissionPostDto dto) {
        var saved = permissionRepository.save(Permission.builder()
                .username(dto.getUsername())
                .featurePermissions(dto.getPermissions()).build());
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, saved.getId()).build();
    }


    @Value
    static class PermissionPostDto {
        String username;
        List<String> permissions;
    }
}
