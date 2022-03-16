package com.upgle.api;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

  //MockMvc라는걸로 테스트 하기
  @Autowired
  private MockMvc mvc;

//  @Autowired
//  WebTestClient webClient;

  @Test
  void testWithMockMvx() throws Exception {
    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string("HELLO"));
  }

//  @Test
//  void testWithWebTestClient() {
//    webClient
//        .get().uri("/")
//        .exchange()
//        .expectStatus().isOk()
//        .expectBody(String.class).isEqualTo("HELLO");
//  }
}
