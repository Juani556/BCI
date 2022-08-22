package ar.com.globallogic.ejercicio.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {

    private LocalDateTime timestamp;

    private int codigo;

    private String detail;
}
