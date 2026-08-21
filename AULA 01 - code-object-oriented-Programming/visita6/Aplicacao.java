package visita6;

/**
 * Testa as regras de encapsulamento de Pedido.
 */
public final class Aplicacao {

    private Aplicacao() {
        // Impede a criação da classe de inicialização.
    }

    public static void main(String[] args) {

        /*
         * =====================================================
         * TESTE 1 — ESTADO INICIAL
         * =====================================================
         */

        var pedido104 = new Pedido(104);

        System.out.println(
            "Situação inicial: "
                + pedido104.consultarStatus()
        );

        /*
         * =====================================================
         * TESTE 2 — TENTATIVA DE CONFIRMAR UM PEDIDO VAZIO
         * =====================================================
         */

        try {
            pedido104.confirmar();
        } catch (IllegalStateException erro) {
            System.out.println(
                "Operação rejeitada: "
                    + erro.getMessage()
            );
        }

        /*
         * A operação foi rejeitada.
         * O objeto permaneceu em um estado válido.
         */
        System.out.println(
            "Situação após a tentativa: "
                + pedido104.consultarStatus()
        );

        /*
         * =====================================================
         * TESTE 3 — CONFIRMAÇÃO VÁLIDA
         * =====================================================
         */

        // O Pedido controla internamente a quantidade de itens.
        pedido104.adicionarItem();
        pedido104.adicionarItem();

        // Agora a confirmação é válida.
        pedido104.confirmar();

        System.out.println(
            "Situação após confirmar: "
                + pedido104.consultarStatus()
        );

        /*
         * =====================================================
         * TESTE 4 — ALTERAÇÃO APÓS A CONFIRMAÇÃO
         * =====================================================
         */

        try {
            pedido104.adicionarItem();
        } catch (IllegalStateException erro) {
            System.out.println(
                "Operação rejeitada: "
                    + erro.getMessage()
            );
        }

        /*
         * A tentativa inválida não alterou o estado.
         */
        System.out.println(
            "Situação final: "
                + pedido104.consultarStatus()
        );

        /*
         * =====================================================
         * TESTE 5 — DESCARTE DE UM PEDIDO EM MONTAGEM
         * =====================================================
         */

        var pedido105 = new Pedido(105);

        pedido105.adicionarItem();
        pedido105.descartar();

        System.out.println(
            "Situação do pedido 105: "
                + pedido105.consultarStatus()
        );

        // Um pedido descartado também não pode ser confirmado.
        try {
            pedido105.confirmar();
        } catch (IllegalStateException erro) {
            System.out.println(
                "Operação rejeitada: "
                    + erro.getMessage()
            );
        }
    }
}