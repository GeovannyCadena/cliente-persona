package cliente_persona.com.service.impl;

import cliente_persona.com.dto.ClienteDTO;
import cliente_persona.com.dto.ClienteResponseDTO;
import cliente_persona.com.exception.NegocioException;
import cliente_persona.com.model.Cliente;
import cliente_persona.com.repository.interfaces.ClienteRepository;
import cliente_persona.com.service.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public ClienteResponseDTO saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.save(mapToEntity(clienteDTO));
        return mapToResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> getAllClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO  getClienteById(String id) {
        Cliente cliente = clienteRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NegocioException("Cliente con ID " + id + " no encontrado."));
        return mapToResponseDTO(cliente);
    }

    public void deleteCliente(String id) {
        if (!clienteRepository.existsById(Long.parseLong(id))) {
            throw new NegocioException("Cuenta con ID " + id + " no encontrada para eliminar.");
        }
        clienteRepository.deleteById(Long.parseLong(id));
    }

    public ClienteResponseDTO updateCliente(String id, ClienteDTO clienteDTO) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(Long.parseLong(id));
        if (clienteExistente.isPresent()) {
            Cliente cliente = mapToEntity(clienteDTO);
            cliente.setPersonaId(clienteExistente.get().getPersonaId());
            cliente = clienteRepository.save(cliente);
            return mapToResponseDTO(cliente);
        } else {
            throw new NegocioException("Cliente no encontrado con el id: " + id);
        }
    }

    private Cliente mapToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setClienteId(clienteDTO.getClienteId());
        cliente.setContrasena(clienteDTO.getContrasena());
        cliente.setEstado(clienteDTO.getEstado());
        return cliente;
    }

    private ClienteResponseDTO mapToResponseDTO(Cliente cliente) {
        ClienteResponseDTO responseDTO = new ClienteResponseDTO();
        responseDTO.setNombre(cliente.getNombre());
        responseDTO.setGenero(cliente.getGenero());
        responseDTO.setEdad(cliente.getEdad());
        responseDTO.setIdentificacion(cliente.getIdentificacion());
        responseDTO.setDireccion(cliente.getDireccion());
        responseDTO.setTelefono(cliente.getTelefono());
        responseDTO.setClienteId(cliente.getClienteId());
        responseDTO.setEstado(cliente.getEstado());
        return responseDTO;
    }

}
