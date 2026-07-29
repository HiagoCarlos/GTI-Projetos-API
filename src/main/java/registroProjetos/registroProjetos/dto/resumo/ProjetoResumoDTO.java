package registroProjetos.registroProjetos.dto.resumo;

import registroProjetos.registroProjetos.entity.CategoriaProjeto;
import registroProjetos.registroProjetos.entity.StatusProjeto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoResumoDTO {
    private Long id;
    private String titulo;
    private String responsavelNome;
    private CategoriaProjeto categoria;
    private StatusProjeto status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}