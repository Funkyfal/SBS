package Test.sbs;

import Test.sbs.dto.GoodDTO;
import Test.sbs.services_impl.GoodServiceImpl;
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

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GoodServiceImpl goodService;
    private Long lastGoodId;

    @Test
    @Order(1)
    public void getAllGoodsTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/good")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Pasta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Pepperoni"));
    }

    @Order(2)
    @Test
    public void getOneGoodTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/good/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pasta"));
    }

    @Order(3)
    @Test
    public void addGoodTest() throws Exception {
        GoodDTO goodDTO = new GoodDTO("na", "n", "aasd", BigDecimal.valueOf(52.0));
        String requestBody = objectMapper.writeValueAsString(goodDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/good")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("na"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Order(4)
    @Test
    public void replaceGoodTest() throws Exception {
        GoodDTO goodDTO = new GoodDTO("na", "n", "aasd", BigDecimal.valueOf(52.52));
        lastGoodId = goodService.getIdForTheTests(goodDTO.getName(), goodDTO.getShortName());
        String requestBody = objectMapper.writeValueAsString(goodDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/good/" + lastGoodId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(52.52)));
    }

    @Order(5)
    @Test
    public void deleteGoodTest() throws Exception {
        lastGoodId = goodService.getIdForTheTests("na", "n");
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/good/" + lastGoodId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
