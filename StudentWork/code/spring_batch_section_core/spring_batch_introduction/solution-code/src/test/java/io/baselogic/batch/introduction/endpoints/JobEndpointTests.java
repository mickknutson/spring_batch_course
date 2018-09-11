package io.baselogic.batch.introduction.endpoints;

import io.baselogic.batch.introduction.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Mick Knutson
 *
 */
@DirtiesContext

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
public class JobEndpointTests {

    @Autowired
    private MockMvc mockMvc;


    //-----------------------------------------------------------------------//


    @Test
    public void test_batch_job_endpoint__launch_job() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/launch"))

                // Lets view the response first:
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andReturn()
                ;

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getContentAsString()).contains("exitCode=COMPLETED");

    }


    @Test
    public void test_batch_job_endpoint__launch_job_diabled() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/launch")
                        .param("launchJob", "false")
                )

                // Lets view the response first:
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andReturn()
                ;

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getContentAsString()).contains("UNKNOWN");

    }

    //-----------------------------------------------------------------------//


} // The End