package me.dio.carrinho.repository;

import me.dio.carrinho.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// interface pontos de conexão com o banco de dados
// jpaRepository veio da dependencia instalada
@Repository
public interface CarrinhoRepository extends JpaRepository <Carrinho, Long>{


}
