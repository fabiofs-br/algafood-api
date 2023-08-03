package com.algaworks.algafood;

import com.algaworks.algafood.core.io.Base64ProtocolResolver;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@ContextConfiguration(initializers = Base64ProtocolResolver.class) // Chama o conversor base64
public class CadastroRestauranteIT {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private final int RESTAURANTE_ID_INEXISTENTE = 100;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtDecoder jwtDecoder;

    private Restaurante burgerTopRestaurante;
    private String jsonRestauranteCorreto;
    private String jsonRestauranteComCozinhaInexistente;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteSemFrete;
    private int quantidadeRestaurantesCadastrados;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssuredMockMvc.basePath = "/v1/restaurantes";

        databaseCleaner.clearTables();
        jsonRestauranteCorreto = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-new-york-barbecue.json");
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");

        prepararDados();
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarStatus200_QuandoCosultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarQuantidadeCorretaDeRestaurantes_QuandoCosultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("_embedded.restaurantes", hasSize(quantidadeRestaurantesCadastrados));
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
                .body(jsonRestauranteCorreto)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
            .body(jsonRestauranteSemFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
            .body(jsonRestauranteSemCozinha)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
            .accept(ContentType.JSON)
            .when()
            .get("/{restauranteId}", burgerTopRestaurante.getId())
        .then()
            .statusCode(HttpStatus.OK.value())
        .body("nome", equalTo(burgerTopRestaurante.getNome()));
    }

    @Test
    @WithMockUser(
            username="joao.ger@algafood.com.br",
            authorities = {
                    "SCOPE_READ",
                    "SCOPE_WRITE",
                    "EDITAR_RESTAURANTES"
            }
    )
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}", RESTAURANTE_ID_INEXISTENTE)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Estado estadoMinasGerais = new Estado();
        estadoMinasGerais.setNome("Uberlândia");
        estadoRepository.save(estadoMinasGerais);

        Cidade cidadeUberlandia = new Cidade();
        cidadeUberlandia.setNome("Uberlândia");
        cidadeUberlandia.setEstado(estadoMinasGerais);
        cidadeRepository.save(cidadeUberlandia);

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);

        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);

        quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
    }

}
