package br.com.fiap.cp02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TB_PRODUTO")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    @Column(name = "ds_nome", nullable = false)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres")
    @Column(name = "ds_descricao", nullable = false)
    private String descricao;

    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    @Column(name = "vl_preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Column(name = "nr_quantidade", precision = 4)
    private Integer quantidade;

    @Column(name = "st_disponivel")
    private Boolean disponivel;

    @Column(name = "dt_fabricacao")
    private LocalDate dataFabricacao;

    @Size(max = 50, message = "A categoria deve ter no máximo 50 caracteres")
    @Column(name = "ds_categoria")
    private String categoria;
}
