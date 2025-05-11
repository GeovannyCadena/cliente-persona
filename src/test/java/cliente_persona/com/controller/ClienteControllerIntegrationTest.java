package cliente_persona.com.controller;

import cliente_persona.com.dto.ClienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

     @Test
     public void testCrearCliente() throws Exception {
         ClienteDTO clienteDTO = new ClienteDTO();
         clienteDTO.setNombre("Marianela");
         clienteDTO.setGenero("Femenino");
         clienteDTO.setEdad(30);
         clienteDTO.setIdentificacion("1234567894");
         clienteDTO.setDireccion("Calle Ficticia 123");
         clienteDTO.setTelefono("123456789");
         clienteDTO.setClienteId("CLI-001");
         clienteDTO.setContrasena("password123");
         clienteDTO.setEstado(true);

         mockMvc.perform(post("/api/clientes")
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(clienteDTO)))
                 .andDo(print())
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.nombre").value("Marianela"))
                 .andExpect(jsonPath("$.genero").value("Femenino"))
                 .andExpect(jsonPath("$.edad").value(30))
                 .andExpect(jsonPath("$.identificacion").value("1234567894"))
                 .andExpect(jsonPath("$.direccion").value("Calle Ficticia 123"))
                 .andExpect(jsonPath("$.telefono").value("123456789"))
                 .andExpect(jsonPath("$.clienteId").value("CLI-001"))
                 .andExpect(jsonPath("$.estado").value(true));
     }

    @Test
    public void testGetAllClientes() throws Exception {
        mockMvc.perform(get("/api/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nombre").isNotEmpty());
    }

    @Test
    public void testGetClienteById() throws Exception {
        String clienteId = "1";

        mockMvc.perform(get("/api/clientes/{id}", clienteId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value("1"))
                .andExpect(jsonPath("$.nombre").isNotEmpty());
    }

    @Test
    public void testUpdateCliente() throws Exception {
        String clienteId = "1";

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Marianela Actualizada");
        clienteDTO.setGenero("Femenino");
        clienteDTO.setEdad(32);
        clienteDTO.setIdentificacion("1234567894");
        clienteDTO.setDireccion("Calle Ficticia 123 Actualizada");
        clienteDTO.setTelefono("987654321");
        clienteDTO.setClienteId("CLI-001");
        clienteDTO.setContrasena("password123");
        clienteDTO.setEstado(true);

        mockMvc.perform(put("/api/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Marianela Actualizada"))
                .andExpect(jsonPath("$.edad").value(32));
    }

    @Test
    public void testDeleteCliente() throws Exception {
        String clienteId = "1";

        mockMvc.perform(delete("/api/clientes/{id}", clienteId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
