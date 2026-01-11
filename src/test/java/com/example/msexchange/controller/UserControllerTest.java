package com.example.msexchange.controller;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.model.enums.UserRole;
import com.example.msexchange.model.repsone.UserResponse;
import com.example.msexchange.model.request.UserRequest;
import com.example.msexchange.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.example.msexchange.model.enums.UserRole.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final String USER_PATH = "/v1/users";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;




    @Test
    public void registerUserTest() throws Exception{
        var request = """
                {
                   "userName": "cffer",
                   "password": "123",
                   "role": "ADMIN",
                   "email": "cefer20051965@gmail.com"
                }
                """;
        mockMvc.perform(post("/v1/users")
                        .content(request)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(userService, times(1)).registerUser(any(UserRequest.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getAllUserTest() throws Exception{
        //given
        var id = 1L;

        var userRequest = new UserRequest();
        userRequest.setUserName("user");
        userRequest.setEmail("user@gmail.com");
        userRequest.setPassword("user123");
        userRequest.setRole(USER);

        var userResponse =  new UserResponse();
        userResponse.setId(1L);
        userResponse.setUserName("user");
        userResponse.setPassword("user123");

        //when
        when(userService.getAllUsers()).thenReturn(List.of(userResponse));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(USER_PATH))
                .andExpect(status().isOk());

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateUser()throws  Exception{
        //given
        var id = 1L;
        var userRequest = new UserRequest();
        userRequest.setUserName("user");
        userRequest.setEmail("user@gmail.com");
        userRequest.setPassword("user123");
        userRequest.setRole(USER);

        var request = """
                {
                   "userName": "user",
                   "password": "user123",
                   "role": "USER",
                   "email": "user@gmail.com"
                }
                """;

        //then
        mockMvc.perform(put(USER_PATH + "/{id}",id)
                        .content(request)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(id, userRequest);
        verifyNoMoreInteractions(userService);


    }
}
