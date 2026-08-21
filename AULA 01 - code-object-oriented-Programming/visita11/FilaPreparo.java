package visita11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// A fila colabora somente com o tipo Preparavel.
// Ela não precisa conhecer Bebida, Alimento
// ou EncomendaEspecial.
public final class FilaPreparo {

    // A coleção possui referências do tipo comum.
    // Os objetos armazenados podem pertencer
    // a classes concretas diferentes.
    private final List<Preparavel> elementos =
        new ArrayList<>();

    // Recebe qualquer objeto compatível com Preparavel.
    public void adicionar(Preparavel item) {
        elementos.add(
            Objects.requireNonNull(
                item,
                "O item preparável é obrigatório."
            )
        );
    }

    // Envia a mesma solicitação para todos os objetos.
    public void prepararTodos() {
        System.out.println("FILA DE PREPARO");

        int posicao = 1;

        for (Preparavel item : elementos) {

            /*
             * POLIMORFISMO
             *
             * A referência possui o tipo Preparavel.
             * Entretanto, ela pode apontar para uma Bebida,
             * um Alimento ou uma EncomendaEspecial.
             *
             * Durante a execução, a JVM identifica o objeto
             * concreto e seleciona sua implementação
             * sobrescrita de preparar().
             */
            System.out.printf(
                "%d. %s%n",
                posicao,
                item.preparar()
            );

            posicao++;
        }
    }
}