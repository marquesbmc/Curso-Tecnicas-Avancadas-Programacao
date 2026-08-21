public class CategoriaEconomica extends CategoriaCorrida {
    public CategoriaEconomica() { super(2.40, 0.45); }
    @Override public double calcular(double km, int minutos) { return 6.00 + km * valorPorKm + minutos * valorPorMinuto; }
    @Override public String getNome() { return "ECONOMICA"; }
}
