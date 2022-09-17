package data;

import java.util.ArrayList;

public class ChargingZones {
    private float latitud;
    private float longitud;
    private int id_estacio;
    private String nom;
    private String carrer;
    private String ciutat;
    public ArrayList<Endolls> llistaEndolls=  new ArrayList<Endolls>();

    public boolean addNewEndoll(Endolls newEndoll) {
        boolean exitNewEndoll= false;
        exitNewEndoll= llistaEndolls.add(newEndoll);

        return exitNewEndoll;
    }

    public boolean comprovarEndoll(Endolls endoll) {
        boolean endollPresent= false;
        endollPresent= llistaEndolls.contains(endoll);

        return endollPresent;
    }


    public ChargingZones() {
        this.latitud= -1;
        this.longitud= -1;
        this.id_estacio= 0;
        this.nom= null;
        this.carrer= null;
        this.ciutat= null;
        //this.llistaEndolls= new ArrayList<Endolls>();
    }

    public ChargingZones(int id_estacio, float latitud, float longitud, String nom, String carrer, String ciutat, Endolls endolls) {
        this.latitud= latitud;
        this.longitud= longitud;
        this.id_estacio= id_estacio;
        this.nom= nom;
        this.carrer= carrer;
        this.ciutat= ciutat;
        this.llistaEndolls.add(endolls);
    }

    public float getMaxPotenciaEndoll() {
        float maxPotencia= 0;

        for (int i= 0; i< llistaEndolls.size(); i++) {
            if (llistaEndolls.get(i).getPotencia()> maxPotencia) {
                maxPotencia= llistaEndolls.get(i).getPotencia();
            }
        }

        return maxPotencia;
    }

    /* GETTERS AND SETTERS */
    public int getId_estacio() {
        return id_estacio;
    }
    public void setId_estacio(int id_estacio) {
        this.id_estacio = id_estacio;
    }
    public float getLatitud() {
        return latitud;
    }
    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }
    public float getLongitud() {
        return longitud;
    }
    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getCarrer() {
        return carrer;
    }
    public void setCarrer(String carrer) {
        this.carrer = carrer;
    }
    public String getCiutat() {
        return ciutat;
    }
    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }
}
