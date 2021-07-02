package com.scrat.background;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrat.background.model.Res;
import com.scrat.background.module.test.TestModel;
import com.scrat.background.module.test.TestService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UnitTests {
    @Autowired
    private TestService testService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testException() {
        try {
            testService.runtimeException();
            AssertionErrors.fail("Never run here");
        } catch (RuntimeException ignore) {
        }
    }

    @Test
    void testNotFoundApi() throws Exception {
        mockMvc.perform(
                get("/exception/{exception_id}", "exceptionParam")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testPostJson() throws Exception {
        TestModel model = new TestModel()
                .setId(123)
                .setName("Summer");
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(model);
        String urlParam = "MyTest";
        mockMvc.perform(
                post("/test/json/{urlParam}", urlParam)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String res = result.getResponse().getContentAsString();
                    JavaType javaType = mapper.getTypeFactory().constructParametricType(Res.class, TestModel.class);
                    Res<TestModel> obj = mapper.readValue(res, javaType);
                    Assert.isTrue(obj.getStatus() == 200, "Response status error");
                    Assert.isTrue(urlParam.equals(obj.getMessage()), "Response message error");
                    String resContent = mapper.writeValueAsString(obj.getData());
                    Assert.isTrue(content.equals(resContent), "Response content error");
                });
    }

    @Test
    void testPostForm() throws Exception {
        UrlEncodedFormEntity form = new UrlEncodedFormEntity(
                Collections.singletonList(new BasicNameValuePair("name", "Hello"))
        );
        mockMvc.perform(
                post("/test/form")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(form))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(
                get("/test/get")
                        .param("name", "Hello")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
