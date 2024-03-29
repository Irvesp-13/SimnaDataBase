package mx.edu.utez.simnaDatabase.models.pozos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PozosRepository extends JpaRepository<Pozos, Long> {
    Optional<Pozos> findByNombre(String nombre);
    Optional<Pozos> findById(Long id);
}
