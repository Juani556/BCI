package ar.com.globallogic.ejercicio;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EjercicioApplication {

	@Bean
	public JWTVerifier getVerifier(@Value("${token.jwt.secret}") String secret) {

		Algorithm algorithm = Algorithm.HMAC256(secret);

		return JWT.require(algorithm).withIssuer("GlobalLogic").build();
	}


	public static void main(String[] args) {
		SpringApplication.run(EjercicioApplication.class, args);
	}

}


