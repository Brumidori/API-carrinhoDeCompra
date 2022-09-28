package me.dio.carrinho.model;
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
public class Endereco {

    private String cep;

    private String complemento;
}
