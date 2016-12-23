package me.nielcho;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nielcho.domain.Good;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationText {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void test() throws Exception {

        String url = "/hello";

        List<Good> goodList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Good good = new Good();
            good.setId(i);
            good.setName("Good_" + i);
            goodList.add(good);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(goodList);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        goodList.remove(5);
        json = mapper.writeValueAsString(goodList);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andReturn();
    }

}
