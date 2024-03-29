package mx.edu.utez.simnaDatabase.controllers.historial.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.simnaDatabase.models.historial.Historial;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class HistorialDto {
    private Long id;
    private LocalDate fechaRecopilacion;
    private LocalTime horaRecopilacion;
    private Double nivelAgua;
    private Long pozoid; // Id del pozo asociado al historial

    public HistorialDto(Long id, LocalDate fechaRecopilacion, LocalTime horaRecopilacion, Double nivelAgua, Long pozoid) {
        this.id = id;
        this.fechaRecopilacion = fechaRecopilacion;
        this.horaRecopilacion = horaRecopilacion;
        this.nivelAgua = nivelAgua;
        this.pozoid = pozoid;
    }

    // Método para convertir un DTO de Historial a una entidad de Historial
    public Historial toEntity() {
        Historial historial = new Historial();
        historial.setId(this.id);
        historial.setFecharecopilacion(this.fechaRecopilacion);
        historial.setHorarecopilacion(this.horaRecopilacion);
        historial.setNivelagua(this.nivelAgua);
        // No establecemos el pozo directamente en este método, ya que solo tenemos su ID en el DTO.
        // Esto se manejará en el servicio correspondiente.
        return historial;
    }
}
