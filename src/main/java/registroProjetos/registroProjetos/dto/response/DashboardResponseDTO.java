package registroProjetos.registroProjetos.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {
    private long totalProjetos;
    private Map<String, Long> porStatus;
    private Map<String, Long> porCategoria;
}