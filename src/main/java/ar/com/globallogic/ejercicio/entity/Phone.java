package ar.com.globallogic.ejercicio.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phoneSeq")
    @SequenceGenerator(name = "phoneSeq", allocationSize = 100, sequenceName = "PHONE_SEQ")
    private long id;

    @ManyToOne
    private Usuario usuario;

    private long number;

    @Column(name = "city_code")
    private int cityCode;

    @Column(name = "country_code")
    private String countryCode;
}
