package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

            restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));

            restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        }

        if (algaSecurity.podeGerenciarCadastroRestaurante()) {
            restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));

            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(algaLinks.linkToResaturanteInativacao(restaurante.getId(), "inativar"));
            }
        }

        if (algaSecurity.podeGerenciarFuncionamentoRestaurante(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(algaLinks.linkToResaturanteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(algaLinks.linkToResaturanteFechamento(restaurante.getId(), "fechar"));
            }
        }

        if (algaSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        if(algaSecurity.podeConsultarCidades()) {
            if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade()
                        .add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(algaLinks.linkToRestaurantes());
        }

        return collectionModel;
    }

}
