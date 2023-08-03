package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private FotoStorageService fotoStorage;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;


    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                          @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoSalva);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@PathVariable Long restauranteId,
                                   @PathVariable Long produtoId) {
        FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

        return ResponseEntity.ok(fotoProdutoModelAssembler.toModel(fotoProduto));
    }

    @Override
    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> servir(@PathVariable Long restauranteId,
                                    @PathVariable Long produtoId,
                                    @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            return recuperarFoto(restauranteId, produtoId, acceptHeader);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<?> buscar(@PathVariable Long restauranteId,
//                                    @PathVariable Long produtoId,
//                                    @RequestHeader(name = "accept") String acceptHeader)
//            throws HttpMediaTypeNotAcceptableException {
//        if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
//            return recuperarInformacoesFoto(restauranteId, produtoId);
//        }
//
//        try {
//            return recuperarFoto(restauranteId, produtoId, acceptHeader);
//        } catch (EntidadeNaoEncontradaException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    private ResponseEntity<?> recuperarFoto(Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

        MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
        List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);

        verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypeAceitas);

        FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

        if (fotoRecuperada.temUrl()) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                    .build();
        } else {
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
        }
    }

//    private ResponseEntity<?> recuperarInformacoesFoto(Long restauranteId, Long produtoId) {
//        FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
//
//        return ResponseEntity.ok(fotoProdutoModelAssembler.toModel(fotoProduto));
//    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas)
            throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypeAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
        }

    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
//    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProduto.excluir(restauranteId, produtoId);
    }

}
