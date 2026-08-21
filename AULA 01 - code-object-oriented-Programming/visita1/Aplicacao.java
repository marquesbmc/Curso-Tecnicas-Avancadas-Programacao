package visita1;

import java.math.BigDecimal;

public class Aplicacao {

    public static void main(String[] args) {

        // Cada execução de new cria um objeto independente.
        var pedido104 = new Pedido(104, "Ana");
        var pedido105 = new Pedido(105, "Bruno");
        var pedido106 = new Pedido(106, "Carla");

        // O método altera somente o objeto que o executa.
        pedido104.registrarProduto(
            "Café expresso", new BigDecimal("8.00")
        );

        pedido105.registrarProduto(
            "Café expresso", new BigDecimal("8.00")
        );
        pedido105.registrarProduto(
            "Pão de queijo", new BigDecimal("11.00")
        );

        pedido106.registrarProduto(
            "Cappuccino", new BigDecimal("13.50")
        );

        // Cada objeto produz seu próprio resumo.
        System.out.println(pedido104.gerarResumo());
        System.out.println(pedido105.gerarResumo());
        System.out.println(pedido106.gerarResumo());
    }
}
    
