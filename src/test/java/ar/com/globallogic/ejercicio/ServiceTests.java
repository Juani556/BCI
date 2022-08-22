package ar.com.globallogic.ejercicio;

import ar.com.globallogic.ejercicio.dto.LoginRequestDto;
import ar.com.globallogic.ejercicio.dto.LoginResponseDto;
import ar.com.globallogic.ejercicio.dto.SignUpRequestDto;
import ar.com.globallogic.ejercicio.dto.SignUpResponseDto;
import ar.com.globallogic.ejercicio.entity.Usuario;
import ar.com.globallogic.ejercicio.helper.JsonExamples;
import ar.com.globallogic.ejercicio.helper.Properties;
import ar.com.globallogic.ejercicio.mapper.PhoneMapper;
import ar.com.globallogic.ejercicio.repository.UsuarioRepository;
import ar.com.globallogic.ejercicio.service.UsuarioService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTests {

    private UsuarioService usuarioService;

    private ObjectMapper objectMapper;

    private UsuarioRepository repository;

    @BeforeAll
    void  setUp() {

        objectMapper = new ObjectMapper();

        Algorithm algorithm = Algorithm.HMAC256("Secret");
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("GlobalLogic").build();

        Properties properties = new Properties();
        properties.setPasswordSalt("Secret".getBytes());
        properties.setExpiration(60000L);
        properties.setSecret("secret");

        repository = Mockito.mock(UsuarioRepository.class);


        usuarioService = new UsuarioService(new PhoneMapper(), repository, properties, verifier);

    }


    @Test
    void testSignUp() throws Exception {

        SignUpRequestDto requestDto = objectMapper.readValue(JsonExamples.SIGN_UP_REQUEST, SignUpRequestDto.class);
        Usuario usuario = new Usuario();
        usuario.setId(UUID.fromString("a903eceb-b3d0-49d4-b6f7-f65d8eb75c21"));

        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        SignUpResponseDto responseDto = usuarioService.signUp(requestDto);

        Assertions.assertEquals(usuario.getId().toString(), responseDto.getId());
        Assertions.assertNotNull(responseDto.getToken());

    }

    @Test
    void testLogin_WhenPasswordIsSent() throws Exception {

        LoginRequestDto requestDto = objectMapper.readValue(JsonExamples.LOGIN_REQUEST, LoginRequestDto.class);

        Usuario usuario = new Usuario();
        usuario.setId(UUID.fromString("a903eceb-b3d0-49d4-b6f7-f65d8eb75c21"));
        usuario.setPassword(JsonExamples.PASSWORD_SAMPLE);
        usuario.setPhones(new ArrayList<>());

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        LoginResponseDto responseDto = usuarioService.login(requestDto, null);

        Assertions.assertEquals(usuario.getId().toString(), responseDto.getId());
        Assertions.assertNotNull(responseDto.getToken());

    }

}
