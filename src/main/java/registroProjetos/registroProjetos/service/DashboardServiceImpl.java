package registroProjetos.registroProjetos.service;

import registroProjetos.registroProjetos.dto.response.DashboardResponseDTO;
import registroProjetos.registroProjetos.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final ProjetoRepository projetoRepository;

    @Override
    public DashboardResponseDTO gerarDashboard() {
        var porStatus = projetoRepository.contarPorStatus().stream()
                .collect(Collectors.toMap(
                        ProjetoRepository.ContagemProjecao::getChave,
                        ProjetoRepository.ContagemProjecao::getTotal
                ));

        var porCategoria = projetoRepository.contarPorCategoria().stream()
                .collect(Collectors.toMap(
                        ProjetoRepository.ContagemProjecao::getChave,
                        ProjetoRepository.ContagemProjecao::getTotal
                ));

        long total = projetoRepository.count();

        return DashboardResponseDTO.builder()
                .totalProjetos(total)
                .porStatus(porStatus)
                .porCategoria(porCategoria)
                .build();
    }
}