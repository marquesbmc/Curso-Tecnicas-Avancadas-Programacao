package nucleo2;

import java.math.BigDecimal;

/**
 * Especialização concreta de Produto.
 */
public final class Alimento extends Produto {

    private final int pesoEmGramas;

    public Alimento(
            String nome,
            BigDecimal preco,
            boolean disponivel,
            int pesoEmGramas) {

        // Inicializa as características herdadas de Produto.
        super(nome, preco, disponivel);

        if (pesoEmGramas <= 0) {
            throw new IllegalArgumentException(
                "Peso deve ser positivo"
            );
        }

        this.pesoEmGramas = pesoEmGramas;
    }

    public int consultarPesoEmGramas() {
        return pesoEmGramas;
    }

    // Implementação concreta exigida por Produto.
    @Override
    public String preparar() {
        return "Preparar o alimento "
            + consultarNome()
            + " e servir uma porção de "
            + pesoEmGramas
            + " g.";
    }
}