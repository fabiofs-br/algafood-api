package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {

        // Para evitar:
        // org.hibernate.HibernateException:
        // identifier of an instance of com.algaworks.algafood.domain.model.FormaPagamento was altered from 1 to 2
        pedido.setFormaPagamento(new FormaPagamento());

        // Para evitar:
        // org.hibernate.HibernateException:
        // identifier of an instance of com.algaworks.algafood.domain.model.Restaurante was altered from 1 to 2
        pedido.setRestaurante(new Restaurante());

        modelMapper.map(pedidoInput, pedido);
    }

}
