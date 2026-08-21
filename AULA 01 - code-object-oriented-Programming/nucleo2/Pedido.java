package nucleo2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma compra realizada por um Cliente.
 *
 * Demonstra:
 * - associação navegável com Cliente;
 * - multiplicidade de itens;
 * - composição com ItemPedido;
 * - encapsulamento das regras de mudança.
 */
public final class Pedido {

    private final int numero;

    // Associação: Pedido conhece o Cliente.
    private final Cliente cliente;

    /*
     * Multiplicidade 0..* e composição:
     * Pedido cria e controla seus ItemPedido.
     */
    private final List<ItemPedido> itens = new ArrayList<>();

    // Estado protegido.
    private StatusPedido status;

    public Pedido(int numero, Cliente cliente) {
        if (numero <= 0) {
            throw new IllegalArgumentException(
                "Número do pedido deve ser positivo"
            );
        }

        this.numero = numero;

        this.cliente = Objects.requireNonNull(
            cliente,
            "Cliente é obrigatório"
        );

        this.status = StatusPedido.EM_MONTAGEM;
    }

    /**
     * Acrescenta um produto somente enquanto o pedido
     * estiver em montagem.
     */
    public void adicionarItem(
            Produto produto,
            int quantidade) {

        validarEmMontagem();

        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        if (!produto.estaDisponivel()) {
            throw new IllegalStateException(
                "Produto indisponível: "
                    + produto.consultarNome()
            );
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "Quantidade deve ser positiva"
            );
        }

        /*
         * Pedido cria internamente sua parte ItemPedido.
         * O preço atual fica registrado no item.
         */
        var item = new ItemPedido(
            produto,
            quantidade,
            produto.consultarPreco()
        );

        itens.add(item);
    }

    /**
     * Retorna uma cópia não modificável da coleção.
     */
    public List<ItemPedido> consultarItens() {
        return List.copyOf(itens);
    }

    public BigDecimal calcularTotal() {
        return itens.stream()
            .map(ItemPedido::calcularSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * O pedido pode começar vazio, mas não pode
     * ser confirmado vazio.
     */
    public void confirmar() {
        validarEmMontagem();

        if (itens.isEmpty()) {
            throw new IllegalStateException(
                "Pedido vazio não pode ser confirmado"
            );
        }

        status = StatusPedido.CONFIRMADO;
    }

    /**
     * Ao descartar o todo, suas partes são removidas.
     * Os objetos Produto continuam existindo.
     */
    public void descartar() {
        validarEmMontagem();

        itens.clear();
        status = StatusPedido.DESCARTADO;
    }

    public StatusPedido consultarStatus() {
        return status;
    }

    /**
     * Permite navegar de Pedido para Cliente.
     */
    public Cliente consultarCliente() {
        return cliente;
    }

    public int consultarNumero() {
        return numero;
    }

    /**
     * Regra interna que protege as mudanças do Pedido.
     */
    private void validarEmMontagem() {
        if (status != StatusPedido.EM_MONTAGEM) {
            throw new IllegalStateException(
                "Pedido não está mais em montagem"
            );
        }
    }
}