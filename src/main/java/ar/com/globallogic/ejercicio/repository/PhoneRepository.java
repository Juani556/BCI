package ar.com.globallogic.ejercicio.repository;

import ar.com.globallogic.ejercicio.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
