package com.spring.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.docs.dto.SaveRequest;
import com.spring.docs.entity.Post;
import com.spring.docs.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class PostControllerTest {

    @Autowired
    private PostRepository postRepository;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("게시글 저장 API 문서 생성 테스트")
    void createSaveDocs() throws Exception {
        SaveRequest request = new SaveRequest();
        request.setTitle("제목");
        request.setContent("내용");

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/save")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("save",
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        )));
    }

    @Test
    @DisplayName("게시글 조회 API 문서 생성 테스트")
    void createGetDocs() throws Exception {
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        Post save = postRepository.save(post);

        mockMvc.perform(get("/post/{id}", save.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(status().isOk())
                .andDo(document("get"));
    }
}