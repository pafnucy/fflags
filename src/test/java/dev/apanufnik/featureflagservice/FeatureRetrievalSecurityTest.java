package dev.apanufnik.featureflagservice;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
class FeatureRetrievalSecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PermissionRepository permissionRepository;



    @Test
    void notAuthenticatedUserCannotAccessPermissionsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/features")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    @WithMockUser(username = "user-1", password = "pwd", authorities = {"user"})
    void authenticatedUserGetsPermissionsAsJsonAndTokenTest() throws Exception {

        permissionRepository.save(Permission.builder()
                .username("user-1")
                .featurePermissions(List.of("read-history", "add-products")).build());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/features")
                        .header(HttpHeaders.AUTHORIZATION, "Basic dXNlci0xOntub29wfXB3ZAo=")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "permissions" : ["read-history", "add-products"]
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.header().string("X-feature-permissions", TestData.userToken()));
    }


    @Test
    void versionEndpointIsAccessibleTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/version"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

}
