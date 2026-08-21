package visita9;

import java.math.BigDecimal;
import java.util.Objects;

// Representa a definição comum dos produtos da cafeteria.
// A classe é abstrata porque um produto genérico não possui
// uma forma concreta de preparo.
public abstract class Produto implements Preparavel {

    // Estado compartilhado por todos os tipos de produto.
    private final String nome;
    private final BigDecimal preco;

    // Inicializa a parte comum de qualquer produto concreto.
    protected Produto(String nome, BigDecimal preco) {
        this.nome = validarNome(nome);
        this.preco = validarPreco(preco);
    }

    // Permite consultar o nome sem permitir sua alteração.
    public final String consultarNome() {
        return nome;
    }

    // Permite consultar o preço sem permitir sua alteração.
    public final BigDecimal consultarPreco() {
        return preco;
    }

    // Produto assume o contrato Preparavel, mas não determina
    // uma forma genérica de preparo.
    // As subclasses concretas devem implementar esta operação.
    @Override
    public abstract String preparar();

    // Protege a regra de que todo produto precisa de um nome.
    private static String validarNome(String nome) {
        Objects.requireNonNull(nome, "O nome é obrigatório.");

        if (nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome do produto não pode estar vazio."
            );
        }

        return nome;
    }

    // Protege a regra de que o preço precisa ser positivo.
    private static BigDecimal validarPreco(BigDecimal preco) {
        Objects.requireNonNull(preco, "O preço é obrigatório.");

        if (preco.signum() <= 0) {
            throw new IllegalArgumentException(
                "O preço deve ser maior que zero."
            );
        }

        return preco;
    }
}