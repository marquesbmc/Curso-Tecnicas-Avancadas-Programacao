package nucleo3;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa a escolha de um Produto dentro de um Pedido específico.
 *
 * POO — COMPOSIÇÃO:
 * ItemPedido é criado e controlado por Pedido. Seu construtor não é público,
 * reduzindo a possibilidade de criação fora desse contexto.
 */
public final class ItemPedido {

    // POO — ASSOCIAÇÃO/NAVEGABILIDADE:
    // ItemPedido mantém uma referência para o Produto registrado.
    private final Produto produto;
    private final int quantidade;
    private final BigDecimal valorUnitario;

    ItemPedido(Produto produto, int quantidade) {
        this.produto = Objects.requireNonNull(produto, "produto não pode ser nulo");
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser maior que zero");
        }
        this.quantidade = quantidade;

        // O preço é registrado no momento da compra.
        this.valorUnitario = produto.consultarPreco();
    }

    public Produto consultarProduto() {
        return produto;
    }

    public int consultarQuantidade() {
        return quantidade;
    }

    public BigDecimal consultarValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal calcularSubtotal() {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public String gerarResumo() {
        return "%dx %s = R$ %s".formatted(
                quantidade,
                produto.consultarNome(),
                calcularSubtotal().toPlainString()
        );
    }
}
