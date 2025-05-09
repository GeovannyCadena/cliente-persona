package cliente_persona.com.service.impl;

import cliente_persona.com.dto.ClienteDTO;
import cliente_persona.com.dto.ClienteResponseDTO;
import cliente_persona.com.exception.NegocioException;
import cliente_persona.com.model.Cliente;
import cliente_persona.com.repository.interfaces.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceImpTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImp clienteServiceImp;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cliente = new Cliente();
        cliente.setPersonaId(1L);
        cliente.setNombre("Jose Lema");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Otavalo sn y principal");
        cliente.setTelefono("098254785");
        cliente.setClienteId("1");
        cliente.setContrasena("123");
        cliente.setEstado(true);
    }

    @Test
    void saveCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Jose Lema");
        clienteDTO.setIdentificacion("1234567890");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setEdad(30);
        clienteDTO.setDireccion("Otavalo sn y principal");
        clienteDTO.setTelefono("098254785");
        clienteDTO.setClienteId("1");
        clienteDTO.setContrasena("123");
        clienteDTO.setEstado(true);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO response = clienteServiceImp.saveCliente(clienteDTO);

        assertNotNull(response);
        assertEquals("1234567890", response.getIdentificacion());
        assertEquals("Jose Lema", response.getNombre());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void getAllClientes() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        List<ClienteResponseDTO> clientes = clienteServiceImp.getAllClientes();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("1234567890", clientes.get(0).getIdentificacion());
        verify(clienteRepository).findAll();
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void getClienteById() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO response = clienteServiceImp.getClienteById("1");

        assertNotNull(response);
        assertEquals("1234567890", response.getIdentificacion());
        verify(clienteRepository).findById(1L);
        verify(clienteRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void getCuentaById_notFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NegocioException.class, () -> clienteServiceImp.getClienteById("99"));
    }

    @Test
    void deleteCliente() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        clienteServiceImp.deleteCliente("1");

        verify(clienteRepository).existsById(1L);
        verify(clienteRepository).deleteById(1L);
        verify(clienteRepository, times(1)).existsById(any(Long.class));
        verify(clienteRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void deleteCliente_notFound() {
        when(clienteRepository.existsById(2L)).thenReturn(false);

        assertThrows(NegocioException.class, () -> clienteServiceImp.deleteCliente("2"));
        verify(clienteRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Andrea Robles");
        clienteDTO.setIdentificacion("1727366088");
        clienteDTO.setGenero("Femenino");
        clienteDTO.setEdad(50);
        clienteDTO.setDireccion("Puembo calle juan leon mera");
        clienteDTO.setTelefono("098254785");
        clienteDTO.setClienteId("1");
        clienteDTO.setContrasena("123");
        clienteDTO.setEstado(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO clienteResponse = clienteServiceImp.updateCliente("1", clienteDTO);

        assertNotNull(clienteResponse);
        assertEquals("1234567890", clienteResponse.getIdentificacion());
        verify(clienteRepository).findById(1L);
        verify(clienteRepository).save(any(Cliente.class));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(clienteRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void updateCuenta_notFound() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NegocioException.class, () -> clienteServiceImp.updateCliente("2", new ClienteDTO()));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}