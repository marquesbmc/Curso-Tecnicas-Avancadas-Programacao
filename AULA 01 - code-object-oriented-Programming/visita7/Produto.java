package visita7;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa o conceito geral Produto.
 *
 * Esta classe reúne os dados e comportamentos comuns
 * aos diferentes tipos de produto da cafeteria.
 */
public class Produto {

    // Estado comum a bebidas e alimentos.
    private final String nome;
    private final BigDecimal preco;
    private boolean disponivel;

    /**
     * Inicializa as características comuns.
     */
    public Produto(String nome, BigDecimal preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome do produto é obrigatório"
            );
        }

        Objects.requireNonNull(
            preco,
            "Preço do produto é obrigatório"
        );

        if (preco.signum() <= 0) {
            throw new IllegalArgumentException(
                "Preço deve ser positivo"
            );
        }

        this.nome = nome;
        this.preco = preco;

        // Todo produto começa disponível.
        this.disponivel = true;
    }

    /**
     * Comportamento comum herdado pelas especializações.
     */
    public String consultarNome() {
        return nome;
    }

    /**
     * Comportamento comum herdado pelas especializações.
     */
    public BigDecimal consultarPreco() {
        return preco;
    }

    /**
     * Informa a disponibilidade atual do produto.
     */
    public boolean estaDisponivel() {
        return disponivel;
    }

    /**
     * Centraliza a alteração da disponibilidade.
     */
    public void alterarDisponibilidade(boolean disponivel) {
        this.disponivel = disponivel;
    }
}