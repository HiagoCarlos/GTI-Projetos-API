package registroProjetos.registroProjetos.service;

import registroProjetos.registroProjetos.dto.request.ResponsavelRequestDTO;
import registroProjetos.registroProjetos.dto.resumo.ResponsavelResumoDTO;
import registroProjetos.registroProjetos.entity.Responsavel;
import registroProjetos.registroProjetos.exception.ResourceNotFoundException;
import registroProjetos.registroProjetos.mapper.ResponsavelMapper;
import registroProjetos.registroProjetos.repository.ResponsavelRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResponsavelServiceImpl implements ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final ResponsavelMapper responsavelMapper;
    private final PasswordEncoder passwordEncoder;

    

    @Override
    @Transactional(readOnly = true)
    public ResponsavelResumoDTO buscarPorId(Long id) {
        return responsavelMapper.toResumoDTO(buscarOuFalhar(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponsavelResumoDTO> listarTodos() {
        return responsavelRepository.findAll()
                .stream()
                .map(responsavelMapper::toResumoDTO)
                .toList();
    }

    @Override
    public ResponsavelResumoDTO atualizar(Long id, ResponsavelRequestDTO dto) {
        Responsavel responsavel = buscarOuFalhar(id);
        responsavel.setNome(dto.getNome());
        responsavel.setEmail(dto.getEmail());

        Responsavel atualizado = responsavelRepository.save(responsavel);
        return responsavelMapper.toResumoDTO(atualizado);
    }

    @Override
    public void excluir(Long id) {
        Responsavel responsavel = buscarOuFalhar(id);
        responsavelRepository.delete(responsavel);
    }

    private Responsavel buscarOuFalhar(Long id) {
        return responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Responsável não encontrado com id: " + id));
    }

    @Override
public ResponsavelResumoDTO criar(ResponsavelRequestDTO dto) {
    Responsavel responsavel = Responsavel.builder()
            .nome(dto.getNome())
            .email(dto.getEmail())
            .cargo(dto.getCargo())
            .login(dto.getLogin())
            .senha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null)
            .build();

    Responsavel salvo = responsavelRepository.save(responsavel);
    return responsavelMapper.toResumoDTO(salvo);
}
}