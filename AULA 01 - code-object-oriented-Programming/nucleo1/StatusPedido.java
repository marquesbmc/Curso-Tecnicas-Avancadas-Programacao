package nucleo1;

/**
 * Define os estados possíveis de um Pedido.
 */
public enum StatusPedido {

    // O pedido ainda pode receber itens.
    EM_MONTAGEM,

    // O pedido foi concluído e não pode mais ser alterado.
    CONFIRMADO,

    // O pedido foi abandonado e seus itens foram removidos.
    DESCARTADO
}