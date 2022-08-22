package ar.com.globallogic.ejercicio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SignUpResponseDto {

    private String id;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "MMMM dd, yyyy hh:mm:ss")
    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;
}
