package registroProjetos.registroProjetos.repository;

import registroProjetos.registroProjetos.entity.Responsavel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
    Optional<Responsavel> findByLogin(String login);
}