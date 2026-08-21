package visita9;

// Define o contrato para qualquer objeto que possa ser preparado.
public interface Preparavel {

    // Cada classe concreta determina sua própria forma de preparo.
    String preparar();
}