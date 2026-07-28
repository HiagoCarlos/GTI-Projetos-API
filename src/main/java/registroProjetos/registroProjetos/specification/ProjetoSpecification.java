package registroProjetos.registroProjetos.specification;

import registroProjetos.registroProjetos.entity.CategoriaProjeto;
import registroProjetos.registroProjetos.entity.Projeto;
import registroProjetos.registroProjetos.entity.StatusProjeto;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ProjetoSpecification {

    public static Specification<Projeto> comFiltros(
            String titulo,
            Long responsavelId,
            CategoriaProjeto categoria,
            StatusProjeto status,
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    ) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (titulo != null && !titulo.isBlank()) {
                predicates = cb.and(predicates,
                        cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
            if (responsavelId != null) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("responsavel").get("id"), responsavelId));
            }
            if (categoria != null) {
                predicates = cb.and(predicates, cb.equal(root.get("categoria"), categoria));
            }
            if (status != null) {
                predicates = cb.and(predicates, cb.equal(root.get("status"), status));
            }
            if (dataInicio != null) {
                predicates = cb.and(predicates,
                        cb.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicio));
            }
            if (dataFim != null) {
                predicates = cb.and(predicates,
                        cb.lessThanOrEqualTo(root.get("dataCriacao"), dataFim));
            }
            return predicates;
        };
    }
}