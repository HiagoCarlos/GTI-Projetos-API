package registroProjetos.registroProjetos.controller;

import registroProjetos.registroProjetos.dto.request.ProjetoRequestDTO;
import registroProjetos.registroProjetos.dto.response.ProjetoResponseDTO;
import registroProjetos.registroProjetos.dto.resumo.ProjetoResumoDTO;
import registroProjetos.registroProjetos.entity.CategoriaProjeto;
import registroProjetos.registroProjetos.entity.StatusProjeto;
import registroProjetos.registroProjetos.service.ProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> criar(@Valid @RequestBody ProjetoRequestDTO dto) {
        ProjetoResponseDTO criado = projetoService.criar(dto);
        return ResponseEntity.created(URI.create("/api/projetos/" + criado.getId())).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProjetoResumoDTO>> listar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Long responsavelId,
            @RequestParam(required = false) CategoriaProjeto categoria,
            @RequestParam(required = false) StatusProjeto status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            Pageable pageable
    ) {
        Page<ProjetoResumoDTO> resultado = projetoService.listar(
                titulo, responsavelId, categoria, status, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProjetoRequestDTO dto
    ) {
        return ResponseEntity.ok(projetoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        projetoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}