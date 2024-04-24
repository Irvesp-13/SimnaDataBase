package mx.edu.utez.simnaDatabase.services.pozos;

import mx.edu.utez.simnaDatabase.config.ApiResponse;
import mx.edu.utez.simnaDatabase.controllers.pozos.dto.PozosDto;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;
import mx.edu.utez.simnaDatabase.models.pozos.PozosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.edu.utez.simnaDatabase.SimnaDatabaseApplication;

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
        existingPozo.setCapacidadlitros(pozo.getCapacidadlitros());
        existingPozo.setPorcentajeagua(SimnaDatabaseApplication.getMensajeRecibido());
        existingPozo.setUbicacionpozo(pozo.getUbicacionpozo());
        existingPozo.setComunidades(pozo.getComunidades());
        existingPozo.setEstatus(pozo.getEstatus());

        // Guardar el pozo actualizado en la base de datos
        Pozos updatedPozo = repository.save(existingPozo);

        // Convertir el pozo actualizado a un DTO y devolverlo en un ResponseEntity con código 200 (OK)
        return ResponseEntity.ok(PozosDto.fromPozo(updatedPozo));
    }



    @Transactional
    public ResponseEntity<PozosDto> updatePorc(Pozos pozo) {
        Optional<Pozos> optionalPozo = repository.findById(pozo.getId());
        if (optionalPozo.isEmpty()) {
            // Si no se encuentra el pozo, devolver un ResponseEntity con código 404 (NOT FOUND)
            return ResponseEntity.notFound().build();
        }

        Pozos existingPozo = optionalPozo.get();

        // Actualizar los campos del pozo existente con los valores del DTO
        existingPozo.setNombre(pozo.getNombre());
        existingPozo.setCapacidadlitros(pozo.getCapacidadlitros());
        if ("Pozo1".equals(existingPozo.getNombre()) || "pozo palo escrito".equals(existingPozo.getNombre()) ||
                "pozo paseos del rio".equals(existingPozo.getNombre()) || "pozo prohogar".equals(existingPozo.getNombre()) ||
                "pozo rezoyuca".equals(existingPozo.getNombre())) {
            existingPozo.setPorcentajeagua(SimnaDatabaseApplication.getMensajeRecibido());
            System.out.println("Se ha actualizado el porcentaje de agua del Pozo 1 correctamente cantidad: " + SimnaDatabaseApplication.getMensajeRecibido());
        } else if ("Pozo2".equals(existingPozo.getNombre()) || "pozo el organo".equals(existingPozo.getNombre()) ||
                "pozo el Capiri".equals(existingPozo.getNombre()) || "pozo calera".equals(existingPozo.getNombre()) ||
                "pozo centro".equals(existingPozo.getNombre())) {
            existingPozo.setPorcentajeagua(SimnaDatabaseApplication.getMensajeRecibido2());
            System.out.println("Se ha actualizado el porcentaje de agua del Pozo 2 correctamente cantidad: " + SimnaDatabaseApplication.getMensajeRecibido2());
        } else {
            System.out.println("El nombre del pozo no coincide con ninguna condición.");
            return ResponseEntity.notFound().build();
        }

        existingPozo.setUbicacionpozo(pozo.getUbicacionpozo());
        existingPozo.setComunidades(pozo.getComunidades());
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
