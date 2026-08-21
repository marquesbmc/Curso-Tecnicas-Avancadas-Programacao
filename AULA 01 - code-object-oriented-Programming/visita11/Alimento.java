package visita11;

import java.math.BigDecimal;

// Alimento é outro tipo concreto de Produto.
public final class Alimento extends Produto {

    // Característica específica do alimento.
    private final int pesoEmGramas;

    public Alimento(
            String nome,
            BigDecimal preco,
            int pesoEmGramas) {

        // Inicializa nome e preço em Produto.
        super(nome, preco);

        if (pesoEmGramas <= 0) {
            throw new IllegalArgumentException(
                "O peso deve ser maior que zero."
            );
        }

        this.pesoEmGramas = pesoEmGramas;
    }

    public int consultarPesoEmGramas() {
        return pesoEmGramas;
    }

    // Implementação concreta de preparar().
    @Override
    public String preparar() {
        return "Aquecer e montar o alimento %s de %d g."
            .formatted(
                consultarNome(),
                pesoEmGramas
            );
    }
}