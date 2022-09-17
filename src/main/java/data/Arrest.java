package data;

public class Arrest<T> {

    private T data; //distancia
    private Vertex<T> iVex; //iVex--> puntero al vector V1
    private Vertex<T> jVex; //jVex--> puntero al vector V2
    protected Arrest<T> nextPosA; //iLink--> siguiente Aresta del vector V1
    protected Arrest<T> nextPosB; //jLink--> siguiente Aresta del vector V2

    /**
     * @desc Constructor de l'element Arrest
     */
    public Arrest() {
        this.setData(null);
        this.setiVex(null);
        this.setjVex(null);
        this.setNextPosA(null);
        this.setNextPosB(null);
    }

    /**
     * Constructor de l'element Arrest, pero afegint data
     * @param data
     */
    public Arrest(T data) {
        this.setData(data);
        this.setiVex(null);
        this.setjVex(null);
        this.setNextPosA(null);
        this.setNextPosB(null);
    }

    /**
     * @desc Passar per parametre el data, el Arrest previ i el Arrest seguent
     * @param data
     * @param iVex
     * @param jVex
     * @param nextPosA
     * @param nextPosB
     */
    public Arrest(T data, T iVex, T jVex, Arrest<T> nextPosA, Arrest<T> nextPosB) {
        this.setData(data);
        this.setiVex(iVex);
        this.setjVex(jVex);
        this.setNextPosA(nextPosA);
        this.setNextPosB(nextPosB);
    }

    /**
     * @desc Comparar dos Objectes
     * @param data
     * @return
     */
    public int compareTo(T data) {
        int h1, h2, r= -1;

        if (data!= null) {
            h1= data.hashCode(); // autonomia
            h2= this.getData().hashCode(); // distancia minima

            if (h1== h2) {
                r= 0;
            } else if (h1<h2) {
                r= 1;
            }
        }

        return r;
    }

    public String print(Arrest arrestToPrint) {
        String toPrint= "";
        toPrint+= arrestToPrint.getiVex()+"<--->"+arrestToPrint.getjVex();
        return toPrint;
    }

    public int compareTo(T data1, T data2) {
        int h1, h2, r= -1;

        if (data1!= null && data2!= null) {
            h1= data1.hashCode(); // autonomia
            h2= data2.hashCode(); // distancia minima

            if (h1== h2) {
                r= 0;
            } else if (h1<h2) {
                r= 1;
            }
        }

        return r;
    }

    public int menorDistancia(T data) {
        float h1, h2;
        int r= -1;

        if (data instanceof Arrest<?> && data!= null) {
            Arrest aToCompare = (Arrest) data;
            h1= (float)aToCompare.getData();
            h2= (float)this.getData();

            if (h1== h2) {
                r= 0;
            } else if (h1>h2) {
                r= 1;
            }
        }

        return r;
    }

    /* GETTERS AND SETTERS */
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public Vertex<T> getiVex() {
        return iVex;
    }
    public void setiVex(T iVex) {
        this.iVex = (Vertex<T>) iVex;
    }
    public Vertex<T> getjVex() {
        return jVex;
    }
    public void setjVex(T jVex) {
        this.jVex = (Vertex<T>) jVex;
    }
    public Arrest<T> getNextPosA() {
        return nextPosA;
    }
    public void setNextPosA(Arrest<T> nextPosA) {
        this.nextPosA = nextPosA;
    }
    public Arrest<T> getNextPosB() {
        return nextPosB;
    }
    public void setNextPosB(Arrest<T> nextPosB) {
        this.nextPosB = nextPosB;
    }
    @Override
    public String toString() {
        return "Arrest [data=" + data.toString() + "]\n";
    }
}
