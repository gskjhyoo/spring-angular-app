package kr.ktkim.app.api;

import kr.ktkim.app.SpringAngularAppApplication;
import kr.ktkim.app.TestUtil;
import kr.ktkim.app.model.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Keumtae Kim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringAngularAppApplication.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    public void testRegisterAccount() throws Exception {

        UserDto.Create create = new UserDto.Create();
        create.setLogin("test1");
        create.setPassword("test1");
        create.setEmail("test@test.com");
        create.setName("test1");

        mockMvc.perform(post("/api/register")
                .content(TestUtil.convertObjectToJsonBytes(create))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void testGetUser() throws Exception {

        String login = "user1";
        mockMvc.perform(get("/api/users/{login}", login)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void testGetUsers() throws Exception {

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    @WithMockUser("user1")
    public void testUpdatePassword() throws Exception {
        UserDto.Login userDto = new UserDto.Login();
        userDto.setLogin("user1");
        userDto.setPassword("user2");

        mockMvc.perform(post("/api/user/update-password")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
