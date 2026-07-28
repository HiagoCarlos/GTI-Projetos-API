package registroProjetos.registroProjetos.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import registroProjetos.registroProjetos.dto.resumo.ResponsavelResumoDTO;
import registroProjetos.registroProjetos.entity.CategoriaProjeto;
import registroProjetos.registroProjetos.entity.StatusProjeto;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private ResponsavelResumoDTO responsavel;
    private CategoriaProjeto categoria;
    private StatusProjeto status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}