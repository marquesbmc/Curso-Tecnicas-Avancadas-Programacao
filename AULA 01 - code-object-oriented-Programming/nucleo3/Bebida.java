package nucleo3;

import java.math.BigDecimal;

/**
 * POO — HERANÇA/ESPECIALIZAÇÃO:
 * Bebida é um tipo específico de Produto.
 */
public final class Bebida extends Produto {

    private final int volumeEmMl;

    public Bebida(String nome, BigDecimal preco, int volumeEmMl) {
        super(nome, preco);
        if (volumeEmMl <= 0) {
            throw new IllegalArgumentException("volume deve ser maior que zero");
        }
        this.volumeEmMl = volumeEmMl;
    }

    public int consultarVolumeEmMl() {
        return volumeEmMl;
    }

    // POO — SOBRESCRITA:
    // Bebida fornece sua execução concreta para o contrato preparar().
    @Override
    public String preparar() {
        return "Extrair e servir %s (%d ml)"
                .formatted(consultarNome(), volumeEmMl);
    }
}
