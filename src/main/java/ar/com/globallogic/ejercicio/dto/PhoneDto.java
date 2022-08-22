package ar.com.globallogic.ejercicio.dto;

import lombok.Data;

@Data
public class PhoneDto {

    private long number;

    private int citycode;

    private String countrycode;
}
