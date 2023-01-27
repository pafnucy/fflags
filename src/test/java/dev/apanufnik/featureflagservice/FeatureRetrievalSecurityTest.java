package dev.apanufnik.featureflagservice;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(FeaturePermissionController.class)
class FeatureRetrievalSecurityTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void notAuthenticatedUserCannotAccessPermissionsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/features")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    @WithMockUser("user-1")
    void authenticatedUserGetsPermissionsAsJsonAndTokenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/features")
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

}
