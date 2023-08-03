package com.algaworks.algafood.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "security_auth")
public interface FluxoPedidoControllerOpenApi {

    @Operation(summary = "Confirmação de pedido por código", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> confirmar(@Parameter(description = "Código de um pedido",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

    @Operation(summary = "Cancelamento de pedido por código", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido cancelado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> cancelar(@Parameter(description =  "Código de um pedido",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

    @Operation(summary = "Registrar entrega de pedido por código", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido entregue"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> entregar(@Parameter(description = "Código de um pedido",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);
}
