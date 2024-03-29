package mx.edu.utez.simnaDatabase.controllers.historial;

import mx.edu.utez.simnaDatabase.config.ApiResponse;
import mx.edu.utez.simnaDatabase.controllers.historial.dto.HistorialDto;
import mx.edu.utez.simnaDatabase.services.historial.HistorialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {

    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getHistorialById(@PathVariable Long id) {
        return historialService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createHistorial(@Valid @RequestBody HistorialDto historialDto) {
        return historialService.save(historialDto.toEntity());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateHistorial(@PathVariable Long id, @Valid @RequestBody HistorialDto historialDto) {
        return historialService.update(id, historialDto.toEntity());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteHistorial(@PathVariable Long id) {
        return historialService.deleteById(id);
    }
}
