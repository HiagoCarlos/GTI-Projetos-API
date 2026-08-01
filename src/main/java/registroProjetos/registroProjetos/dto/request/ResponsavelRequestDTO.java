package registroProjetos.registroProjetos.dto.request;

import registroProjetos.registroProjetos.entity.CargoResponsavel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsavelRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @Email(message = "E-mail inválido")
    @Size(max = 150)
    private String email;

    @NotNull(message = "O cargo é obrigatório")
    private CargoResponsavel cargo;

    @Size(max = 60)
    private String login;

    // Só valida o formato se vier preenchida (na edição, senha em branco = "não alterar senha")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
        message = "A senha deve ter ao menos 8 caracteres, 1 maiúscula, 1 número e 1 símbolo"
    )
    private String senha;
}