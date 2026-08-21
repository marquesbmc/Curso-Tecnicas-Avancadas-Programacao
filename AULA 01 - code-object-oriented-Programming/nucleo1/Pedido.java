package nucleo1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma compra realizada por um Cliente.
 *
 * Nesta classe aparecem:
 * - classe, objeto, estado e comportamento;
 * - associação navegável com Cliente;
 * - multiplicidade de itens;
 * - composição com ItemPedido;
 * - encapsulamento das regras do pedido.
 */
public final class Pedido {

    // Cada Pedido possui seu próprio número.
    private final int numero;

    /*
     * Associação navegável:
     * Pedido mantém uma referência para o Cliente.
     * Os dados do cliente não são copiados.
     */
    private final Cliente cliente;

    /*
     * Multiplicidade 0..*:
     * o Pedido começa vazio e pode receber vários itens.
     *
     * Composição:
     * Pedido cria e controla seus próprios ItemPedido.
     */
    private final List<ItemPedido> itens = new ArrayList<>();

    // Estado atual do pedido.
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

        // Todo novo pedido começa em montagem.
        this.status = StatusPedido.EM_MONTAGEM;
    }

    /**
     * Acrescenta um produto ao pedido.
     *
     * Pedido recebe um Produto independente e cria internamente
     * o ItemPedido que registra aquela escolha.
     */
    public void adicionarItem(
            Produto produto,
            int quantidade) {

        validarEmMontagem();

        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "Quantidade deve ser positiva"
            );
        }

        /*
         * Pedido controla a criação de ItemPedido.
         * O preço atual do produto é registrado no item.
         */
        var novoItem = new ItemPedido(
            produto,
            quantidade,
            produto.consultarPreco()
        );

        itens.add(novoItem);
    }

    /**
     * Retorna uma cópia não modificável da coleção.
     *
     * Quem recebe a lista pode consultar os itens,
     * mas não consegue acrescentar ou remover elementos.
     */
    public List<ItemPedido> consultarItens() {
        return List.copyOf(itens);
    }

    // Soma os subtotais de todos os itens.
    public BigDecimal calcularTotal() {
        return itens.stream()
            .map(ItemPedido::calcularSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Confirma o pedido.
     *
     * Regra de negócio:
     * o pedido pode começar vazio, mas não pode ser
     * confirmado sem pelo menos um item.
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
     * Descarta o pedido em montagem.
     *
     * Como ItemPedido faz parte da composição,
     * os itens são removidos junto com o pedido descartado.
     *
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
     *
     * Retorna a referência para o objeto Cliente,
     * em vez de copiar nome e telefone para Pedido.
     */
    public Cliente consultarCliente() {
        return cliente;
    }

    /**
     * Operação interna responsável por proteger a invariante:
     * pedidos confirmados ou descartados não podem ser alterados.
     */
    private void validarEmMontagem() {
        if (status != StatusPedido.EM_MONTAGEM) {
            throw new IllegalStateException(
                "Pedido não está mais em montagem"
            );
        }
    }
}