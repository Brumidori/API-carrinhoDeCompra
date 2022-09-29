package me.dio.carrinho.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.carrinho.enumeration.FormaPagamento;
import me.dio.carrinho.model.Carrinho;
import me.dio.carrinho.model.Item;
import me.dio.carrinho.model.Restaurante;
import me.dio.carrinho.repository.CarrinhoRepository;
import me.dio.carrinho.repository.ItemRepository;
import me.dio.carrinho.repository.ProdutoRepository;
import me.dio.carrinho.resource.dto.ItemDto;
import me.dio.carrinho.service.CarrinhoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarrinhoServiceImpl implements CarrinhoService {

   private final CarrinhoRepository carrinhoRepository;
   private final ProdutoRepository produtoRepository;
   private final ItemRepository itemRepository;

    @Override
    public Carrinho verCarrinho(Long id) {
        return carrinhoRepository.findById(id)
                .orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse carrinho não existe!");
                        }
                );
    }

    @Override
    public Carrinho fecharCarrinho(Long id, int numeroFormaPagamento) {
        Carrinho carrinho = verCarrinho(id);

        if (carrinho.getItens().isEmpty()){
            throw new RuntimeException("Inclua itens no carrinho");
        }

        FormaPagamento formaPagamento =
                numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        carrinho.setFormaPagamento(formaPagamento);
        carrinho.setFechada(true);
        return carrinhoRepository.save(carrinho);

    }

    @Override
    public Item incluirItemNoCarrinho(ItemDto itemDto) {
       Carrinho carrinho = verCarrinho(itemDto.getCarrinhoId());

       if (carrinho.isFechada()){
           throw new RuntimeException("Carrinho está fechado");
       }

        Item itemParaSerInserido = Item.builder()
               .quantidade(itemDto.getQuantidade())
               .carrinho(carrinho)
               .produto(produtoRepository.findById(itemDto.getProdutoId()).
                       orElseThrow(
                               () -> {
                                   throw new RuntimeException("Produto não encontrado");
                               })
                       )
               .build();

        List<Item> itensDoCarrinho = carrinho.getItens();

        if(itensDoCarrinho.isEmpty()){
            itensDoCarrinho.add(itemParaSerInserido);
        } else {

            Restaurante restauranteAtual =
            itensDoCarrinho.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdcionar = itemParaSerInserido.getProduto().getRestaurante();

            if(restauranteAtual.equals(restauranteDoItemParaAdcionar)){
                itensDoCarrinho.add(itemParaSerInserido);
            }else {
                throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferentes.");
            }

        }

        List<Double> valorDosItens = new ArrayList<>();
        for(Item itemDoCarrinho : itensDoCarrinho ) {
            double valorTotalItem =
                    itemDoCarrinho.getProduto().getValorUnitario() * itemDoCarrinho.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        Double valorTotalSacola = 0.0;

        for (Double valorDeCadaItem: valorDosItens) {
            valorTotalSacola += valorDeCadaItem;
        }

        carrinho.setValorTotal(valorTotalSacola);

        carrinhoRepository.save(carrinho);

        return itemParaSerInserido;
    }
}
