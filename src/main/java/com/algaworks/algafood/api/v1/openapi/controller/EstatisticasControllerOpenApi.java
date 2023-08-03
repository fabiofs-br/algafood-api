package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.EstatisticasController.EstatisticasModel;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Estatísticas")
@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {

    @Operation(hidden = true)
    EstatisticasModel estatisticas();

    @Operation(summary = "Consulta estatísticas de vendas diárias", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendaDiaria.class))),
                    @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))
            }),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválida", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    @Parameter(
            in = ParameterIn.QUERY,
            name = "dataCriacaoInicio",
            example = "2023-05-11T00:00:00.00Z",
            description = "Data/hora inicial da criação do pedido",
            schema = @Schema(type = "string", format = "date-time")
    )
    @Parameter(
            in = ParameterIn.QUERY,
            name = "dataCriacaoFim",
            example = "2023-05-11T00:00:00.00Z",
            description = "Data/hora final da criação do pedido",
            schema = @Schema(type = "string", format = "date-time")
    )
    @Parameter(
            in = ParameterIn.QUERY,
            name = "restauranteId",
            example = "1",
            description = "ID do restaurante",
            schema = @Schema(type = "integer")
    )
    List<VendaDiaria> consultarVendasDiarias(
            @Parameter(hidden = true) VendaDiariaFilter filtro,
            @Parameter(
                    description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                    schema = @Schema(type = "string", defaultValue = "+00:00")
            ) String timeOffset);

    @Operation(hidden = true)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, String timeOffset);
}
