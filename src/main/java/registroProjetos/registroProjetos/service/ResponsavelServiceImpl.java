package registroProjetos.registroProjetos.service;

import registroProjetos.registroProjetos.dto.request.ResponsavelRequestDTO;
import registroProjetos.registroProjetos.dto.resumo.ResponsavelResumoDTO;
import registroProjetos.registroProjetos.entity.CargoResponsavel;
import registroProjetos.registroProjetos.entity.Responsavel;
import registroProjetos.registroProjetos.exception.RegraNegocioException;
import registroProjetos.registroProjetos.exception.ResourceNotFoundException;
import registroProjetos.registroProjetos.mapper.ResponsavelMapper;
import registroProjetos.registroProjetos.repository.ResponsavelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponsavelResumoDTO criar(ResponsavelRequestDTO dto) {
        String login = normalizar(dto.getLogin());
        String email = normalizar(dto.getEmail());
        validarLoginEmailUnicos(login, email, null);
        validarEscaladaDeCargo(dto.getCargo());

        Responsavel responsavel = Responsavel.builder()
                .nome(dto.getNome())
                .email(email)
                .cargo(dto.getCargo())
                .login(login)
                .senha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null)
                .ativo(true)
                .build();

        Responsavel salvo = responsavelRepository.save(responsavel);
        return responsavelMapper.toResumoDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsavelResumoDTO buscarPorId(Long id) {
        return responsavelMapper.toResumoDTO(buscarOuFalhar(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponsavelResumoDTO> listarTodos() {
        return responsavelRepository.findByAtivoTrue()
                .stream()
                .map(responsavelMapper::toResumoDTO)
                .toList();
    }

    @Override
    public ResponsavelResumoDTO atualizar(Long id, ResponsavelRequestDTO dto) {
        Responsavel responsavel = buscarOuFalhar(id);
        String loginAtorLogado = usuarioLogadoLogin();
        boolean isSelf = responsavel.getLogin() != null && responsavel.getLogin().equalsIgnoreCase(loginAtorLogado);

        
        if (isSelf && dto.getCargo() != responsavel.getCargo()) {
            throw new RegraNegocioException("Você não pode alterar o próprio cargo");
        }

        
        validarEscaladaDeCargo(dto.getCargo());

        
        if (responsavel.getCargo() == CargoResponsavel.DIRETOR
                && dto.getCargo() != CargoResponsavel.DIRETOR) {
            validarNaoUltimoDiretor(responsavel);
        }

        String login = normalizar(dto.getLogin());
        String email = normalizar(dto.getEmail());
        validarLoginEmailUnicos(login, email, id);

        responsavel.setNome(dto.getNome());
        responsavel.setEmail(email);
        responsavel.setCargo(dto.getCargo());
        if (login != null) {
            responsavel.setLogin(login);
        }
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            responsavel.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Responsavel atualizado = responsavelRepository.save(responsavel);
        return responsavelMapper.toResumoDTO(atualizado);
    }

    @Override
    public void excluir(Long id) {
        Responsavel responsavel = buscarOuFalhar(id);

       
        String loginAtorLogado = usuarioLogadoLogin();
        if (responsavel.getLogin() != null && responsavel.getLogin().equalsIgnoreCase(loginAtorLogado)) {
            throw new RegraNegocioException("Você não pode excluir a própria conta");
        }

        
        validarNaoUltimoDiretor(responsavel);

       
        responsavel.setAtivo(false);
        responsavelRepository.save(responsavel);
    }

    private void validarNaoUltimoDiretor(Responsavel responsavel) {
        if (responsavel.getCargo() == CargoResponsavel.DIRETOR) {
            long diretoresAtivos = responsavelRepository.countByCargoAndAtivoTrue(CargoResponsavel.DIRETOR);
            if (diretoresAtivos <= 1) {
                throw new RegraNegocioException("Não é possível remover o último Diretor do sistema");
            }
        }
    }

    private void validarEscaladaDeCargo(CargoResponsavel cargoAlvo) {
        boolean alvoAlto = cargoAlvo == CargoResponsavel.DIRETOR || cargoAlvo == CargoResponsavel.VICE_DIRETOR;
        if (!alvoAlto) {
            return;
        }
        CargoResponsavel cargoAtor = cargoDoLogin(usuarioLogadoLogin());
        if (cargoAtor != CargoResponsavel.DIRETOR) {
            throw new RegraNegocioException("Apenas o Diretor pode atribuir os cargos Diretor ou Vice-Diretor");
        }
    }


    private void validarLoginEmailUnicos(String login, String email, Long idAtual) {
        if (email != null) {
            responsavelRepository.findByEmail(email).ifPresent(existente -> {
                if (!existente.getId().equals(idAtual)) {
                    throw new RegraNegocioException("Já existe um responsável com esse e-mail");
                }
            });
        }
        if (login != null) {
            responsavelRepository.findByLogin(login).ifPresent(existente -> {
                if (!existente.getId().equals(idAtual)) {
                    throw new RegraNegocioException("Já existe um responsável com esse login");
                }
            });
        }
    }

    private String normalizar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim().toLowerCase();
    }

    private String usuarioLogadoLogin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : null;
    }

    private CargoResponsavel cargoDoLogin(String login) {
        return responsavelRepository.findByLogin(login).map(Responsavel::getCargo).orElse(null);
    }

    private Responsavel buscarOuFalhar(Long id) {
        return responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado com id: " + id));
    }
}