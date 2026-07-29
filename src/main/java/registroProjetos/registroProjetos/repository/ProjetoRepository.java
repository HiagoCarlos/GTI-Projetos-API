package registroProjetos.registroProjetos.repository;

import registroProjetos.registroProjetos.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>, JpaSpecificationExecutor<Projeto> {

    @Query("SELECT p.status AS chave, COUNT(p) AS total FROM Projeto p GROUP BY p.status")
    List<ContagemProjecao> contarPorStatus();

    @Query("SELECT p.categoria AS chave, COUNT(p) AS total FROM Projeto p GROUP BY p.categoria")
    List<ContagemProjecao> contarPorCategoria();

    interface ContagemProjecao {
        String getChave();
        Long getTotal();
    }
}