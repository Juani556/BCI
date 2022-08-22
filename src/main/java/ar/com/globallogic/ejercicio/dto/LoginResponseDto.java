package ar.com.globallogic.ejercicio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LoginResponseDto {

    private String id;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss")
    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;

    private String name;

    private String email;

    private String password;

    private List<PhoneDto> phones;

}
