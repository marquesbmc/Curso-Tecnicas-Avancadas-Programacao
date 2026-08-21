package visita11;

import java.math.BigDecimal;
import java.util.Objects;

// Representa o conceito geral dos produtos da cafeteria.
// A classe é abstrata porque não existe um preparo
// válido para um Produto genérico.
public abstract class Produto implements Preparavel {

    // Estado compartilhado pelos produtos concretos.
    private final String nome;
    private final BigDecimal preco;

    // Inicializa a parte comum das subclasses.
    protected Produto(String nome, BigDecimal preco) {
        this.nome = validarNome(nome);
        this.preco = validarPreco(preco);
    }

    // Permite consultar o nome sem alterá-lo.
    public final String consultarNome() {
        return nome;
    }

    // Permite consultar o preço sem alterá-lo.
    public final BigDecimal consultarPreco() {
        return preco;
    }

    // Produto realiza Preparavel, mas transfere
    // às subclasses a definição concreta do preparo.
    @Override
    public abstract String preparar();

    // Protege a validade do nome.
    private static String validarNome(String nome) {
        Objects.requireNonNull(
            nome,
            "O nome do produto é obrigatório."
        );

        if (nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome do produto não pode estar vazio."
            );
        }

        return nome;
    }

    // Protege a validade do preço.
    private static BigDecimal validarPreco(BigDecimal preco) {
        Objects.requireNonNull(
            preco,
            "O preço do produto é obrigatório."
        );

        if (preco.signum() <= 0) {
            throw new IllegalArgumentException(
                "O preço deve ser maior que zero."
            );
        }

        return preco;
    }
}