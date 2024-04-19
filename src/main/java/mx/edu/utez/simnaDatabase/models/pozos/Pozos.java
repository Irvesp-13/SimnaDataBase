package mx.edu.utez.simnaDatabase.models.pozos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.simnaDatabase.models.historial.Historial;
import mx.edu.utez.simnaDatabase.models.person.Person;

import java.util.List;

@Entity
@Table(name = "pozos")
@NoArgsConstructor
@Setter
@Getter
public class Pozos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Double capacidadlitros;
    @Column
    private Integer porcentajeagua;
    @Column(nullable = false)
    private String ubicacionpozo;
    @Column(nullable = false)
    private String comunidades;
    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean estatus; //Wrappers boolean

    @OneToMany(mappedBy = "pozo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Historial> historial;

    @ManyToMany(mappedBy = "pozos")
    @JsonIgnore
    private List<Person> personas;

    public Pozos(String nombre, Double capacidadlitros, Integer porcentajeagua) {
        this.nombre = nombre;
        this.capacidadlitros = capacidadlitros;
        this.porcentajeagua = porcentajeagua;
    }

    public Pozos(Long id, String nombre, Double capacidadlitros, Integer porcentajeagua) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadlitros = capacidadlitros;
        this.porcentajeagua = porcentajeagua;
    }
}
