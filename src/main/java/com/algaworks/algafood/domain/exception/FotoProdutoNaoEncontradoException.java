package com.algaworks.algafood.domain.exception;

import java.io.Serial;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d.",
                produtoId, restauranteId));
    }

}
