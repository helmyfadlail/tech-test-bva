package helmyfadlail.technicaltestbva.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import helmyfadlail.technicaltestbva.dto.LoginUserRequest;
import helmyfadlail.technicaltestbva.dto.RegisterUserRequest;
import helmyfadlail.technicaltestbva.dto.TokenResponse;
import helmyfadlail.technicaltestbva.dto.WebResponse;
import helmyfadlail.technicaltestbva.entity.User;
import helmyfadlail.technicaltestbva.repository.UserRepository;
import helmyfadlail.technicaltestbva.security.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
                userRepository.deleteAll();
        }

        @Test
        void registerSuccess() throws Exception {
                RegisterUserRequest request = new RegisterUserRequest();
                request.setUsername("helmy_fadlail");
                request.setEmail("helmyfadlail.5@gmail.com");
                request.setPassword("rahasia");
                request.setConfirmPassword("rahasia");

                mockMvc.perform(
                                post("/api/auth/register")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isOk())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertEquals("OK", response.getData());
                                });
        }

        @Test
        void registerBadRequest() throws Exception {
                RegisterUserRequest request = new RegisterUserRequest();
                request.setEmail("");
                request.setUsername("");
                request.setPassword("");
                request.setConfirmPassword("");

                mockMvc.perform(
                                post("/api/auth/register")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isBadRequest())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNotNull(response.getErrors());
                                });
        }

        @Test
        void registerDuplicate() throws Exception {
                User user = new User();
                user.setUsername("helmy_fadlail");
                user.setEmail("helmyfadlail.5@gmail.com");
                user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));

                userRepository.save(user);

                RegisterUserRequest request = new RegisterUserRequest();
                request.setUsername("helmy_fadlail");
                request.setEmail("helmyfadlail.5@gmail.com");
                request.setPassword("rahasia");
                request.setConfirmPassword("rahasia");

                mockMvc.perform(
                                post("/api/auth/register")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isBadRequest())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNotNull(response.getErrors());
                                });
        }

        @Test
        void loginFailedUserNotFound() throws Exception {
                LoginUserRequest request = new LoginUserRequest();
                request.setUsername("helmy_fadlail_albab");
                request.setPassword("rahasia");

                mockMvc.perform(
                                post("/api/auth/login")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNotNull(response.getErrors());
                                });
        }

        @Test
        void loginFailedWrongPassword() throws Exception {
                User user = new User();
                user.setUsername("helmy_fadlail");
                user.setEmail("helmyfadlail.5@gmail.com");
                user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
                userRepository.save(user);

                LoginUserRequest request = new LoginUserRequest();
                request.setUsername("helmy_fadlail");
                request.setPassword("rahasiaku");

                mockMvc.perform(
                                post("/api/auth/login")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNotNull(response.getErrors());
                                });
        }

        @Test
        void loginSuccess() throws Exception {
                User user = new User();
                user.setUsername("helmy_fadlail");
                user.setEmail("helmyfadlail.5@gmail.com");
                user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
                userRepository.save(user);

                LoginUserRequest request = new LoginUserRequest();
                request.setUsername("helmy_fadlail");
                request.setPassword("rahasia");

                mockMvc.perform(
                                post("/api/auth/login")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isOk())
                                .andDo(result -> {
                                        WebResponse<TokenResponse> response = objectMapper
                                                        .readValue(result.getResponse().getContentAsString(),
                                                                        new TypeReference<>() {
                                                                        });
                                        assertNull(response.getErrors());
                                        assertNotNull(response.getData().getToken());
                                        assertNotNull(response.getData().getExpiredAt());

                                        User userDb = userRepository.findById("helmy_fadlail").orElse(null);
                                        assertNotNull(userDb);
                                        assertEquals(userDb.getToken(), response.getData().getToken());
                                        assertEquals(userDb.getTokenExpiredAt(), response.getData().getExpiredAt());
                                });
        }

        @Test
        void logoutFailed() throws Exception {
                mockMvc.perform(
                                delete("/api/auth/logout")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpectAll(
                                                status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNotNull(response.getErrors());
                                });
        }

        @Test
        void logoutSuccess() throws Exception {
                User user = new User();
                user.setUsername("helmy_fadlail");
                user.setEmail("helmyfadlail.5@gmail.com");
                user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
                user.setToken("test_token");
                user.setTokenExpiredAt(System.currentTimeMillis() + 10000000);
                userRepository.save(user);

                mockMvc.perform(
                                delete("/api/auth/logout")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNull(response.getErrors());
                                        assertEquals("OK", response.getData());

                                        User userDb = userRepository.findById("helmy_fadlail").orElse(null);
                                        assertNotNull(userDb);
                                        assertNull(userDb.getTokenExpiredAt());
                                        assertNull(userDb.getToken());
                                });
        }
}
