package mx.edu.utez.simnaDatabase.models.historial;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "historial")
@NoArgsConstructor
@Setter
@Getter
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate fecharecopilacion;

    @Column(columnDefinition = "TIME", nullable = false)
    private LocalTime horarecopilacion;

    @Column(nullable = false)
    private Double nivelagua;

    @ManyToOne
    @JoinColumn(name = "pozos_id", nullable = false)
    private Pozos pozo;

    public Historial(LocalDate fecharecopilacion, LocalTime horarecopilacion, Double nivelagua) {
        this.fecharecopilacion = fecharecopilacion;
        this.horarecopilacion = horarecopilacion;
        this.nivelagua = nivelagua;
    }

    public Historial(Long id, LocalDate fecharecopilacion, LocalTime horarecopilacion, Double nivelagua, Pozos pozo) {
        this.id = id;
        this.fecharecopilacion = fecharecopilacion;
        this.horarecopilacion = horarecopilacion;
        this.nivelagua = nivelagua;
        this.pozo = pozo;
    }
}
