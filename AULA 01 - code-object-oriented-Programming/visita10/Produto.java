package visita10;

import java.math.BigDecimal;
import java.util.Objects;

// Representa a definição comum dos produtos.
// Não permite a criação de um produto genérico.
public abstract class Produto implements Preparavel {

    // Estado comum protegido pelo próprio objeto.
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

    // Produto assume o contrato, mas não define
    // uma preparação válida para todos os produtos.
    @Override
    public abstract String preparar();

    // Garante que o produto sempre tenha um nome válido.
    private static String validarNome(String nome) {
        Objects.requireNonNull(nome, "O nome é obrigatório.");

        if (nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome não pode estar vazio."
            );
        }

        return nome;
    }

    // Garante que o produto sempre tenha um preço válido.
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