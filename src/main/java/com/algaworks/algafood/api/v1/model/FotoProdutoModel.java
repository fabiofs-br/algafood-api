package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "fotos")
@Setter
@Getter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

    @Schema(example = "1cb82c89-c819-48cd-b430-8b295a884ddb_prime-rib.jpg")
    private String nomeArquivo;

    @Schema(example = "Prime Rib ao ponto")
    private String descricao;

    @Schema(example = "image/jpeg")
    private String contentType;

    @Schema(example = "55688")
    private Long tamanho;

}
