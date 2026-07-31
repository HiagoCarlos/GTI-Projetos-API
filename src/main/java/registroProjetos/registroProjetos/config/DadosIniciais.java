package registroProjetos.registroProjetos.config;

import registroProjetos.registroProjetos.entity.CargoResponsavel;
import registroProjetos.registroProjetos.entity.Responsavel;
import registroProjetos.registroProjetos.repository.ResponsavelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DadosIniciais implements CommandLineRunner {

    private final ResponsavelRepository responsavelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (responsavelRepository.findByLogin("diretor").isEmpty()) {
            responsavelRepository.save(Responsavel.builder()
                    .nome("Administrador")
                    .cargo(CargoResponsavel.DIRETOR)
                    .login("diretor")
                    .senha(passwordEncoder.encode("1234"))
                    .build());
        }
    }
}