package visita10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Pedido utiliza Produto nas operações de inclusão.
// Internamente, mantém seus objetos ItemPedido.
public final class Pedido {

    private final int numero;

    // A coleção representa a composição com ItemPedido.
    private final List<ItemPedido> itens =
        new ArrayList<>();

    public Pedido(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException(
                "O número do pedido deve ser positivo."
            );
        }

        this.numero = numero;
    }

    /*
     * SOBRECARGA 1
     *
     * Assinatura considerada pelo compilador:
     * adicionarItem(Produto)
     *
     * Quando somente o produto é informado,
     * o método considera uma unidade.
     */
    public void adicionarItem(Produto produto) {
        adicionarItem(produto, 1);
    }

    /*
     * SOBRECARGA 2
     *
     * Assinatura considerada pelo compilador:
     * adicionarItem(Produto, int)
     *
     * Esta versão recebe também a quantidade.
     */
    public void adicionarItem(
            Produto produto,
            int quantidade) {

        // Pedido cria e controla seu próprio ItemPedido.
        itens.add(new ItemPedido(produto, quantidade));
    }

    // Devolve uma cópia não modificável da coleção.
    public List<ItemPedido> consultarItens() {
        return List.copyOf(itens);
    }

    // Soma os subtotais de todos os itens.
    public BigDecimal calcularTotal() {
        return itens.stream()
            .map(ItemPedido::calcularSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Produz uma representação textual do pedido.
    public String gerarResumo() {
        return "Pedido %d — %d item(ns) — Total: R$ %s"
            .formatted(
                numero,
                itens.size(),
                calcularTotal()
                    .setScale(2)
                    .toPlainString()
                    .replace(".", ",")
            );
    }
}