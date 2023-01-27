package dev.apanufnik.featureflagservice;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class DeveloperProfileDataInitializer {

    public DeveloperProfileDataInitializer(UserRepository userRepository, PermissionRepository permissionRepository) {
        log.warn("deleting all users and permissions");
        userRepository.deleteAll();
        permissionRepository.deleteAll();

        log.info("adding data for testing purposes");
        userRepository.save(User.builder()
                .username("user-a")
                .password("{noop}unsecure")
                .role("user").build());

        permissionRepository.save(Permission.builder()
                .username("user-a")
                .featurePermissions(List.of("first-feature")).build());
    }
}
