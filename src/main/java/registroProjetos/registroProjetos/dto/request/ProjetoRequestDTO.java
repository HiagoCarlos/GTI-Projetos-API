package registroProjetos.registroProjetos.dto.request;


import registroProjetos.registroProjetos.entity.CategoriaProjeto;
import registroProjetos.registroProjetos.entity.StatusProjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O responsável é obrigatório")
    private Long responsavelId;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaProjeto categoria;

    private StatusProjeto status;
}