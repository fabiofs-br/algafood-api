package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
//import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

//@ApiModel("RestaurantesBasicoModel")
@Data
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesBasicoEmbeddedModelOpenApi _embedded;
    private Links _links;

//    @ApiModel("RestaurantesBasicoEmbeddedModel")
    @Data
    public class RestaurantesBasicoEmbeddedModelOpenApi {

        private List<RestauranteBasicoModel> restaurantes;

    }

}
