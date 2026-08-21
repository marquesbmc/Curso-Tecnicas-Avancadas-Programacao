public abstract class CategoriaCorrida {
    protected double valorPorKm;
    protected double valorPorMinuto;

    public CategoriaCorrida(double valorPorKm, double valorPorMinuto) {
        this.valorPorKm = valorPorKm;
        this.valorPorMinuto = valorPorMinuto;
    }
    public abstract double calcular(double km, int minutos);
    public abstract String getNome();
}
