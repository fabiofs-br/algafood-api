package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista restaurantes", parameters = {
            @Parameter(name = "projecao",
                    description = "Nome da projeção",
                    example = "apenas-nome",
                    in = ParameterIn.QUERY,
                    required = false
            )
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @Operation(summary = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    @Operation(summary = "Busca um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválida", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    RestauranteModel buscar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Cadastra um restaruante", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado"),
    })
    RestauranteModel adicionar(@RequestBody(description = "Representação de um novo restaurante", required = true)
                           RestauranteInput restauranteInput);

    @Operation(summary = "Atualiza uma restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    RestauranteModel atualizar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @RequestBody(description = "Representação de um restaurante com os novos dados", required = true)
            RestauranteInput restauranteInput);

    @Operation(summary = "Ativa um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante ativado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> ativar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Inativa um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante inativado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> inativar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Ativa múltiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    ResponseEntity<Void> ativarMultiplos(
            @RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);


    @Operation(summary = "Inativa múltiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
    })
    ResponseEntity<Void> inativarMultiplos(
            @RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

    @Operation(summary = "Abre um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante aberto"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> abrir(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Fecha um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante fechado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> fechar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);
}
