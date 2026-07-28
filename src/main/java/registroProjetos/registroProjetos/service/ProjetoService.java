package registroProjetos.registroProjetos.service;

import registroProjetos.registroProjetos.dto.request.ProjetoRequestDTO;
import registroProjetos.registroProjetos.dto.response.ProjetoResponseDTO;
import registroProjetos.registroProjetos.dto.resumo.ProjetoResumoDTO;
import registroProjetos.registroProjetos.entity.CategoriaProjeto;
import registroProjetos.registroProjetos.entity.StatusProjeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ProjetoService {

    ProjetoResponseDTO criar(ProjetoRequestDTO dto);

    ProjetoResponseDTO buscarPorId(Long id);

    Page<ProjetoResumoDTO> listar(
            String titulo,
            Long responsavelId,
            CategoriaProjeto categoria,
            StatusProjeto status,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Pageable pageable
    );

    ProjetoResponseDTO atualizar(Long id, ProjetoRequestDTO dto);

    void excluir(Long id);
}