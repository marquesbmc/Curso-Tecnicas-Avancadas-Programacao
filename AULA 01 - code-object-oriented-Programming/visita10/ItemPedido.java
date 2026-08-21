package visita10;

import java.math.BigDecimal;
import java.util.Objects;

// Representa uma parte exclusiva de um Pedido.
public final class ItemPedido {

    // O item referencia o produto selecionado.
    private final Produto produto;

    // Registra a quantidade solicitada.
    private final int quantidade;

    // Somente Pedido pode criar seus itens.
    ItemPedido(Produto produto, int quantidade) {
        this.produto = Objects.requireNonNull(
            produto,
            "O produto é obrigatório."
        );

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "A quantidade deve ser maior que zero."
            );
        }

        this.quantidade = quantidade;
    }

    // Permite consultar o produto registrado.
    public Produto consultarProduto() {
        return produto;
    }

    // Permite consultar a quantidade registrada.
    public int consultarQuantidade() {
        return quantidade;
    }

    // Calcula o valor deste item.
    public BigDecimal calcularSubtotal() {
        return produto.consultarPreco()
            .multiply(BigDecimal.valueOf(quantidade));
    }
}