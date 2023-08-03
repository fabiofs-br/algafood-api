package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoFotoControllerOpenApi {

    @Operation(summary = "Atualiza a foto de um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Foto, produto ou restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    FotoProdutoModel atualizarFoto(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um produto", example = "1", required = true) Long produtoId,
            @RequestBody(required = true) FotoProdutoInput fotoProdutoInput) throws IOException;

    @Operation(summary = "Busca a foto de um produto de um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
            }),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválida", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Foto, produto ou restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<?> buscar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um produto", example = "1", required = true) Long produtoId);

    @Operation(hidden = true)
    ResponseEntity<?> servir(
            Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Exclui a foto de um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválida", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Foto, produto ou restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    void remover(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                 @Parameter(description = "ID de um produto", example = "1", required = true) Long produtoId);
}
