package visita9;

import java.math.BigDecimal;

// Representa outro tipo concreto de Produto.
public final class Alimento extends Produto {

    // Característica específica de um alimento.
    private final int pesoEmGramas;

    // Inicializa os dados comuns e específicos do alimento.
    public Alimento(
            String nome,
            BigDecimal preco,
            int pesoEmGramas) {

        // Inicializa nome e preço na classe Produto.
        super(nome, preco);

        if (pesoEmGramas <= 0) {
            throw new IllegalArgumentException(
                "O peso deve ser maior que zero."
            );
        }

        this.pesoEmGramas = pesoEmGramas;
    }

    // Permite consultar o peso sem alterá-lo.
    public int consultarPesoEmGramas() {
        return pesoEmGramas;
    }

    // Concretiza o comportamento exigido por Preparavel.
    @Override
    public String preparar() {
        return "Aquecendo e montando o alimento %s de %d g."
            .formatted(consultarNome(), pesoEmGramas);
    }
}