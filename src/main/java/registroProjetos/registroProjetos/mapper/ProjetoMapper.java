package registroProjetos.registroProjetos.mapper;

import registroProjetos.registroProjetos.dto.resumo.ProjetoResumoDTO;
import registroProjetos.registroProjetos.dto.response.ProjetoResponseDTO;
import registroProjetos.registroProjetos.entity.Projeto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjetoMapper {

    private final ResponsavelMapper responsavelMapper;

    public ProjetoResponseDTO toResponseDTO(Projeto projeto) {
        if (projeto == null) {
            return null;
        }
        return ProjetoResponseDTO.builder()
                .id(projeto.getId())
                .titulo(projeto.getTitulo())
                .descricao(projeto.getDescricao())
                .responsavel(responsavelMapper.toResumoDTO(projeto.getResponsavel()))
                .categoria(projeto.getCategoria())
                .status(projeto.getStatus())
                .dataCriacao(projeto.getDataCriacao())
                .dataAtualizacao(projeto.getDataAtualizacao())
                .build();
    }
public ProjetoResumoDTO toResumoDTO(Projeto projeto) {
    if (projeto == null) {
        return null;
    }
    return ProjetoResumoDTO.builder()
            .id(projeto.getId())
            .titulo(projeto.getTitulo())
            .descricao(projeto.getDescricao())
            .responsavelNome(projeto.getResponsavel() != null ? projeto.getResponsavel().getNome() : null)
            .categoria(projeto.getCategoria())
            .status(projeto.getStatus())
            .dataCriacao(projeto.getDataCriacao())
            .dataAtualizacao(projeto.getDataAtualizacao())
            .build();
}
}