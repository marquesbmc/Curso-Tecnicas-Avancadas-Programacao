package visita3;

import java.math.BigDecimal;

public final class Aplicacao {

    public static void main(String[] args) {

        // Cria um único objeto representando Ana.
        var ana = new Cliente(
            "Ana",
            "(21) 99999-1111"
        );

        // O pedido nasce associado ao cliente.
        // Neste momento, a lista de itens está vazia.
        var pedido104 = new Pedido(104, ana);

        System.out.println(
            "Pode confirmar vazio? "
                + pedido104.podeConfirmar()
        );

        // Os itens são criados individualmente.
        var cappuccino = new ItemPedido(
            "Cappuccino",
            1,
            new BigDecimal("13.50")
        );

        var paoDeQueijo = new ItemPedido(
            "Pão de queijo",
            2,
            new BigDecimal("5.50")
        );

        var brownie = new ItemPedido(
            "Brownie",
            1,
            new BigDecimal("8.00")
        );

        // O pedido recebe vários itens gradualmente.
        pedido104.adicionarItem(cappuccino);
        pedido104.adicionarItem(paoDeQueijo);
        pedido104.adicionarItem(brownie);

        System.out.println();
        System.out.println("Itens registrados:");

        // Percorre todos os itens associados ao pedido.
        pedido104.consultarItens().forEach(
            item -> System.out.println(
                item.gerarResumo()
            )
        );

        System.out.println();
        System.out.println(
            "Pode confirmar agora? "
                + pedido104.podeConfirmar()
        );

        // A confirmação é permitida porque há itens.
        pedido104.confirmar();

        System.out.println();
        System.out.println(pedido104.gerarResumo());

        // Atualiza o único objeto Cliente.
        ana.atualizarTelefone("(21) 98888-2222");

        // Pedido acessa o telefone atualizado
        // por meio da associação com Cliente.
        System.out.println(
            "Telefone pelo pedido: "
                + pedido104.consultarTelefoneCliente()
        );
    }
}