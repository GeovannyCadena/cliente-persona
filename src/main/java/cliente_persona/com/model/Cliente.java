package cliente_persona.com.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Table(name = "cliente")
public class Cliente extends Persona  implements Serializable {

    @Column(name = "cliente_id", nullable = false, unique = true)
    private String clienteId;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getPersonaId()+
                ", nombre='" + getNombre() + '\'' +
                ", genero='" + getGenero() + '\'' +
                ", edad=" + getEdad() +
                ", identificacion='" + getIdentificacion() + '\'' +
                ", direccion='" + getDireccion() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", clienteId='" + clienteId + '\'' +
                ", estado=" + estado +
                '}';
    }
}