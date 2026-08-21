package nucleo3;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * POO — CLASSE ABSTRATA:
 * Reúne estado e comportamento comuns aos produtos, mas não representa
 * sozinha um produto concreto que possa ser preparado.
 *
 * POO — REALIZAÇÃO DE INTERFACE:
 * Produto assume o contrato Preparavel. Como a classe é abstrata,
 * pode deixar preparar() para suas subclasses concretas.
 */
public abstract class Produto implements Preparavel {

    // POO — ESTADO PROTEGIDO POR ENCAPSULAMENTO.
    private final String nome;
    private final BigDecimal preco;
    private boolean disponivel;

    protected Produto(String nome, BigDecimal preco) {
        Objects.requireNonNull(nome, "nome não pode ser nulo");
        Objects.requireNonNull(preco, "preço não pode ser nulo");

        if (nome.isBlank()) {
            throw new IllegalArgumentException("nome não pode ficar vazio");
        }
        if (preco.signum() <= 0) {
            throw new IllegalArgumentException("preço deve ser maior que zero");
        }

        this.nome = nome.trim();
        this.preco = preco;
        this.disponivel = true;
    }

    public String consultarNome() {
        return nome;
    }

    public BigDecimal consultarPreco() {
        return preco;
    }

    public boolean estaDisponivel() {
        return disponivel;
    }

    // Operações do domínio controlam a alteração do atributo disponivel.
    public void suspenderVenda() {
        disponivel = false;
    }

    public void reativarVenda() {
        disponivel = true;
    }

    // POO — MÉTODO ABSTRATO:
    // Declara o comportamento exigido, sem fornecer uma execução geral.
    @Override
    public abstract String preparar();
}
