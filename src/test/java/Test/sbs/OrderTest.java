package Test.sbs;

import Test.sbs.dto.MyOrderDTO;
import Test.sbs.services_impl.MyOrderServiceImpl;
import Test.sbs.tables.StatusEnum;
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
public class OrderTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MyOrderServiceImpl orderService;
    private Long lastOrderId;

    @Test
    @Order(1)
    public void getOrderTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/my_order")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].delivery").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].delivery").value(false));
    }

    @Order(2)
    @Test
    public void getOneOrderTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/my_order/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.delivery").value(true));
    }

    @Order(3)
    @Test
    public void addOrderTest() throws Exception {
        MyOrderDTO orderDTO = new MyOrderDTO(false,StatusEnum.COMPLETE, 2L);
        String requestBody = objectMapper.writeValueAsString(orderDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/my_order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.delivery").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Complete"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Order(4)
    @Test
    public void replaceOrderTest() throws Exception {
        MyOrderDTO orderDTO = new MyOrderDTO(false,StatusEnum.COMPLETE, 1L);
        lastOrderId = orderService.getIdForTheTests(orderDTO.getStatus(), orderDTO.isDelivery());
        String requestBody = objectMapper.writeValueAsString(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/my_order/" + lastOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").value(1L));
    }

    @Order(5)
    @Test
    public void deleteOrderTest() throws Exception {
        lastOrderId = orderService.getIdForTheTests(StatusEnum.COMPLETE, false);
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/my_order/" + lastOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
