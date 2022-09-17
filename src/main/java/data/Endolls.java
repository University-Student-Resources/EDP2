package data;

public class Endolls {
    private int id;
    private float potencia;
    private String estat;
    private String tipus;

    public Endolls() {
        this.id= 0;
        this.potencia= 0;
        this.estat= null;
        this.tipus= null;
    }

    public Endolls(int id, String estat, float potencia, String tipus) {
        this.id= id;
        this.potencia= potencia;
        this.estat= estat;
        this.tipus= tipus;
    }


    /* GETTERS AND SETTERS */
    public float getPotencia() {
        return potencia;
    }
    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEstat() {
        return estat;
    }
    public void setEstat(String estat) {
        this.estat = estat;
    }
    public String getTipus() {
        return tipus;
    }
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
