package visita6;

/**
 * Define os estados permitidos para um Pedido.
 */
public enum StatusPedido {

    // O pedido ainda pode receber itens.
    EM_MONTAGEM,

    // O pedido foi concluído.
    CONFIRMADO,

    // O pedido foi abandonado antes da conclusão.
    DESCARTADO
}