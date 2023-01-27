package dev.apanufnik.featureflagservice;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
