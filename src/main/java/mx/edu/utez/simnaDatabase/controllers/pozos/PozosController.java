package mx.edu.utez.simnaDatabase.controllers.pozos;

import mx.edu.utez.simnaDatabase.controllers.pozos.dto.PozosDto;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;
import mx.edu.utez.simnaDatabase.services.pozos.PozosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pozos")
public class PozosController {
    private final PozosService pozosService;

    public PozosController(PozosService pozosService) {
        this.pozosService = pozosService;
    }

    @GetMapping
    public ResponseEntity<List<PozosDto>> getAllPozos() {
        List<Pozos> pozos = (List<Pozos>) pozosService.findAll().getBody().getData(); // Obtener todos los pozos
        List<PozosDto> pozosDtoList = pozos.stream()
                .map(PozosDto::fromPozo) // Convertir cada pozo a su DTO correspondiente
                .collect(Collectors.toList());
        return ResponseEntity.ok(pozosDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PozosDto> getPozoById(@PathVariable Long id) {
        Pozos pozo = pozosService.findById(id); // Obtener el pozo por su ID
        if (pozo != null) {
            return ResponseEntity.ok(PozosDto.fromPozo(pozo)); // Devolver el DTO del pozo encontrado
        } else {
            return ResponseEntity.notFound().build(); // Devolver not found si no se encuentra el pozo
        }
    }

    @PostMapping
    public ResponseEntity<PozosDto> createPozo(@RequestBody PozosDto pozoDto) {
        // Convertir el DTO a la entidad Pozos
        Pozos pozo = pozoDto.toEntity();

        // Guardar el pozo y obtener el DTO del pozo guardado
        ResponseEntity<PozosDto> response = pozosService.save(pozo);

        // Devolver el DTO del pozo guardado
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PozosDto> updatePozo(@PathVariable Long id, @RequestBody PozosDto pozoDto) {
        // Convertir el DTO a la entidad Pozos
        Pozos pozo = pozoDto.toEntity();

        // Establecer el ID del pozo a actualizar
        pozo.setId(id);

        // Actualizar el pozo
        ResponseEntity<PozosDto> response = pozosService.update(pozo);

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePozo(@PathVariable Long id) {
        boolean deleted = pozosService.deleteById(id); // Eliminar el pozo por su ID
        if (deleted) {
            return ResponseEntity.noContent().build(); // Devolver no content si se eliminó correctamente
        } else {
            return ResponseEntity.notFound().build(); // Devolver not found si no se encuentra el pozo a eliminar
        }
    }
}
