package mx.edu.utez.simnaDatabase.services.historial;

import mx.edu.utez.simnaDatabase.config.ApiResponse;
import mx.edu.utez.simnaDatabase.models.historial.Historial;
import mx.edu.utez.simnaDatabase.models.historial.HistorialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HistorialService {
    private final HistorialRepository repository;

    public HistorialService(HistorialRepository repository) {
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
    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<Historial> historialOptional = repository.findById(id);
        if (historialOptional.isPresent()) {
            return new ResponseEntity<>(
                    new ApiResponse(historialOptional.get(), HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse("Historial no encontrado", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> save(Historial historial) {
        repository.save(historial);
        return new ResponseEntity<>(
                new ApiResponse("Historial creado exitosamente", HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @Transactional
    public ResponseEntity<ApiResponse> update(Long id, Historial historialDetails) {
        Optional<Historial> historialOptional = repository.findById(id);
        if (historialOptional.isPresent()) {
            Historial historial = historialOptional.get();
            historial.setFecharecopilacion(historialDetails.getFecharecopilacion());
            historial.setHorarecopilacion(historialDetails.getHorarecopilacion());
            historial.setNivelagua(historialDetails.getNivelagua());
            historial.setPozo(historialDetails.getPozo());
            repository.save(historial);
            return new ResponseEntity<>(
                    new ApiResponse("Historial actualizado exitosamente", HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse("Historial no encontrado", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> deleteById(Long id) {
        Optional<Historial> historialOptional = repository.findById(id);
        if (historialOptional.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(
                    new ApiResponse("Historial eliminado exitosamente", HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse("Historial no encontrado", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}