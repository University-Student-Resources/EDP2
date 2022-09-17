package data;

import com.google.gson.internal.bind.ObjectTypeAdapter;
import exceptions.*;

import java.util.HashMap;

public class Graph<T, V, E> implements TADgraph<V,E> {
    public HashMap<T, Vertex<V>> vertex= new HashMap<T, Vertex<V>>(); //Tener almacenado cada Vertice (V:key, Vertex<V> Vertice)
    private Arrest<E> arrest; //Inicializar la lista de Arestas

    public void unioArestes() throws InserirException {
        float latitudV1, longitudV1, latitudV2, longitudV2;
        float distanciaComprar= 40;

        for (T i: vertex.keySet()) {
            Vertex v1= vertex.get(i);
            ChargingZones v1CZ = (ChargingZones) v1.getData();
            latitudV1= v1CZ.getLatitud();
            longitudV1= v1CZ.getLongitud();

            for (T j : vertex.keySet()) { //Aqui hacer otro for para comparar de este a los otros vertices
                Vertex v2= vertex.get(j);
                ChargingZones v2CZ= (ChargingZones) vertex.get(j).getData();
                latitudV2= v2CZ.getLatitud();
                longitudV2= v2CZ.getLongitud();
                float distancia= calcDist(latitudV1,longitudV1,latitudV2,longitudV2);

                if (calcDist(latitudV1,longitudV1,latitudV2,longitudV2)<distanciaComprar) {
                    //1r--> Comprobar que la conexión no exista entre v1 y v2
                    boolean unionExistente= this.existeixAresta((V)v1,(V)v2);

                    if (unionExistente== false) {
                        //2n--> En caso de no existir creamos una nueva Arista
                        //2.1--> Para tener una referencia comparamos los ids de v1 y v2, quien sea el mas grande sera nextPosA, y el menor sera nextPosB
                        Arrest newArrest= new Arrest(distancia);
                        //3r--> Vinculamos en el v1 la Arista creada en la ultima posición de la lista de Aristas del vector v1
                        this.afegirAresta((V)v1,(V)v2,(E)newArrest);
                    }
                }
                System.out.println(v1.getId()+"--> "+v2.getId());
            }
        }
    }

    /**
     * @desc check is key exists in the HashMap
     * @param key
     * @return
     */
    public boolean comprovarVertex(T key) {
        boolean keyPresent= false;
        if (this.vertex!= null) {
            keyPresent= this.vertex.containsKey(key);
        }

        return keyPresent;
    }

    public Vertex getVertexExistent(T key) {
        Vertex<V> auxVertex= null;

        if (this.vertex!= null) {
            if (vertex.containsKey(key) == true) {
                auxVertex = this.vertex.get(key);
            }
        }

        return auxVertex;
    }

    public void addNewVertex(T key, Vertex value) {
        this.vertex.put(key,value);
    }

    /**
     * https://www.youtube.com/watch?v=mqfzEZ1CkJc
     * @param latitudV1
     * @param longitudV1
     * @param latitudV2
     * @param longitudV2
     * @return
     */
    public float calcDist(float latitudV1, float longitudV1, float latitudV2, float longitudV2) {
        latitudV1= (float) Math.toRadians(latitudV1);
        longitudV1= (float) Math.toRadians(longitudV1);
        latitudV2= (float) Math.toRadians(latitudV2);
        longitudV2= (float) Math.toRadians(longitudV2);

        final float RADI_TERRA= (float)6371.01;
        float distancia= (float) (RADI_TERRA*Math.acos(Math.sin(latitudV1)*Math.sin(latitudV2)
                                    + Math.cos(latitudV1)*Math.cos(latitudV2)*Math.cos(longitudV1-longitudV2)));
        return distancia;
    }

    public void printa() {
        for (T i : vertex.keySet()) {
            ChargingZones ch = (ChargingZones) vertex.get(i).getData();
            System.out.println("key: " + i + " value: " + ch.getNom());
        }
    }

    /**
     *
     */
    @Override
    public void crearGraf() {
       this.vertex = new HashMap<T, Vertex<V>>();
        this.arrest= new Arrest<E>();
    }

    /**
     * @param v1
     * @param v2
     * @param e
     * @throws InserirException
     */
    @Override
    public void afegirAresta(V v1, V v2, E e) throws InserirException {
        InserirException InserirException= new InserirException();

        if (v1!= null && v1 instanceof Vertex<?>) {
            Vertex vertex1= (Vertex) v1;
            if (v2!= null && v2 instanceof Vertex<?>) {
                Vertex vertex2= (Vertex) v2;
                if (e instanceof Arrest<?>) {
                    int r= vertex1.compareTo(vertex1,vertex2);
                    if (vertex1.compareTo(vertex1,vertex2)!= 0) {
                        Arrest newArrest= (Arrest) e;
                        Vertex largestVertex= null;
                        Vertex shortestVertex= null;

                        if (r== 1) { //v1>v2
                            newArrest.setiVex(v1);
                            newArrest.setjVex(v2);

                            largestVertex= vertex1;
                            shortestVertex= vertex2;
                        } else if (r== -1) { //v1<v2
                            newArrest.setiVex(v2);
                            newArrest.setjVex(v1);

                            largestVertex= vertex2;
                            shortestVertex= vertex1;
                        }

                        //Añadir la arista en el Vertice mas grande y pequeño
                        largestVertex.addLastArrest(this,newArrest);
                    }

                }
            }
        } else {
            System.out.println(InserirException.toString());
        }
    }

    public int compareTo(V v1, V v2) {
        int r= -1;

        if (v1!= null && v2!= null) {
            if (v1 instanceof Vertex<?>) {
                if (v2 instanceof Vertex<?>) {
                    Vertex<?> vi1= (Vertex<?>) v1;
                    Vertex<?> vi2= (Vertex<?>) v2;

                    int id_vi1= (int) vi1.getId();
                    int id_vi2= (int) vi1.getId();

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

    /**
     * @param v1
     * @param v2
     * @return
     */
    @Override
    public boolean existeixAresta(V v1, V v2) {
        boolean exists= false, exists1= false, exists2= false;

        if (v1!= null && v1 instanceof Vertex<?>) {
            Vertex vi1= (Vertex) v1;
            Vertex vi2= (Vertex) v2;

            exists1= vi1.exists(vi2);
            exists2= vi2.exists(vi1);

            if (exists1== true || exists2== true) {
                exists= true;
            }
        }

        return exists;
    }

    /**
     * @param v1
     * @param v2
     * @return
     * @throws ExistentException
     */
    @Override
    public E valorAresta(V v1, V v2) throws ExistentException {
        E valor= null;
        ExistentException ExistentException= new ExistentException();

        if (v1 instanceof Vertex<?> && v2 instanceof Vertex<?>) {
            Vertex vi1= (Vertex) v1;
            Vertex vi2= (Vertex) v2;

            valor= (E) vi1.valorConnexio(vi2);
        }

        if (valor== null) {
            System.out.println(ExistentException.toString());
        }

        return valor;
    }

    /**
     * @param v
     * @return
     * @throws InserirException
     */
    @Override
    public LlistaGenerica<V> adjacents(V v) throws ObtenirException {
        LlistaGenerica llArrest= new LlistaGenerica();
        ObtenirException ObtenirException= new ObtenirException();

        if (v instanceof Vertex<?>) {
            Vertex vi= (Vertex) v;

            llArrest= vi.llistaV();
        }

        if (llArrest== null) {
            System.out.println(ObtenirException.toString());
        }

        return llArrest;
    }
}