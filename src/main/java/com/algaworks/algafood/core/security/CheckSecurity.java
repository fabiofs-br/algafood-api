package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

    }

    public @interface Restaurantes {

        @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

        @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurante(#restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarFuncionamento { }

        @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurante()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarCadastro { }

    }

    public @interface Pedidos {

        @PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodePesquisar { }

        @PreAuthorize("@algaSecurity.podePesquisarPedidos()")
        @PostAuthorize("@algaSecurity.podePesquisarPedidos(returnObject.cliente.id, returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeBuscar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeCriar { }

        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciar { }

    }

    public @interface FormasPagamento {

        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar { }

    }

    public @interface Cidades {

        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar { }

    }

    public @interface Estados {

        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar { }

    }

    public @interface UsuarioGruposPermissoes {

        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeAlterarPropriaSenha { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or @algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeAlterarUsuario { }

    }

    public @interface Estatisticas {

        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }

    }

}
