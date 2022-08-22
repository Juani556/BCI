package ar.com.globallogic.ejercicio.entity;

import jdk.vm.ci.meta.Local;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "usuarioSeq")
    @GenericGenerator(name = "usuarioSeq", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Phone> phones;

    private LocalDateTime created;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public Usuario() {
        LocalDateTime now = LocalDateTime.now();
        this.created = now;
        this.lastLogin = now;
    }
}
