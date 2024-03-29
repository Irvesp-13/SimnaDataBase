package mx.edu.utez.simnaDatabase.models.comunidades;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComunidadesRepository extends JpaRepository<Comunidades, Long> {
    Optional<Comunidades> findById(Long id);
}