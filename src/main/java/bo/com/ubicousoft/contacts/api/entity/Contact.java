package bo.com.ubicousoft.contacts.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_contact")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContact;


    @NotNull(message = "nombre is null")
    @NotBlank(message = "nombre no puede estar en blanco")
    @NotEmpty(message = "nombre no puede estar vacio")
    private String nombre;

    @Column(name = "primer_apellido")
    @NotNull(message = "primerApellido is null")
    @NotBlank(message = "primerApellido no puede estar en blanco")
    @NotEmpty(message = "primerApellido no puede estar vacio")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    @NotNull(message = "segundoApellido is null")
    @NotBlank(message = "segundoApellido no puede estar en blanco")
    @NotEmpty(message = "segundoApellido no puede estar vacio")
    private String segundoApellido;

    @Column(name = "code_property")
    @NotNull(message = "celular is null")
    @NotBlank(message = "celular no puede estar en blanco")
    @NotEmpty(message = "celular no puede estar vacio")
    private String celular;

    @NotNull(message = "direccion is null")
    @NotBlank(message = "direccion no puede estar en blanco")
    @NotEmpty(message = "direccion no puede estar vacio")
    private String direccion;

}
