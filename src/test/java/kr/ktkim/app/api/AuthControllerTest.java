package kr.ktkim.app.api;

import kr.ktkim.app.SpringAngularAppApplication;
import kr.ktkim.app.TestUtil;
import kr.ktkim.app.security.jwt.JwtAuthRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Keumtae Kim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringAngularAppApplication.class)
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    public void testAuthenticate() throws Exception {
        JwtAuthRequest request = new JwtAuthRequest("user1", "user1");
        mockMvc.perform(post("/api/authenticate")
                .content(TestUtil.convertObjectToJsonBytes(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
