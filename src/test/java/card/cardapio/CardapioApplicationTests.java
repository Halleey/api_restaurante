package card.cardapio;
import card.cardapio.controller.UserController;
import card.cardapio.dto.address.AddressDTO;
import card.cardapio.dto.user.UserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CardapioApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSaveUser() throws Exception {
        // Dados do usuário para o teste
        UserRequestDto userRequestDto = new UserRequestDto("John",
                "Doe", "password123",
                "testando@gmail.com", 1L);
        mockMvc.perform(post("/public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDto)))
                .andExpect(status().isOk());

    }
    @Test
    public void testSaveAdddres() throws Exception {
        AddressDTO addressDTO = new AddressDTO("Rua Teste", "14", 1L);
        mockMvc.perform(post("/public/address")
                        .header("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testExcludeAddress() throws  Exception {
        AddressDTO addressDTO = new AddressDTO("Rua Teste", "14", 1L);
        mockMvc.perform(delete("/public/address")
                .header("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO))).andExpect(status().isOk());
    }
    @Test
    public void getAddress() throws Exception {
        AddressDTO addressDTO = new AddressDTO("Rua ABC", "123", 12L);

        mockMvc.perform(get("/public/address")
                        .header("userId", "12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressDTO)))
                .andExpect(status().isOk());

        System.out.print(asJsonString(addressDTO));
    }



    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}