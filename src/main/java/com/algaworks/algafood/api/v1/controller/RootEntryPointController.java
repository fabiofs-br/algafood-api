package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

    @Operation(hidden = true)
    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        podeConsultarCozinhas(rootEntryPointModel);
        podePesquisarPedidos(rootEntryPointModel);
        podeConsultarRestaurantes(rootEntryPointModel);
        podeConsultarUsuariosGruposPermissoes(rootEntryPointModel);
        podeConsultarFormasPagamento(rootEntryPointModel);
        podeConsultarEstados(rootEntryPointModel);
        podeConsultarCidades(rootEntryPointModel);
        podeConsultarEstatisticas(rootEntryPointModel);

        return rootEntryPointModel;
    }

    private void podeConsultarEstatisticas(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatísticas"));
        }
    }

    private void podeConsultarCidades(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
        }
    }

    private void podeConsultarEstados(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(algaLinks.linkToEstados("estados"));
        }
    }

    private void podeConsultarFormasPagamento(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
        }
    }

    private void podeConsultarUsuariosGruposPermissoes(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(algaLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(algaLinks.linkToPermissoes("permissoes"));
        }
    }

    private void podeConsultarRestaurantes(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        }
    }

    private void podePesquisarPedidos(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
        }
    }

    private void podeConsultarCozinhas(RootEntryPointModel rootEntryPointModel) {
        if (algaSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
        }
    }
}
