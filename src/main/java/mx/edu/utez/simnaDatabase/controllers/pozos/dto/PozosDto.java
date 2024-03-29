package mx.edu.utez.simnaDatabase.controllers.pozos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;

@Getter
@Setter
@NoArgsConstructor
public class PozosDto {
    private Long id;
    private String nombre;
    private Double profundidad;
    private Double capacidadLitros;
    private Integer porcentajeAgua;
    private Boolean estatus;

    public PozosDto(Long id, String nombre, Double profundidad, Double capacidadLitros, Integer porcentajeAgua, Boolean estatus) {
        this.id = id;
        this.nombre = nombre;
        this.profundidad = profundidad;
        this.capacidadLitros = capacidadLitros;
        this.porcentajeAgua = porcentajeAgua;
        this.estatus = estatus;
    }

    // Constructor para crear un DTO a partir de una entidad Pozos
    public static PozosDto fromPozo(Pozos pozo) {
        return new PozosDto(
                pozo.getId(),
                pozo.getNombre(),
                pozo.getProfundidad(),
                pozo.getCapacidadlitros(),
                pozo.getPorcentajeagua(),
                pozo.getEstatus()
        );
    }

    public Pozos toEntity() {
        Pozos pozo = new Pozos();
        pozo.setId(this.id);
        pozo.setNombre(this.nombre);
        pozo.setProfundidad(this.profundidad);
        pozo.setCapacidadlitros(this.capacidadLitros);
        pozo.setPorcentajeagua(this.porcentajeAgua);
        pozo.setEstatus(this.estatus);
        return pozo;
    }

}
