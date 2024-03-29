package mx.edu.utez.simnaDatabase.models.historial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {
    Optional<Historial> findById(Long id);
}
