package nucleo3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Raiz responsável por controlar os itens e as transições do pedido.
 */
public final class Pedido {

    private final int numero;

    // POO — ASSOCIAÇÃO E NAVEGABILIDADE:
    // Pedido conhece o Cliente que o realizou.
    private final Cliente cliente;

    // POO — COMPOSIÇÃO E MULTIPLICIDADE:
    // Um Pedido contém de zero a muitos ItemPedido.
    private final List<ItemPedido> itens = new ArrayList<>();

    // POO — ESTADO DO OBJETO:
    // O estado atual participa da validação de todas as operações importantes.
    private StatusPedido status;

    public Pedido(int numero, Cliente cliente) {
        if (numero <= 0) {
            throw new IllegalArgumentException("número deve ser maior que zero");
        }
        this.numero = numero;
        this.cliente = Objects.requireNonNull(cliente, "cliente não pode ser nulo");
        this.status = StatusPedido.EM_MONTAGEM;
    }

    // POO — SOBRECARGA:
    // Primeira forma: registra uma unidade.
    public void adicionarItem(Produto produto) {
        adicionarItem(produto, 1);
    }

    // POO — SOBRECARGA:
    // Segunda forma: registra a quantidade informada.
    public void adicionarItem(Produto produto, int quantidade) {
        validarEmMontagem();
        Objects.requireNonNull(produto, "produto não pode ser nulo");

        if (!produto.estaDisponivel()) {
            throw new IllegalStateException("produto indisponível: " + produto.consultarNome());
        }

        // POO — COMPOSIÇÃO:
        // O próprio Pedido cria e passa a controlar o ItemPedido.
        itens.add(new ItemPedido(produto, quantidade));
    }

    public BigDecimal calcularTotal() {
        return itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void confirmar() {
        validarEmMontagem();

        // POO — INVARIANTE:
        // Um pedido vazio não pode ser confirmado.
        if (itens.isEmpty()) {
            throw new IllegalStateException("pedido vazio não pode ser confirmado");
        }

        status = StatusPedido.CONFIRMADO;
    }

    public void descartar() {
        validarEmMontagem();
        status = StatusPedido.DESCARTADO;

        // POO — CICLO DE VIDA NA COMPOSIÇÃO:
        // Ao descartar o todo durante a montagem, suas partes são removidas.
        itens.clear();
    }

    public int consultarNumero() {
        return numero;
    }

    public Cliente consultarCliente() {
        return cliente;
    }

    public StatusPedido consultarStatus() {
        return status;
    }

    // A coleção interna não é exposta para modificação direta.
    public List<ItemPedido> consultarItens() {
        return List.copyOf(itens);
    }

    public String gerarResumo() {
        return "Pedido %d | Cliente: %s | Itens: %d | Total: R$ %s | Status: %s"
                .formatted(
                        numero,
                        cliente.consultarNome(),
                        itens.size(),
                        calcularTotal().toPlainString(),
                        status
                );
    }

    // POO — ENCAPSULAMENTO E PROTEÇÃO DOS INVARIANTES:
    // Somente Pedido decide se a alteração solicitada é válida.
    private void validarEmMontagem() {
        if (status != StatusPedido.EM_MONTAGEM) {
            throw new IllegalStateException(
                    "operação permitida somente para pedido em montagem"
            );
        }
    }
}
