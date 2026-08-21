package visita10;

import java.math.BigDecimal;

// Representa outro tipo concreto de Produto.
public final class Alimento extends Produto {

    // Característica específica dos alimentos.
    private final int pesoEmGramas;

    public Alimento(
            String nome,
            BigDecimal preco,
            int pesoEmGramas) {

        // Inicializa a parte herdada de Produto.
        super(nome, preco);

        if (pesoEmGramas <= 0) {
            throw new IllegalArgumentException(
                "O peso deve ser maior que zero."
            );
        }

        this.pesoEmGramas = pesoEmGramas;
    }

    // Permite consultar a característica específica.
    public int consultarPesoEmGramas() {
        return pesoEmGramas;
    }

    // Sobrescreve a operação recebida de Produto.
    @Override
    public String preparar() {
        return "Aquecer e montar %s em uma porção de %d g."
            .formatted(consultarNome(), pesoEmGramas);
    }
}