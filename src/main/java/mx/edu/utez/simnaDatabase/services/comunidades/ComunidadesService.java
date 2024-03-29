package mx.edu.utez.simnaDatabase.services.comunidades;

import mx.edu.utez.simnaDatabase.config.ApiResponse;
import mx.edu.utez.simnaDatabase.models.comunidades.Comunidades;
import mx.edu.utez.simnaDatabase.models.comunidades.ComunidadesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ComunidadesService {
    private final ComunidadesRepository repository;

    public ComunidadesService(ComunidadesRepository repository) {
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
        Optional<Comunidades> comunidadOptional = repository.findById(id);
        if (comunidadOptional.isPresent()) {
            return new ResponseEntity<>(
                    new ApiResponse(comunidadOptional.get(), HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse("Comunidad no encontrada", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> save(Comunidades comunidad) {
        repository.save(comunidad);
        return new ResponseEntity<>(
                new ApiResponse("Comunidad creada exitosamente", HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @Transactional
    public ResponseEntity<ApiResponse> update(Long id, Comunidades comunidadDetails) {
        Optional<Comunidades> comunidadOptional = repository.findById(id);
        if (comunidadOptional.isPresent()) {
            Comunidades comunidad = comunidadOptional.get();
            comunidad.setNombre(comunidadDetails.getNombre());
            comunidad.setMunicipio(comunidadDetails.getMunicipio());
            comunidad.setCodigopostal(comunidadDetails.getCodigopostal());
            comunidad.setPozo(comunidadDetails.getPozo());
            repository.save(comunidad);
            return new ResponseEntity<>(
                    new ApiResponse("Comunidad actualizada exitosamente", HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse("Comunidad no encontrada", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> deleteById(Long id) {
        Optional<Comunidades> comunidadOptional = repository.findById(id);
        if (comunidadOptional.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(
                    new ApiResponse("Comunidad eliminada exitosamente", HttpStatus.OK),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse("Comunidad no encontrada", HttpStatus.NOT_FOUND),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}