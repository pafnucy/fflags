package dev.apanufnik.featureflagservice;


import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TokenFacade {

    String createFeatureFlagPermissionToken(String username, List<String> featureFlags) {
        // TODO change to proper JWT payload
        return String.format("Bearer %s,%s", username, String.join(",", featureFlags));
    }
}
