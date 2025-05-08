package cliente_persona.com.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

    @NotNull(message = "El nombre no puede ser nulo.")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres.")
    private String nombre;

    @NotNull(message = "La identificación no puede ser nula.")
    @Size(min = 10, max = 10, message = "La identificación debe tener exactamente 10 caracteres.")
    private String identificacion;

    @NotNull(message = "El genero no puede ser nulo.")
    private String genero;

    @NotNull(message = "La edad no puede ser nulo.")
    private Integer edad;

    private String direccion;
    private String telefono;

    @NotNull
    private String clienteId;

    @NotNull(message = "La contraseña no puede ser nula.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String contrasena;

    @NotNull(message = "El estado no puede ser nulo.")
    private Boolean estado;
}
