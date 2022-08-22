package ar.com.globallogic.ejercicio.service;

import ar.com.globallogic.ejercicio.dto.LoginRequestDto;
import ar.com.globallogic.ejercicio.dto.LoginResponseDto;
import ar.com.globallogic.ejercicio.dto.SignUpRequestDto;
import ar.com.globallogic.ejercicio.dto.SignUpResponseDto;
import ar.com.globallogic.ejercicio.entity.Usuario;
import ar.com.globallogic.ejercicio.exception.InternalServerException;
import ar.com.globallogic.ejercicio.exception.PasswordNotValidException;
import ar.com.globallogic.ejercicio.exception.UserNotFoundException;
import ar.com.globallogic.ejercicio.helper.Properties;
import ar.com.globallogic.ejercicio.mapper.PhoneMapper;
import ar.com.globallogic.ejercicio.repository.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioService {

    private PhoneMapper phoneMapper;

    private UsuarioRepository usuarioRepository;

    private Properties properties;

    private JWTVerifier verifier;

    public UsuarioService(@Autowired PhoneMapper phoneMapper, @Autowired UsuarioRepository usuarioRepository,
    @Autowired Properties properties, @Autowired JWTVerifier verifier) {
        this.phoneMapper = phoneMapper;
        this.usuarioRepository = usuarioRepository;
        this.properties = properties;
        this.verifier = verifier;
    }

    public SignUpResponseDto signUp(SignUpRequestDto request) {

        Usuario usuario = new Usuario();
        usuario.setPhones(phoneMapper.toEntities(request.getPhones(), usuario));
        usuario.setName(request.getName());
        usuario.setEmail(request.getEmail());
        try {
            usuario.setPassword(encodePassword(request.getPassword()));
        } catch (Exception e) {
            throw new InternalServerException();
        }

        usuario = usuarioRepository.save(usuario);

        SignUpResponseDto response = new SignUpResponseDto();
        response.setId(usuario.getId().toString());
        response.setCreated(usuario.getCreated());
        response.setIsActive(usuario.getIsActive());
        response.setLastLogin(usuario.getLastLogin());
        response.setToken(getToken());

        return response;

    }

    private String encodePassword(String rawPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), properties.getPasswordSalt(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return new String(factory.generateSecret(spec).getEncoded());

    }

    private String getToken() {

        Algorithm algorithm = Algorithm.HMAC256(properties.getSecret());

        Date expirationDate = new Date(new Date().getTime() + properties.getExpiration());

        return JWT.create().withIssuer("GlobalLogic")
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    private void verifyToken(String token) {

        verifier.verify(token);

    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto request, String token) {

        Optional<Usuario> usuarioOpt = this.usuarioRepository.findByEmail(request.getEmail());

        Usuario usuario = usuarioOpt.orElseThrow(UserNotFoundException::new);

        if (request.getPassword() != null) {
            try {
                String loginPassword = encodePassword(request.getPassword());
                System.out.println(loginPassword);
                if (!loginPassword.equals(usuario.getPassword())) {
                    throw new PasswordNotValidException();
                }
            } catch (PasswordNotValidException e) {
                throw e;
            } catch (Exception e) {
                throw new InternalServerException();
            }
        } else {
            verifyToken(token);
        }

        LoginResponseDto response = new LoginResponseDto();
        response.setCreated(usuario.getCreated());
        response.setEmail(usuario.getEmail());
        response.setId(usuario.getId().toString());
        response.setLastLogin(usuario.getLastLogin());

        usuario.setLastLogin(LocalDateTime.now());

        response.setPassword(request.getPassword());
        response.setName(usuario.getName());
        response.setIsActive(usuario.getIsActive());
        response.setPhones(phoneMapper.toDtos(usuario.getPhones()));
        response.setToken(getToken());

        return response;
    }
}
