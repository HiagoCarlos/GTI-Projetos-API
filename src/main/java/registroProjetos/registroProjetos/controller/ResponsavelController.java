package registroProjetos.registroProjetos.controller;

import registroProjetos.registroProjetos.dto.request.ResponsavelRequestDTO;
import registroProjetos.registroProjetos.dto.resumo.ResponsavelResumoDTO;
import registroProjetos.registroProjetos.service.ResponsavelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/responsaveis")
@RequiredArgsConstructor
public class ResponsavelController {

    private final ResponsavelService responsavelService;

    @PostMapping
    public ResponseEntity<ResponsavelResumoDTO> criar(@Valid @RequestBody ResponsavelRequestDTO dto) {
        ResponsavelResumoDTO criado = responsavelService.criar(dto);
        return ResponseEntity.created(URI.create("/api/responsaveis/" + criado.getId())).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelResumoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(responsavelService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponsavelResumoDTO>> listarTodos() {
        return ResponseEntity.ok(responsavelService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsavelResumoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResponsavelRequestDTO dto
    ) {
        return ResponseEntity.ok(responsavelService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        responsavelService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}