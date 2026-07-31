package registroProjetos.registroProjetos.controller;

import registroProjetos.registroProjetos.dto.request.LoginRequestDTO;
import registroProjetos.registroProjetos.dto.response.LoginResponseDTO;
import registroProjetos.registroProjetos.entity.Responsavel;
import registroProjetos.registroProjetos.exception.CredenciaisInvalidasException;
import registroProjetos.registroProjetos.repository.ResponsavelRepository;
import registroProjetos.registroProjetos.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ResponsavelRepository responsavelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Responsavel responsavel = responsavelRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new CredenciaisInvalidasException("Login ou senha inválidos"));

        if (responsavel.getSenha() == null
                || !passwordEncoder.matches(dto.getSenha(), responsavel.getSenha())) {
            throw new CredenciaisInvalidasException("Login ou senha inválidos");
        }

        String token = jwtService.gerarToken(responsavel.getLogin(), responsavel.getCargo().name());

        return ResponseEntity.ok(LoginResponseDTO.builder()
                .token(token)
                .nome(responsavel.getNome())
                .cargo(responsavel.getCargo().name())
                .build());
    }
}