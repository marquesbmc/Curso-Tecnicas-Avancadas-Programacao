public class CalculadoraUniversal {
    public double processar(double valor, String operacao, double numero, boolean arredondar) {
        double r = valor;
        if (operacao.equals("SOMAR")) r = valor + numero;
        if (operacao.equals("SUBTRAIR")) r = valor - numero;
        if (operacao.equals("PERCENTUAL_MAIS")) r = valor + valor * numero / 100;
        if (operacao.equals("PERCENTUAL_MENOS")) r = valor - valor * numero / 100;
        if (operacao.equals("MULTIPLICAR")) r = valor * numero;
        if (operacao.equals("TETO") && valor > numero) r = numero;
        if (operacao.equals("PISO") && valor < numero) r = numero;
        if (arredondar) r = Math.round(r * 100.0) / 100.0;
        return r;
    }
}
