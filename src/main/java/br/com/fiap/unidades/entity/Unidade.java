package br.com.fiap.unidades.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_UNIDADE", uniqueConstraints = {
        @UniqueConstraint(
                name = "UK_SIGLA_MACRO",
                columnNames = {
                        "sigla", "macro"
                }
        )})
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_UNIDADE")
    @SequenceGenerator(name = "SQ_UNIDADE", sequenceName = "SQ_UNIDADE", allocationSize = 1)
    @Column(name="id_unidade")
    private Long id;

    private String nome;

    private String sigla;

    private String descricao;

    @ManyToOne
    @JoinColumn(
            name="unidade",
            referencedColumnName = "id_unidade",
            foreignKey = @ForeignKey(name="FK_UNIDADE_MACRO")
    )
    private Unidade macro;

}
