package com.algaworks.algafood.core.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinksModel {

    private LinkModel rel;

    @Getter
    @Setter
    private class LinkModel {
        @Schema (example = "http://www.algafood.com.br/pedidos")
        private String href;
    }
}
