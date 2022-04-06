package com.upgle.api.domain.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgle.api.domain.user.dto.request.UserSignUpRequest;
import com.upgle.api.domain.user.dto.response.UserSignUpResponse;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserController.class)
public class UserControllerMockTest {

  @MockBean
  UserService userService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @DisplayName("회원가입요청_Invalid실패")
  @Test
  void signUp_유효하지_않은_값() throws Exception {
    //given
    UserSignUpRequest request = invalidEmailSingUpRequestDto();
    User res = request.toEntity("query");
    given(userService.signUp(request)).willReturn(res);
    //when
    ResultActions resultActions = requestSignUp(request);

    //then
    resultActions.andExpect(status().isBadRequest())
        .andExpect(jsonPath("message").value(" input value is invalid"))
        .andExpect(jsonPath("status").value(400));
  }


  @DisplayName("회원가입요청_성공")
  @Test
  void signUp_성공() throws Exception {
    //given
    UserSignUpRequest request = createSignUpRequestDto();
    User user = request.toEntity("query");

    ReflectionTestUtils.setField(user, "id", 1L);

    UserSignUpResponse res = new UserSignUpResponse(user);

    given(userService.signUp(any(UserSignUpRequest.class))).willReturn(user);

    //when
    ResultActions resultActions = requestSignUp(request);

    //then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(1L))
        .andExpect(jsonPath("$.data.email").value(request.getEmail()))
        .andExpect(jsonPath("$.data.nickname").value(request.getNickname()))
    ;
  }

  private UserSignUpRequest createSignUpRequestDto() {
    return new UserSignUpRequest("email@email.com", "nickname", "password");
  }

  private UserSignUpRequest invalidEmailSingUpRequestDto() {
    return new UserSignUpRequest("email", "nickname", "passsword");
  }


  private ResultActions requestSignUp(UserSignUpRequest dto) throws Exception {
    return mockMvc.perform(post("/api/user")
            .content(objectMapper.writeValueAsString(dto).getBytes(StandardCharsets.UTF_8))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print());
  }
}
