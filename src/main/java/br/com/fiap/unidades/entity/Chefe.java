package br.com.fiap.unidades.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="TB_CHEFE",
        uniqueConstraints = {
        @UniqueConstraint(
                        name = "UK_CHEFE_ATIVO",
                        columnNames= {
                                "usuario", "unidade", "fim"
                        }
                )})
public class Chefe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CHEFE")
    @SequenceGenerator(name = "SQ_CHEFE", sequenceName = "SQ_CHEFE", allocationSize = 1)
    @Column(name="id_chefe")
    private Long id;

    private Boolean substituto;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "usuario",
            referencedColumnName = "id_usuario",
            foreignKey = @ForeignKey(
                    name = "FK_CHEFE_USUARIO"
            )
    )
    private Usuario usuario;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "unidade",
            referencedColumnName = "id_unidade",
            foreignKey = @ForeignKey(
                    name = "FK_CHEFE_UNIDADE"
            )
    )
    private Unidade unidade;

    private LocalDateTime inicio;

    private LocalDateTime fim;

}
