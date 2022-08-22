package ar.com.globallogic.ejercicio.controller;

import ar.com.globallogic.ejercicio.dto.LoginRequestDto;
import ar.com.globallogic.ejercicio.dto.LoginResponseDto;
import ar.com.globallogic.ejercicio.dto.SignUpRequestDto;
import ar.com.globallogic.ejercicio.dto.SignUpResponseDto;
import ar.com.globallogic.ejercicio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(@Autowired UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public SignUpResponseDto signUp(@RequestBody @Valid SignUpRequestDto request) {

        return this.usuarioService.signUp(request);

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto request, @RequestHeader String token) {

        return this.usuarioService.login(request, token);
    }
}
