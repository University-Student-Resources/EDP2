package data;

public class Vertex<T> implements Comparable<Vertex> {
    private T id; //key
    private Object data; //zones de recarrega
    //protected Vertex<T> nextCol; //seguent colisio, *no ens fa falta amb aquest cas, ja que utilitzem el Hashmap
    private Arrest<T> nextArrest; //primer Aresta
    private int nArrest;

    /**
     * @desc Constructor de l'element Node
     */
    public Vertex() {
        this.setId(null);
        this.setData(null);
        this.setNextArrest(null);
        this.nArrest= 0;
    }

    /**
     * @desc Constructor de l'element Node, pero afegint data
     * @param id
     * @param data
     */
    public Vertex(T id, T data) {
        this.setId(id);
        this.setData(data);
        this.setNextArrest(null);
        this.nArrest= 0;
    }

    /**
     * @desc Passar per parametre el data, el Node previ i el Node seguent
     * @param id
     * @param data
     */
    public Vertex(T id, T data, Arrest<T> nextArrest) {
        this.setId(id);
        this.setData(data);
        this.setNextArrest(nextArrest);
        this.nArrest++;
    }


    public Arrest getLastArrest() {
        Arrest lastArrest= null;
        if (this.nextArrest!= null) {
            Arrest hopArrest= nextArrest;
            Vertex<T> actualV= new Vertex<T>(this.getId(), (T) this.getData(),this.getNextArrest());

            while (hopArrest!= null) {
                lastArrest= hopArrest;
                if (this.compareTo((T)actualV,(T)hopArrest.getiVex())== 0) {
                    hopArrest= hopArrest.getNextPosA();
                } else {
                    hopArrest= hopArrest.getNextPosB();
                }
            }
        }

        return lastArrest;
    }


    public void addLastArrest(Graph graph, Arrest newNextArrest) {
        Vertex vToCompareI= newNextArrest.getiVex();
        Vertex vToCompareJ= newNextArrest.getjVex();
        Vertex<T> actualV= new Vertex<T>(this.getId(), (T) this.getData(),this.getNextArrest());

        //1. Comprovar que existe una conexión
        //if (graph.existeixAresta(vToCompareI,vToCompareJ)== false) {
        if (vToCompareJ.exists((T) vToCompareI)== false) {
                //2. En caso negativo, THEN: si nextArrest es nulo o no, en el vertice actual
                //2.1. En caso no nulo, THEN:
                if (this.nextArrest != null) {
                    //2.1.1. Mirar donde esta el vertice actual (iVex o jVex) ultimaAresta= this.getLastArrest();
                    //2.1.2. Hacer que el ultimo siguiente arista del vertice, apunte al nuevo
                    //3. Añadir la Aresta al vertice conectado, segun su posicion iVex o jVex
                    Arrest ultimaAresta= this.getLastArrest();
                    if (ultimaAresta.compareTo((T) ultimaAresta.getiVex().getId(), (T) vToCompareI.getId()) == 0) {
                        vToCompareI.getLastArrest().setNextPosA(newNextArrest);
                        vToCompareJ.addLastArrest(graph, newNextArrest);
                    } else {
                        //vToCompareJ.getLastArrest().setNextPosB(newNextArrest);
                        ultimaAresta.setNextPosB(newNextArrest);
                        vToCompareJ.addLastArrest(graph, newNextArrest);
                    }
                } else {
                    //2.2. En caso nulo, THEN: Simplemente apunta el "nextArresta" a la nueva arista "newNextArrest"
                    this.nextArrest = newNextArrest;
                    if (this.compareTo((T) actualV, (T) vToCompareI) == 1) {
                        vToCompareI.addLastArrest(graph, newNextArrest);
                    } else {
                        vToCompareJ.addLastArrest(graph, newNextArrest);
                    }
                }

                this.nArrest++;
            }
        }




    /**
     * @desc comprobar si existe una conexión entre el veertice actual y el pasado por parametro
     * @param vToCompare
     * @return
     */
    public boolean exists(T vToCompare) {
        Arrest hopArrest= nextArrest;
        boolean exist= false;
        while (hopArrest != null && !exist) {
            Vertex<T> actualV= new Vertex<T>(this.getId(), (T) this.getData(),this.getNextArrest());
            if (this.compareTo((T) actualV,vToCompare)== 1) { //id_vi1 > id_vi2 | actualV > vToCompare

                //Comparar aquí, si hay conexion entre los dos
                if ((hopArrest.getiVex().compareTo(hopArrest.getjVex(),vToCompare)== 0) && (hopArrest.getjVex().compareTo(hopArrest.getiVex(),this)== 0)) {
                    return exist= true;
                }
                hopArrest= hopArrest.getNextPosA();
            } else { //id_vi1 < id_vi2 | actualV < vToCompare

                //Comparar aquí, si hay conexion entre los dos
                if (hopArrest.getjVex().compareTo(hopArrest.getiVex(),vToCompare)== 0) {
                    return exist= true;
                }
                hopArrest= hopArrest.getNextPosB();
            }
        }

        return exist;
    }


    public T valorConnexio(T v2) {
        boolean exit= false;
        T rdata= null;
        Arrest hopArrest= nextArrest;

        while (hopArrest != null && !exit) {
            Vertex<T> actualV= new Vertex<T>(this.getId(), (T) this.getData(),this.getNextArrest());
            if (this.compareTo((T) actualV,v2)== 1) { //id_vi1 > id_vi2 | actualV > vToCompare

                //Comparar aquí, si hay conexion entre los dos
                if (hopArrest.getiVex().compareTo(hopArrest.getjVex(),v2)== 0) {
                    rdata= (T) hopArrest.getData();
                    exit= true;
                }
                hopArrest= hopArrest.getNextPosA();
            } else { //id_vi1 < id_vi2 | actualV < vToCompare

                //Comparar aquí, si hay conexion entre los dos
                if (hopArrest.getjVex().compareTo(hopArrest.getiVex(),v2)== 0) {
                    rdata= (T) hopArrest.getData();
                    exit= true;
                }
                hopArrest= hopArrest.getNextPosB();
            }
        }

        return rdata;
    }


    public LlistaGenerica<T> llistaV() {
        LlistaGenerica llArrest= new LlistaGenerica<T>();

        boolean exit= false;
        T rVn= null;
        Arrest hopArrest= nextArrest;

        while (hopArrest != null && !exit) {
            Vertex<T> actualV= new Vertex<T>(this.getId(), (T) this.getData(),this.getNextArrest());
            if (this.compareTo((T) actualV, (T) hopArrest.getiVex())== 0) { //iVex == this
                llArrest.afegir(hopArrest.getjVex());

                hopArrest= hopArrest.getNextPosA();
            } else { //id_vi1 < id_vi2 | actualV < vToCompare
                llArrest.afegir(hopArrest.getiVex());

                hopArrest= hopArrest.getNextPosB();
            }
        }

        return llArrest;
    }


    /**
     * @desc Comparar dos vertices
     * @param v1
     * @param v2
     * @return
     */
    public int compareTo(T v1, T v2) {
        int r= -1;

        if (v1!= null && v2!= null) {
            if (v1 instanceof Vertex<?>) {
                if (v2 instanceof Vertex<?>) {
                    Vertex<?> vi1= (Vertex<?>) v1;
                    Vertex<?> vi2= (Vertex<?>) v2;

                    int id_vi1= (int) vi1.getId();
                    int id_vi2= (int) vi2.getId();

                    if (id_vi1== id_vi2) {
                        r= 0;
                    } else if (id_vi1> id_vi2) {
                        r= 1;
                    }
                }
            }
        }

        return r;
    }


    /* GETTERS AND SETTERS */
    public T getId() {
        return id;
    }
    public void setId(T id) {
        this.id = id;
    }
    public Arrest<T> getNextArrest() {
        return nextArrest;
    }
    public void setNextArrest(Arrest<T> nextArrest) {
        this.nextArrest = nextArrest;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public int getnArrest() {
        return this.nArrest;
    }
    public void setnArrest(int nArrest) {
        this.nArrest = nArrest;
    }

    @Override
    public String toString() {
        return "Node [data=" + id.toString() + "]\n";
    }

    @Override
    public int compareTo(Vertex o) {
        return 0;
    }
}

