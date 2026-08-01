package registroProjetos.registroProjetos.repository;

import registroProjetos.registroProjetos.entity.CargoResponsavel;
import registroProjetos.registroProjetos.entity.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
    Optional<Responsavel> findByLogin(String login);
    Optional<Responsavel> findByLoginAndAtivoTrue(String login);
    Optional<Responsavel> findByEmail(String email);
    List<Responsavel> findByAtivoTrue();
    long countByCargoAndAtivoTrue(CargoResponsavel cargo);
}