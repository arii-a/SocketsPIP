public abstract class Comando {
    String codigoComando;

    public abstract void parsear(String message) throws Exception;

    public abstract String getComando();

    public String getCodigoComando() {
        return codigoComando;
    }

    public void setCodigoComando(String codigoComando) {
        this.codigoComando = codigoComando;
    }
}