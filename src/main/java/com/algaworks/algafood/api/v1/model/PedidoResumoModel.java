package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

    @Schema(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @Schema(example = "298.90")
    private BigDecimal subtotal;

    @Schema(example = "10.00")
    private BigDecimal taxaFrete;

    @Schema(example = "308.90")
    private BigDecimal valorTotal;

    @Schema(example = "CRIADO")
    private StatusPedido status;

    @Schema(example = "2023-05-03T17:18:37Z")
    private OffsetDateTime dataCriacao;

    private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente;

}
