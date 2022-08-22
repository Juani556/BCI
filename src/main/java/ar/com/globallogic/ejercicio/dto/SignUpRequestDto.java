package ar.com.globallogic.ejercicio.dto;

import ar.com.globallogic.ejercicio.helper.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SignUpRequestDto {

    private String name;

    @NotEmpty(message = "El campo 'email' no debe ser vacío")
    @Pattern(regexp = Constants.MAIL_REGEX, message = "El campo 'email' debe tener un formato válido")
    private String email;

    @Size(min = 8, max = 12, message = "El campo 'password' debe tener minimo {min} caracteres y máximo {max} caracteres")
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "El campo 'password' debe tener una mayúscula y dos números")
    private String password;

    private List<PhoneDto> phones;
}
