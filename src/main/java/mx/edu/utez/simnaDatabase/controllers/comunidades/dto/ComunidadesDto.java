package mx.edu.utez.simnaDatabase.controllers.comunidades.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.simnaDatabase.models.comunidades.Comunidades;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;

@NoArgsConstructor
@Setter
@Getter
public class ComunidadesDto {
    private Long id;
    private String nombre;
    private String municipio;
    private String codigoPostal;
    private Boolean status;
    private Long pozoid; // Id del pozo al que pertenece la comunidad

    public ComunidadesDto(Long id, String nombre, String municipio, String codigoPostal, Boolean status, Long pozoid) {
        this.id = id;
        this.nombre = nombre;
        this.municipio = municipio;
        this.codigoPostal = codigoPostal;
        this.status = status;
        this.pozoid = pozoid;
    }

    // Constructor para crear un DTO a partir de una entidad Comunidades
    public static ComunidadesDto fromComunidad(Comunidades comunidad) {
        return new ComunidadesDto(
                comunidad.getId(),
                comunidad.getNombre(),
                comunidad.getMunicipio(),
                comunidad.getCodigopostal(),
                comunidad.getStatus(),
                comunidad.getPozo().getId()
        );
    }

    public Comunidades toEntity() {
        Comunidades comunidad = new Comunidades();
        comunidad.setId(this.id);
        comunidad.setNombre(this.nombre);
        comunidad.setMunicipio(this.municipio);
        comunidad.setCodigopostal(this.codigoPostal);
        comunidad.setStatus(this.status);
        // No se establece 'pozoid' directamente en la entidad 'Comunidades',
        // en su lugar, se debe establecer el objeto 'Pozos'
        Pozos pozo = new Pozos();
        pozo.setId(this.pozoid);
        comunidad.setPozo(pozo);
        return comunidad;
    }
}
