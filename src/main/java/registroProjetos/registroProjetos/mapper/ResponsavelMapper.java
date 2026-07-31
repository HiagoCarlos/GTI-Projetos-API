package registroProjetos.registroProjetos.mapper;

import registroProjetos.registroProjetos.dto.resumo.ResponsavelResumoDTO;
import registroProjetos.registroProjetos.entity.Responsavel;
import org.springframework.stereotype.Component;

@Component
public class ResponsavelMapper {

    public ResponsavelResumoDTO toResumoDTO(Responsavel responsavel) {
    if (responsavel == null) {
        return null;
    }
    return ResponsavelResumoDTO.builder()
            .id(responsavel.getId())
            .nome(responsavel.getNome())
            .cargo(responsavel.getCargo())
            .build();
}
}