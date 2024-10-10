package Test.sbs;

import Test.sbs.dto.MyOrderGoodDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyOrderGoodTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void getAllOrderGoodsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/good/my_order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].myOrderId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].myOrderId").value(2));
    }

    @Order(2)
    @Test
    public void getOneOrderGoodTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/good/4/my_order/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(40));
    }

    @Order(3)
    @Test
    public void addGoodTest() throws Exception {
        MyOrderGoodDTO orderGoodDTO = new MyOrderGoodDTO(52, 1L, 5L);
        String requestBody = objectMapper.writeValueAsString(orderGoodDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/good/my_order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(52))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Order(4)
    @Test
    public void replaceGoodTest() throws Exception {
        MyOrderGoodDTO orderGoodDTO = new MyOrderGoodDTO(5252, 1L, 5L);
        String requestBody = objectMapper.writeValueAsString(orderGoodDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/good/" + 5L + "/my_order/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(5252));
    }

    @Order(5)
    @Test
    public void deleteGoodTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/good/" + 5L + "/my_order/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
