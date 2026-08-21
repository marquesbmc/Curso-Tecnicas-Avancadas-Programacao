package nucleo2;

/**
 * Define os estados válidos de Pedido.
 */
public enum StatusPedido {

    // Pode receber itens.
    EM_MONTAGEM,

    // Não pode mais ser alterado.
    CONFIRMADO,

    // Foi abandonado antes da conclusão.
    DESCARTADO
}