public class CategoriaConforto extends CategoriaCorrida {
    public CategoriaConforto() { super(3.40, 0.65); }
    @Override public double calcular(double km, int minutos) { return 10.00 + km * valorPorKm + minutos * valorPorMinuto; }
    @Override public String getNome() { return "CONFORTO"; }
}
