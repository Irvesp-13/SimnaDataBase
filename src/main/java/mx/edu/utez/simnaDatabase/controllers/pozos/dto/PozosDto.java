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
    private Double capacidadLitros;
    private String porcentajeAgua;
    private String ubicacionPozo;
    private String comunidades;
    private Boolean estatus;

    public PozosDto(Long id,String nombre, Double capacidadLitros, String porcentajeAgua, String ubicacionPozo, String comunidades,Boolean estatus) {;
        this.id = id;
        this.nombre = nombre;
        this.capacidadLitros = capacidadLitros;
        this.porcentajeAgua = porcentajeAgua;
        this.ubicacionPozo = ubicacionPozo;
        this.comunidades = comunidades;
        this.estatus = estatus;
    }

    // Constructor para crear un DTO a partir de una entidad Pozos
    public static PozosDto fromPozo(Pozos pozo) {
        return new PozosDto(
                pozo.getId(),
                pozo.getNombre(),
                pozo.getCapacidadlitros(),
                pozo.getPorcentajeagua(),
                pozo.getUbicacionpozo(),
                pozo.getComunidades(),
                pozo.getEstatus()
        );
    }

    public Pozos toEntity() {
        Pozos pozo = new Pozos();
        pozo.setId(this.id);
        pozo.setNombre(this.nombre);
        pozo.setCapacidadlitros(this.capacidadLitros);
        pozo.setPorcentajeagua(this.porcentajeAgua);
        pozo.setUbicacionpozo(this.ubicacionPozo);
        pozo.setComunidades(this.comunidades);
        pozo.setEstatus(this.estatus);
        return pozo;
    }

}
