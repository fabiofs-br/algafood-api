package com.algaworks.algafood.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {

    @Schema(example = "2019-10-30", type = "string", format = "date")
    private Date data;

    @Schema(example = "10")
    private Long totalVendas;

    @Schema(example = "276.60")
    private BigDecimal totalFaturado;

    public VendaDiaria(java.sql.Date data, Long totalVendas, BigDecimal totalFaturado) {
        this.data = new Date(data.getTime());
        this.totalVendas = totalVendas;
        this.totalFaturado = totalFaturado;
    }
}
