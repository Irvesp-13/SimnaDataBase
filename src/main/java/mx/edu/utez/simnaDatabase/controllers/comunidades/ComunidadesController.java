package mx.edu.utez.simnaDatabase.controllers.comunidades;

import mx.edu.utez.simnaDatabase.config.ApiResponse;
import mx.edu.utez.simnaDatabase.controllers.comunidades.dto.ComunidadesDto;
import mx.edu.utez.simnaDatabase.services.comunidades.ComunidadesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comunidades")
@CrossOrigin(origins = {"*"})
public class ComunidadesController {
    private final ComunidadesService service;

    public ComunidadesController(ComunidadesService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody ComunidadesDto dto
    ) {
        return service.save(dto.toEntity());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ComunidadesDto dto
    ) {
        return service.update(id, dto.toEntity());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }
}

