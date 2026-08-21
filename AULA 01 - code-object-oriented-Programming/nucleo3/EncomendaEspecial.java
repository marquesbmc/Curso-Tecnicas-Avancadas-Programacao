package nucleo3;

import java.util.Objects;

/**
 * Não é um Produto, mas também pode ser preparado.
 *
 * POO — INTERFACE:
 * Classes sem parentesco de herança podem assumir o mesmo contrato.
 */
public final class EncomendaEspecial implements Preparavel {

    private final String descricao;

    public EncomendaEspecial(String descricao) {
        Objects.requireNonNull(descricao, "descrição não pode ser nula");
        if (descricao.isBlank()) {
            throw new IllegalArgumentException("descrição não pode ficar vazia");
        }
        this.descricao = descricao.trim();
    }

    public String consultarDescricao() {
        return descricao;
    }

    // POO — SOBRESCRITA/REALIZAÇÃO DO CONTRATO.
    @Override
    public String preparar() {
        return "Montar encomenda especial: " + descricao;
    }
}
