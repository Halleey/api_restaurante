package card.cardapio;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CardapioApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreatePaymentEndpoint() throws Exception {
        // Preparar o payload da requisição
        PaymentRequest paymentRequest = new PaymentRequest(100.0, "BRL", "paypal", "sale", "Test payment");
        String jsonPayload = objectMapper.writeValueAsString(paymentRequest);

        // Executar a requisição POST para /paypal/create-payment
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/paypal/create-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Verificar se o status HTTP é 200 OK
                .andReturn();

        // Verificar se o JSON de resposta contém um campo 'id'
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        assertTrue(jsonNode.has("id"));
    }

    // Classe interna para representar o payload da requisição
    @Data
    static class PaymentRequest {
        @JsonProperty private double total;
        @JsonProperty private String currency;
        @JsonProperty private String method;
        @JsonProperty private String intent;
        @JsonProperty private String description;

        public PaymentRequest(double total, String currency, String method, String intent, String description) {
            this.total = total;
            this.currency = currency;
            this.method = method;
            this.intent = intent;
            this.description = description;
        }
    }
}