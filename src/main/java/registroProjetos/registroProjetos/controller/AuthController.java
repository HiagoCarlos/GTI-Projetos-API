package registroProjetos.registroProjetos.controller;

import registroProjetos.registroProjetos.dto.request.LoginRequestDTO;
import registroProjetos.registroProjetos.dto.response.LoginResponseDTO;
import registroProjetos.registroProjetos.entity.Responsavel;
import registroProjetos.registroProjetos.exception.CredenciaisInvalidasException;
import registroProjetos.registroProjetos.repository.ResponsavelRepository;
import registroProjetos.registroProjetos.security.JwtService;
import registroProjetos.registroProjetos.security.TentativaLoginService;
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
    private final TentativaLoginService tentativaLoginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        String login = dto.getLogin().trim().toLowerCase();
        tentativaLoginService.validarNaoBloqueado(login);

        Responsavel responsavel = responsavelRepository.findByLoginAndAtivoTrue(login).orElse(null);

        if (responsavel == null || responsavel.getSenha() == null
                || !passwordEncoder.matches(dto.getSenha(), responsavel.getSenha())) {
            tentativaLoginService.registrarFalha(login);
            throw new CredenciaisInvalidasException("Login ou senha inválidos");
        }

        tentativaLoginService.registrarSucesso(login);
        String token = jwtService.gerarToken(responsavel.getLogin(), responsavel.getCargo().name());

        return ResponseEntity.ok(LoginResponseDTO.builder()
                .token(token)
                .nome(responsavel.getNome())
                .cargo(responsavel.getCargo().name())
                .build());
    }
}