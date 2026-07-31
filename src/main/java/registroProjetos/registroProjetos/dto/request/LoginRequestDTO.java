package registroProjetos.registroProjetos.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Informe o login")
    private String login;

    @NotBlank(message = "Informe a senha")
    private String senha;
}