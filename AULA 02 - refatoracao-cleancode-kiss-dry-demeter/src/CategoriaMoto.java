public class CategoriaMoto extends CategoriaCorrida {
    public CategoriaMoto() { super(1.50, 0.25); }
    @Override public double calcular(double km, int minutos) { return 4.00 + km * valorPorKm + minutos * valorPorMinuto; }
    @Override public String getNome() { return "MOTO"; }
}
