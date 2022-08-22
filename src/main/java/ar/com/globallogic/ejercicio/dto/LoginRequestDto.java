package ar.com.globallogic.ejercicio.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequestDto {

    @NotEmpty(message = "El campo 'email' es requerido")
    private String email;

    private String password;
}
