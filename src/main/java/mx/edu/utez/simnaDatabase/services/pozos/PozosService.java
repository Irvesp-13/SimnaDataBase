package mx.edu.utez.simnaDatabase.services.pozos;

import mx.edu.utez.simnaDatabase.config.ApiResponse;
import mx.edu.utez.simnaDatabase.controllers.pozos.dto.PozosDto;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;
import mx.edu.utez.simnaDatabase.models.pozos.PozosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PozosService {
    private final PozosRepository repository;

    public PozosService(PozosRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findAll() {
        return new ResponseEntity<>(
                new ApiResponse(repository.findAll(), HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @Transactional(readOnly = true)
    public Pozos findById(Long id) {
        Optional<Pozos> pozoOptional = repository.findById(id);
        return pozoOptional.orElse(null);
    }

    @Transactional
    public ResponseEntity<PozosDto> save(Pozos pozo) {
        // Guardar el pozo en la base de datos
        Pozos savedPozo = repository.save(pozo);

        // Convertir el pozo guardado a un DTO y devolverlo en un ResponseEntity con código 200 (OK)
        return ResponseEntity.ok(PozosDto.fromPozo(savedPozo));
    }

    @Transactional
    public ResponseEntity<PozosDto> update(Pozos pozo) {
        Optional<Pozos> optionalPozo = repository.findById(pozo.getId());
        if (optionalPozo.isEmpty()) {
            // Si no se encuentra el pozo, devolver un ResponseEntity con código 404 (NOT FOUND)
            return ResponseEntity.notFound().build();
        }

        Pozos existingPozo = optionalPozo.get();

        // Actualizar los campos del pozo existente con los valores del DTO
        existingPozo.setNombre(pozo.getNombre());
        existingPozo.setProfundidad(pozo.getProfundidad());
        existingPozo.setCapacidadlitros(pozo.getCapacidadlitros());
        existingPozo.setPorcentajeagua(pozo.getPorcentajeagua());
        existingPozo.setEstatus(pozo.getEstatus());

        // Guardar el pozo actualizado en la base de datos
        Pozos updatedPozo = repository.save(existingPozo);

        // Convertir el pozo actualizado a un DTO y devolverlo en un ResponseEntity con código 200 (OK)
        return ResponseEntity.ok(PozosDto.fromPozo(updatedPozo));
    }

    @Transactional
    public boolean deleteById(Long id) {
        try {
            repository.deleteById(id);
            return true; // Devolver true si la eliminación fue exitosa
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Devolver false si se produce un error al eliminar
        }
    }
}
