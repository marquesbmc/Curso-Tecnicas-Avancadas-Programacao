package visita9;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

// Reúne objetos por meio do contrato Preparavel.
// A fila não precisa conhecer suas classes concretas.
public final class FilaPreparo {

    // Mantém zero ou vários objetos preparados em ordem de chegada.
    private final Deque<Preparavel> elementos =
        new ArrayDeque<>();

    // Aceita qualquer objeto que cumpra o contrato Preparavel.
    public void adicionar(Preparavel item) {
        Objects.requireNonNull(
            item,
            "O item preparado é obrigatório."
        );

        elementos.addLast(item);
    }

    // Solicita a mesma operação a todos os elementos.
    public void prepararTodos() {
        while (!elementos.isEmpty()) {

            // A variável conhece apenas o contrato comum.
            Preparavel item = elementos.removeFirst();

            // O objeto concreto determina como será preparado.
            System.out.println(item.preparar());
        }
    }
}