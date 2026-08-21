public class Localizacao {
    private String rua;
    private String bairro;
    private double latitude;
    private double longitude;

    public Localizacao(String rua, String bairro, double latitude, double longitude) {
        this.rua = rua; this.bairro = bairro; this.latitude = latitude; this.longitude = longitude;
    }
    public String getRua() { return rua; }
    public String getBairro() { return bairro; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
