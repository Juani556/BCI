package ar.com.globallogic.ejercicio.mapper;

import ar.com.globallogic.ejercicio.dto.PhoneDto;
import ar.com.globallogic.ejercicio.entity.Phone;
import ar.com.globallogic.ejercicio.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhoneMapper {

    public PhoneDto toDto(Phone phone) {

        PhoneDto phoneDto = new PhoneDto();

        phoneDto.setNumber(phone.getNumber());
        phoneDto.setCitycode(phone.getCityCode());
        phoneDto.setCountrycode(phone.getCountryCode());

        return phoneDto;
    }

    public Phone toEntity(PhoneDto phoneDto, Usuario usuario) {

        Phone phone = new Phone();

        phone.setNumber(phoneDto.getNumber());
        phone.setCityCode(phoneDto.getCitycode());
        phone.setCountryCode(phoneDto.getCountrycode());
        phone.setUsuario(usuario);

        return phone;
    }

    public List<PhoneDto> toDtos(List<Phone> phoneList) {

        return phoneList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Phone> toEntities(List<PhoneDto> phoneDtoList, Usuario usuario) {

        return phoneDtoList.stream().map(x -> this.toEntity(x, usuario)).collect(Collectors.toList());
    }
}
