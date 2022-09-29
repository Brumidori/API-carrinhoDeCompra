package me.dio.carrinho.resource.dto;

//resource é a classe controller - em Rest é melhor usar 'resource'

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Builder
@Data
@Embeddable //usa os atributos em outra tabela, nao cria uma tabela nova
@NoArgsConstructor
public class ItemDto {

    private Long produtoId;
    private int quantidade;
    private Long carrinhoId;
}
