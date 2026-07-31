package registroProjetos.registroProjetos.dto.request;

import registroProjetos.registroProjetos.entity.CargoResponsavel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsavelRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @Size(max = 150)
    private String email;

    @NotNull(message = "O cargo é obrigatório")
    private CargoResponsavel cargo;

    @Size(max = 60)
    private String login;

    @Size(min = 4, max = 100, message = "A senha deve ter ao menos 4 caracteres")
    private String senha;
}