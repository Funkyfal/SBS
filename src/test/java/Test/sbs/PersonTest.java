package Test.sbs;

import Test.sbs.dto.PersonDTO;
import Test.sbs.services_impl.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
public class PersonTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PersonServiceImpl personService;
    private Long lastPersonId;


    @Order(1)
    @Test
    public void getPersonTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/person")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Darya"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Anton"));
    }

    @Order(2)
    @Test
    public void getOnePersonTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/person/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Darya"));
    }

    @Order(3)
    @Test
    public void addPersonTest() throws Exception {
        PersonDTO personDTO = new PersonDTO("Anton",
                "Doska",
                "",
                "",
                "",
                "",
                13);
        String requestBody = objectMapper.writeValueAsString(personDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anton"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName").value("Doska"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Order(4)
    @Test
    public void replacePersonTest() throws Exception {
        PersonDTO personDTO = new PersonDTO("Anton",
                "Doska",
                "",
                "",
                "",
                "123",
                13);
        lastPersonId = personService.getIdForTheTests("");
        String requestBody = objectMapper.writeValueAsString(personDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/person/" + lastPersonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anton"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName").value("Doska"));
    }

    @Order(5)
    @Test
    public void deletePersonTest() throws Exception {
        lastPersonId = personService.getIdForTheTests("123");
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/person/" + lastPersonId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
