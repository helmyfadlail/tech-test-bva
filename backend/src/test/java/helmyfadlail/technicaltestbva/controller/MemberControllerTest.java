package helmyfadlail.technicaltestbva.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import helmyfadlail.technicaltestbva.entity.Member;
import helmyfadlail.technicaltestbva.entity.User;
import helmyfadlail.technicaltestbva.dto.MemberResponse;
import helmyfadlail.technicaltestbva.dto.WebResponse;
import helmyfadlail.technicaltestbva.repository.MemberRepository;
import helmyfadlail.technicaltestbva.repository.UserRepository;
import helmyfadlail.technicaltestbva.security.BCrypt;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private MemberRepository memberRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
                memberRepository.deleteAll();
                userRepository.deleteAll();

                User user = new User();
                user.setUsername("helmy_fadlail");
                user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
                user.setToken("test_token");
                user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000);
                userRepository.save(user);
        }

        @Test
        void getMemberNotFound() throws Exception {
                mockMvc.perform(
                                get("/api/members/123456789")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isNotFound())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });
                                        assertNotNull(response.getErrors());
                                });
        }

        @Test
        void getMemberSuccess() throws Exception {
                User user = userRepository.findById("helmy_fadlail").orElseThrow();

                Member member = new Member();
                member.setId(UUID.randomUUID().toString());
                member.setUser(user);
                member.setName("Helmy");
                member.setPosition("Developer");
                member.setSuperior("Albab");
                member.setPictureUrl("http://www.imagekit.org/helmy-fadlail.png");
                memberRepository.save(member);

                mockMvc.perform(
                                get("/api/members/" + member.getId())
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<MemberResponse> response = objectMapper
                                                        .readValue(result.getResponse().getContentAsString(),
                                                                        new TypeReference<>() {
                                                                        });
                                        assertNull(response.getErrors());

                                        assertEquals(member.getId(), response.getData().getId());
                                        assertEquals(member.getName(), response.getData().getName());
                                        assertEquals(member.getPosition(), response.getData().getPosition());
                                        assertEquals(member.getSuperior(), response.getData().getSuperior());
                                        assertEquals(member.getPictureUrl(), response.getData().getPictureUrl());
                                });
        }

        @Test
        void deleteMemberNotFound() throws Exception {
                mockMvc.perform(
                                delete("/api/members/5000")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isNotFound())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });
                                        assertNotNull(response.getErrors());
                                });
        }

        @Test
        void deleteContactSuccess() throws Exception {
                User user = userRepository.findById("helmy_fadlail").orElseThrow();

                Member member = new Member();
                member.setId(UUID.randomUUID().toString());
                member.setUser(user);
                member.setName("Helmy Fadlail");
                member.setPosition("Developer");
                member.setSuperior("Albab");
                memberRepository.save(member);

                mockMvc.perform(
                                delete("/api/members/" + member.getId())
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
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
                                });
        }

        @Test
        void searchNotFound() throws Exception {
                mockMvc.perform(
                                get("/api/members")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<List<MemberResponse>> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNull(response.getErrors());
                                        assertEquals(0, response.getData().size());
                                        assertEquals(0, response.getPaging().getTotalPage());
                                        assertEquals(0, response.getPaging().getCurrentPage());
                                        assertEquals(10, response.getPaging().getSize());
                                });
        }

        @Test
        void searchSuccess() throws Exception {
                User user = userRepository.findById("helmy_fadlail").orElseThrow();

                for (int i = 0; i < 100; i++) {
                        Member member = new Member();
                        member.setId(UUID.randomUUID().toString());
                        member.setUser(user);
                        member.setName("Helmy Fadlail " + i);
                        member.setPosition("Developer");
                        member.setSuperior("Albab");
                        member.setPictureUrl("http://www.imagekit.org/helmy-fadlail.png");
                        memberRepository.save(member);
                }

                mockMvc.perform(
                                get("/api/members")
                                                .queryParam("name", "Helmy")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<List<MemberResponse>> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNull(response.getErrors());
                                        assertEquals(10, response.getData().size());
                                        assertEquals(10, response.getPaging().getTotalPage());
                                        assertEquals(0, response.getPaging().getCurrentPage());
                                        assertEquals(10, response.getPaging().getSize());
                                });

                mockMvc.perform(
                                get("/api/members")
                                                .queryParam("name", "Fadlail")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<List<MemberResponse>> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNull(response.getErrors());
                                        assertEquals(10, response.getData().size());
                                        assertEquals(10, response.getPaging().getTotalPage());
                                        assertEquals(0, response.getPaging().getCurrentPage());
                                        assertEquals(10, response.getPaging().getSize());
                                });

                mockMvc.perform(
                                get("/api/members")
                                                .queryParam("position", "loper")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<List<MemberResponse>> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNull(response.getErrors());
                                        assertEquals(10, response.getData().size());
                                        assertEquals(10, response.getPaging().getTotalPage());
                                        assertEquals(0, response.getPaging().getCurrentPage());
                                        assertEquals(10, response.getPaging().getSize());
                                });

                mockMvc.perform(
                                get("/api/members")
                                                .queryParam("superior", "bab")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<List<MemberResponse>> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNull(response.getErrors());
                                        assertEquals(10, response.getData().size());
                                        assertEquals(10, response.getPaging().getTotalPage());
                                        assertEquals(0, response.getPaging().getCurrentPage());
                                        assertEquals(10, response.getPaging().getSize());
                                });

                mockMvc.perform(
                                get("/api/members")
                                                .queryParam("position", "developer")
                                                .queryParam("page", "500")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .header("X-API-TOKEN", "test_token"))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<List<MemberResponse>> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<>() {
                                                        });
                                        assertNull(response.getErrors());
                                        assertEquals(0, response.getData().size());
                                        assertEquals(10, response.getPaging().getTotalPage());
                                        assertEquals(500, response.getPaging().getCurrentPage());
                                        assertEquals(10, response.getPaging().getSize());
                                });
        }
}
