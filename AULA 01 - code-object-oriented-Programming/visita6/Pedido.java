package visita6;

/**
 * Demonstra encapsulamento:
 * o próprio Pedido protege seu estado e suas regras.
 */
public final class Pedido {

    // Estado interno: não pode ser alterado diretamente.
    private final int numero;
    private int quantidadeItens;
    private StatusPedido status;

    /**
     * O construtor cria o objeto em um estado inicial válido.
     */
    public Pedido(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException(
                "Número do pedido deve ser positivo"
            );
        }

        this.numero = numero;
        this.quantidadeItens = 0;
        this.status = StatusPedido.EM_MONTAGEM;
    }

    /**
     * Acrescenta um item somente enquanto o pedido
     * estiver em montagem.
     */
    public void adicionarItem() {
        validarEmMontagem();

        quantidadeItens++;
    }

    /**
     * Confirma somente um pedido em montagem
     * que possua pelo menos um item.
     */
    public void confirmar() {
        validarEmMontagem();

        if (quantidadeItens == 0) {
            throw new IllegalStateException(
                "Pedido vazio não pode ser confirmado"
            );
        }

        status = StatusPedido.CONFIRMADO;
    }

    /**
     * Descarta um pedido que ainda está em montagem.
     */
    public void descartar() {
        validarEmMontagem();

        // Os itens deixam de fazer parte do pedido descartado.
        quantidadeItens = 0;
        status = StatusPedido.DESCARTADO;
    }

    /**
     * Permite consultar a situação, mas não alterá-la.
     */
    public StatusPedido consultarStatus() {
        return status;
    }

    /**
     * Operação interna que protege as mudanças.
     */
    private void validarEmMontagem() {
        if (status != StatusPedido.EM_MONTAGEM) {
            throw new IllegalStateException(
                "Pedido não está mais em montagem"
            );
        }
    }
}