package visita8;

import java.math.BigDecimal;

/**
 * Representa outro tipo concreto de Produto.
 */
public final class Alimento extends Produto {

    // Característica específica de Alimento.
    private final int pesoEmGramas;

    public Alimento(
            String nome,
            BigDecimal preco,
            boolean disponivel,
            int pesoEmGramas) {

        // Inicializa a parte definida em Produto.
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

    /**
     * Alimento fornece sua própria implementação
     * para a operação exigida por Produto.
     */
    @Override
    public String preparar() {
        return "Preparar o alimento "
            + consultarNome()
            + " e servir uma porção de "
            + pesoEmGramas
            + " g.";
    }
}