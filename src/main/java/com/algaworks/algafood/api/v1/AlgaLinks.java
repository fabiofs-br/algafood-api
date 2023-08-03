package com.algaworks.algafood.api.v1;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.api.v1.controller.FluxoPedidoController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToEstatisticas(String relation) {
        return linkTo(EstatisticasController.class).withRel(relation);
    }

    public Link linkToEstatisticas() {
        return linkToEstatisticas(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstatisticasVendasDiarias(String relation) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("restauranteI", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(methodOn(EstatisticasController.class)
                .consultarVendasDiarias(null, null)).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), relation);
    }

    public Link linkToPedidos(String relation) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), relation);
    }

    public Link linkToPedidos() {
        return linkToPedidos(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(relation);
    }

    public Link linkToResaturanteInativacao(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(relation);
    }

    public Link linkToResaturanteAbertura(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(relation);
    }

    public Link linkToResaturanteFechamento(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(relation);
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String relation) {
        return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido)).withRel(relation);
    }

    public Link linkToEntregarPedido(String codigoPedido, String relation) {
        return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido)).withRel(relation);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String relation) {
        return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido)).withRel(relation);
    }

    public Link linkToCidade(Long cidadeId, String relation) {
        return linkTo(methodOn(CidadeController.class).buscar(cidadeId)).withRel(relation);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String relation) {
        return linkTo(CidadeController.class).withRel(relation);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurante(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).buscar(restauranteId)).withRel(relation);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteResponsaveis(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteResponsaveis(Long restauranteId) {
        return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String relation) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId, formaPagamentoId)).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId, null)).withRel(relation);
    }

    public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String relation) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .desassociar(restauranteId, usuarioId)).withRel(relation);
    }

    public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .associar(restauranteId, null)).withRel(relation);
    }

    public Link linkToRestaurantes(String relation) {
        String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), relation);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String relation) {
        return linkTo(UsuarioController.class).withRel(relation);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuario(Long ususarioId, String relation) {
        return linkTo(methodOn(UsuarioController.class).buscar(ususarioId)).withRel(relation);
    }

    public Link linkToUsuario(Long ususarioId) {
        return linkToUsuario(ususarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String relation) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .desassociar(usuarioId, grupoId)).withRel(relation);
    }

    public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String relation) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .associar(usuarioId, null)).withRel(relation);
    }

    public Link linkToGruposUsuario(Long usuarioId, String relation) {
        return linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioId)).withRel(relation);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String relation) {
        return linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null)).withRel(relation);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(String relation) {
        return linkTo(FormaPagamentoController.class).withRel(relation);
    }

    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String relation) {
        return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withRel(relation);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFotoProduto(Long restauranteId, Long produtoId, String relation) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class).buscar(restauranteId, produtoId)).withRel(relation);
    }

    public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
        return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProdutos(Long restauranteId) {
        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProdutos(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null)).withRel(relation);
    }

    public Link linkToEstado(Long estadoId, String relation) {
        return linkTo(methodOn(EstadoController.class).buscar(estadoId)).withRel(relation);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String relation) {
        return linkTo(EstadoController.class).withRel(relation);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long cozinhaId, String relation) {
        return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId)).withRel(relation);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String relation) {
        return linkTo(CozinhaController.class).withRel(relation);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupos(String relation) {
        return linkTo(GrupoController.class).withRel(relation);
    }

    public Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissoes(Long grupoId) {
        return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissoes(Long grupoId, String relation) {
        return linkTo(methodOn(GrupoPermissaoController.class).listar(grupoId)).withRel(relation);
    }

    public Link linkToPermissoes() {
        return linkToPermissoes(IanaLinkRelations.SELF.value());
    }

    public Link linkToPermissoes(String relation) {
        return linkTo(methodOn(PermissaoController.class).listar()).withRel(relation);
    }

    public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String relation) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .desassociar(grupoId, permissaoId)).withRel(relation);
    }

    public Link linkToGrupoPermissaoAssociacao(Long grupoId, String relation) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .associar(grupoId, null)).withRel(relation);
    }

}
