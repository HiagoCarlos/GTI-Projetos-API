package registroProjetos.registroProjetos.dto.resumo;

import registroProjetos.registroProjetos.entity.CargoResponsavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelResumoDTO {
    private Long id;
    private String nome;
    private CargoResponsavel cargo;
}