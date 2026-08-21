package nucleo3;

import java.math.BigDecimal;

/**
 * POO — HERANÇA/ESPECIALIZAÇÃO:
 * Alimento é outro tipo específico de Produto.
 */
public final class Alimento extends Produto {

    private final int pesoEmGramas;

    public Alimento(String nome, BigDecimal preco, int pesoEmGramas) {
        super(nome, preco);
        if (pesoEmGramas <= 0) {
            throw new IllegalArgumentException("peso deve ser maior que zero");
        }
        this.pesoEmGramas = pesoEmGramas;
    }

    public int consultarPesoEmGramas() {
        return pesoEmGramas;
    }

    // POO — SOBRESCRITA:
    // A mesma operação possui uma execução adequada ao tipo Alimento.
    @Override
    public String preparar() {
        return "Aquecer e montar %s (%d g)"
                .formatted(consultarNome(), pesoEmGramas);
    }
}
