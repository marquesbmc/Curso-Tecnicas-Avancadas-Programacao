package visita2;

import java.math.BigDecimal;

public final class Aplicacao {

    public static void main(String[] args) {

        // Cria um único objeto representando Ana.
        Cliente ana = new Cliente(
            "Ana",
            "(21) 99999-1111"
        );

        // Os dois pedidos recebem a mesma referência.
        Pedido pedido104 = new Pedido(104, ana);
        Pedido pedido107 = new Pedido(107, ana);

        // Cada pedido mantém seu próprio total.
        pedido104.registrarProduto(
            "Cappuccino",
            new BigDecimal("8.00")
        );

        pedido107.registrarProduto(
            "Brownie",
            new BigDecimal("13.50")
        );

        // Altera o único objeto Cliente.
        ana.atualizarTelefone("(21) 98888-2222");

        // Os pedidos acessam o mesmo objeto Cliente.
        System.out.println(pedido104.gerarResumo());
        System.out.println(pedido107.gerarResumo());

        System.out.println(
            "Telefone pelo pedido 104: "
                + pedido104.consultarTelefoneCliente()
        );

        System.out.println(
            "Telefone pelo pedido 107: "
                + pedido107.consultarTelefoneCliente()
        );
    }
}