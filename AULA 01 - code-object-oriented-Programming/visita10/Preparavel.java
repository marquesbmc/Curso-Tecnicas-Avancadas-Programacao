package visita10;

// Declara o contrato comum de preparação.
public interface Preparavel {

    // Cada tipo concreto determina como será preparado.
    String preparar();
}