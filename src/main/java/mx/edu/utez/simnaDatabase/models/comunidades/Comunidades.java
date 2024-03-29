package mx.edu.utez.simnaDatabase.models.comunidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;

@Entity
@Table(name = "comunidades")
@NoArgsConstructor
@Setter
@Getter
public class Comunidades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String nombre;
    @Column(length = 50, nullable = false)
    private String municipio;
    @Column(length = 50, nullable = false)
    private String codigopostal;
    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean status; //Wrappers boolean

    @ManyToOne
    @JoinColumn(name = "pozo_id", nullable = false) // Corrección del nombre de la columna
    private Pozos pozo;

    public Comunidades(String nombre, String municipio, String codigopostal) {
        this.nombre = nombre;
        this.municipio = municipio;
        this.codigopostal = codigopostal;
    }
}
