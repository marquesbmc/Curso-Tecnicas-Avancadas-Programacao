package nucleo3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Reúne qualquer objeto que assuma o papel Preparavel.
 */
public final class FilaPreparo {

    // POO — PROGRAMAÇÃO PELO TIPO COMUM:
    // A fila conhece Preparavel, não as classes concretas.
    private final List<Preparavel> elementos = new ArrayList<>();

    public void adicionar(Preparavel item) {
        elementos.add(Objects.requireNonNull(item, "item não pode ser nulo"));
    }

    public int consultarQuantidade() {
        return elementos.size();
    }

    public void prepararTodos() {
        for (Preparavel item : elementos) {
            // POO — POLIMORFISMO:
            // A operação solicitada é a mesma. Em tempo de execução,
            // o objeto concreto decide qual preparar() será executado.
            System.out.println(item.preparar());
        }

        elementos.clear();
    }
}
