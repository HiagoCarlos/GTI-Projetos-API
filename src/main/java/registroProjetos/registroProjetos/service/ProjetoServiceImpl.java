package registroProjetos.registroProjetos.service;

import registroProjetos.registroProjetos.dto.request.ProjetoRequestDTO;
import registroProjetos.registroProjetos.dto.response.ProjetoResponseDTO;
import registroProjetos.registroProjetos.dto.resumo.ProjetoResumoDTO;
import registroProjetos.registroProjetos.entity.CategoriaProjeto;
import registroProjetos.registroProjetos.entity.Projeto;
import registroProjetos.registroProjetos.entity.Responsavel;
import registroProjetos.registroProjetos.entity.StatusProjeto;
import registroProjetos.registroProjetos.exception.ResourceNotFoundException;
import registroProjetos.registroProjetos.mapper.ProjetoMapper;
import registroProjetos.registroProjetos.repository.ProjetoRepository;
import registroProjetos.registroProjetos.repository.ResponsavelRepository;
import registroProjetos.registroProjetos.specification.ProjetoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ProjetoMapper projetoMapper;

    @Override
    public ProjetoResponseDTO criar(ProjetoRequestDTO dto) {
        Responsavel responsavel = buscarResponsavel(dto.getResponsavelId());

        Projeto projeto = Projeto.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .responsavel(responsavel)
                .categoria(dto.getCategoria())
                .status(dto.getStatus())
                .build();

        Projeto salvo = projetoRepository.save(projeto);
        return projetoMapper.toResponseDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjetoResponseDTO buscarPorId(Long id) {
        Projeto projeto = buscarProjetoOuFalhar(id);
        return projetoMapper.toResponseDTO(projeto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjetoResumoDTO> listar(
            String titulo,
            Long responsavelId,
            CategoriaProjeto categoria,
            StatusProjeto status,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Pageable pageable
    ) {
        var specification = ProjetoSpecification.comFiltros(
                titulo, responsavelId, categoria, status, dataInicio, dataFim);

        return projetoRepository.findAll(specification, pageable)
                .map(projetoMapper::toResumoDTO);
    }

    @Override
    public ProjetoResponseDTO atualizar(Long id, ProjetoRequestDTO dto) {
        Projeto projeto = buscarProjetoOuFalhar(id);
        Responsavel responsavel = buscarResponsavel(dto.getResponsavelId());

        projeto.setTitulo(dto.getTitulo());
        projeto.setDescricao(dto.getDescricao());
        projeto.setResponsavel(responsavel);
        projeto.setCategoria(dto.getCategoria());
        if (dto.getStatus() != null) {
            projeto.setStatus(dto.getStatus());
        }

        Projeto atualizado = projetoRepository.save(projeto);
        return projetoMapper.toResponseDTO(atualizado);
    }

    @Override
    public void excluir(Long id) {
        Projeto projeto = buscarProjetoOuFalhar(id);
        projetoRepository.delete(projeto);
    }

    private Projeto buscarProjetoOuFalhar(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Projeto não encontrado com id: " + id));
    }

    private Responsavel buscarResponsavel(Long responsavelId) {
        return responsavelRepository.findById(responsavelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Responsável não encontrado com id: " + responsavelId));
    }
}