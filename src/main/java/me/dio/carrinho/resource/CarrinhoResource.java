package me.dio.carrinho.resource;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.carrinho.model.Carrinho;
import me.dio.carrinho.model.Item;
import me.dio.carrinho.resource.dto.ItemDto;
import me.dio.carrinho.service.CarrinhoService;
import org.springframework.web.bind.annotation.*;

@Api(value="/ifood-devweek/carrinho")
@RestController
@RequestMapping("/ifood-devweek/carrinho")
@RequiredArgsConstructor
public class CarrinhoResource {
    private final CarrinhoService carrinhoService;


    @PostMapping
    public Item incluirItemNaSacola(@RequestBody ItemDto itemDto) {
        return carrinhoService.incluirItemNoCarrinho(itemDto);
    }

    @GetMapping("/{id}")
    public Carrinho verCarrinho(@PathVariable("id") Long id) {
        return carrinhoService.verCarrinho(id);
    }

    @PatchMapping("/fecharCarrinho/{carrinhoId}")
    public Carrinho fecharCarrinho(@PathVariable("carrinhoId") Long carrinhoId, @RequestParam("formaPagamento") int formaPagamento) {
        return carrinhoService.fecharCarrinho(carrinhoId, formaPagamento);
    }
}
