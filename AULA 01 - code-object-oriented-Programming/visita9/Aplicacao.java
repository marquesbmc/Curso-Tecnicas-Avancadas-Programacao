package visita9;

import java.math.BigDecimal;

// Demonstra objetos diferentes colaborando pelo mesmo contrato.
public final class Aplicacao {

    public static void main(String[] args) {

        // Bebida é Produto e, por meio de Produto, é Preparavel.
        var cappuccino = new Bebida(
            "Cappuccino",
            new BigDecimal("13.50"),
            200
        );

        // Alimento também é Produto e Preparavel.
        var paoDeQueijo = new Alimento(
            "Pão de queijo",
            new BigDecimal("8.00"),
            120
        );

        // EncomendaEspecial não é Produto.
        // Ela realiza Preparavel diretamente.
        var kitPresente = new EncomendaEspecial(
            "Kit com café, caneca e embalagem para presente"
        );

        // A fila conhece apenas o contrato Preparavel.
        var fila = new FilaPreparo();

        // Objetos de classes diferentes entram na mesma fila.
        fila.adicionar(cappuccino);
        fila.adicionar(paoDeQueijo);
        fila.adicionar(kitPresente);

        // A mesma operação é solicitada a todos.
        fila.prepararTodos();
    }
}