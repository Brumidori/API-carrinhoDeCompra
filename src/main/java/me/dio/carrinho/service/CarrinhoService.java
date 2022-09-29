package me.dio.carrinho.service;

import me.dio.carrinho.model.Carrinho;
import me.dio.carrinho.model.Item;
import me.dio.carrinho.resource.dto.ItemDto;

public interface CarrinhoService {
    Carrinho verCarrinho(Long id);

    Carrinho fecharCarrinho(Long id, int formaPagamento);

    Item incluirItemNoCarrinho(ItemDto itemDto);
}
