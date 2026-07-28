package registroProjetos.registroProjetos.service;

import registroProjetos.registroProjetos.dto.request.ResponsavelRequestDTO;
import registroProjetos.registroProjetos.dto.resumo.ResponsavelResumoDTO;

import java.util.List;

public interface ResponsavelService {

    ResponsavelResumoDTO criar(ResponsavelRequestDTO dto);

    ResponsavelResumoDTO buscarPorId(Long id);

    List<ResponsavelResumoDTO> listarTodos();

    ResponsavelResumoDTO atualizar(Long id, ResponsavelRequestDTO dto);

    void excluir(Long id);
}