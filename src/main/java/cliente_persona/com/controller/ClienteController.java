package cliente_persona.com.controller;

import cliente_persona.com.dto.ClienteDTO;
import cliente_persona.com.dto.ClienteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cliente_persona.com.service.impl.ClienteServiceImp;
import cliente_persona.com.service.interfaces.ClienteService;

import java.util.List;

@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
@Valid
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteServiceImp clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista con todos los clientes registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
        List<ClienteResponseDTO> clientes = clienteService.getAllClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un cliente por ID", description = "Devuelve la información de un cliente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable String id) {
        ClienteResponseDTO cliente = clienteService.getClienteById(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente con la información proporcionada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteResponseDTO nuevoCliente = clienteService.saveCliente(clienteDTO);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un cliente existente", description = "Actualiza los datos de un cliente por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable String id, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteResponseDTO clienteActualizado = clienteService.updateCliente(id, clienteDTO);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un cliente por ID", description = "Elimina un cliente de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
