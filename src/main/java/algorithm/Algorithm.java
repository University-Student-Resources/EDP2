package algorithm;

import data.*;
import exceptions.*;

import java.util.*;

public class Algorithm<T> implements TADalgorithm {
    Graph graph;

    Queue<T> queueIdVisitados = new PriorityQueue<T>();
    Queue<T> queueId = new PriorityQueue<T>();
    ArrayList<Arrest> queueNoArribarAutonomia = new ArrayList<Arrest>();
    Arrest vMenorDistancia;
    float sumaDistancies= 0;
    Vertex nextV= null;

    public Algorithm(Graph graph) {
        this.graph= graph;
    }

    /**
     * @param identificador_origen
     * @param indentificador_desti
     * @param autonomia
     * @return
     */
    @Override
    public LlistaGenerica<String> camiOptim(String identificador_origen, String indentificador_desti, int autonomia) throws CrearException {
        boolean exit= false;
        int id_origen= Integer.parseInt(identificador_origen);
        int id_desti= Integer.parseInt(indentificador_desti);
        LlistaGenerica<String> llIdCamins= new LlistaGenerica<String>();
        Vertex start;
        Vertex fin;
        CrearException CrearException= new CrearException();

        if (graph.comprovarVertex(id_origen)== true) {
            if (graph.comprovarVertex(id_desti)== true) {
                start= graph.getVertexExistent(id_origen);
                fin= graph.getVertexExistent(id_desti);
                queueIdVisitados.clear();
                queueId.clear();
                sumaDistancies= 0;
                exit= this.dijkstra(start,fin,autonomia);

                if (exit== true) {
                    for(Object item : queueId) {
                        llIdCamins.afegir(item.toString());
                    }
                    String suma= Float.toString(sumaDistancies);
                    llIdCamins.afegir("("+suma+"km)");
                    return llIdCamins;
                }
            }
        }

        if (exit== false) {
            System.out.println(CrearException);
            return llIdCamins;
        }

        return null;
    }

    private boolean dijkstra(Vertex start, Vertex fin, int autonomia) {
        boolean arribar= false;

        //0. Comprobar que no estemos en el final
        if (start!= null) {
            if (start.compareTo(start, fin) != 0) {
                Arrest startHopArrest = start.getNextArrest();
                vMenorDistancia = startHopArrest;

                //1. Recorrer las aristas del vertice "start"
                while (startHopArrest != null) {

                    if (start.compareTo(start, startHopArrest.getiVex()) == 0) {
                        //2. Omitir los vertices visitados, para no volver de nuevo y no contarlos
                        if (queueIdVisitados.contains(startHopArrest.getjVex().getId()) == false) {
                            //3. Buscar/comparar el quien tiene menor distancia y guardarlo
                            if (vMenorDistancia.menorDistancia(startHopArrest) == -1) { //startHopArrest< vMenorDistancia
                                vMenorDistancia = startHopArrest;
                                nextV = startHopArrest.getjVex();
                            } else if (startHopArrest.compareTo(vMenorDistancia) == 0) { //startHopArrest< vMenorDistancia, aplicamos el maximo que tenga la potencia màs grande para que este menos tiempo
                                if (startHopArrest.getiVex().getData() instanceof ChargingZones) {
                                    if (startHopArrest.getjVex().getData() instanceof ChargingZones) {
                                        ChargingZones chargingZonesI = (ChargingZones) startHopArrest.getiVex().getData();
                                        ChargingZones chargingZonesJ = (ChargingZones) startHopArrest.getiVex().getData();

                                        if (chargingZonesI.getMaxPotenciaEndoll() > chargingZonesJ.getMaxPotenciaEndoll()) {
                                            vMenorDistancia = startHopArrest;
                                            nextV = startHopArrest.getiVex();
                                        } else if (chargingZonesJ.getMaxPotenciaEndoll() >= chargingZonesI.getMaxPotenciaEndoll()) {
                                            vMenorDistancia= startHopArrest;
                                            nextV= startHopArrest.getjVex();
                                        }
                                    }
                                }
                            }
                            queueIdVisitados.add((T) startHopArrest.getjVex().getId());
                        }
                        startHopArrest = startHopArrest.getNextPosA();
                    } else {
                        if (queueIdVisitados.contains(startHopArrest.getiVex().getId()) == false) {
                            if (vMenorDistancia.menorDistancia(startHopArrest)== -1) { //startHopArrest< vMenorDistancia
                                vMenorDistancia= startHopArrest;
                                nextV= startHopArrest.getiVex();
                            }
                            queueIdVisitados.add((T) startHopArrest.getiVex().getId());
                        }
                        startHopArrest = startHopArrest.getNextPosB();
                    }
                }

                //3.1 Mirar si con la autonomia puedo llegar
                if (nextV!= null) {
                    if (vMenorDistancia.menorDistancia(autonomia) != 1) {
                        queueIdVisitados.add((T) start.getId());
                        queueId.add((T) nextV.getId());
                        sumaDistancies+= (float)vMenorDistancia.getData();
                        this.dijkstra(nextV, fin, autonomia);
                        return arribar = true;
                    }
                }
            }
        }

        return arribar;
    }

    /**
     * @param indentificador_origen
     * @param autonomia
     * @return
     */
    @Override
    public LlistaGenerica<Arrest> zonesDistMaxNoGarantida(String indentificador_origen, int autonomia) throws CrearException {
        int id_origen= Integer.parseInt(indentificador_origen);
        LlistaGenerica<Arrest> llIdCamins= new LlistaGenerica<Arrest>();
        Vertex start;
        CrearException CrearException= new CrearException();

        if (graph.comprovarVertex(id_origen)== true) {
            start= graph.getVertexExistent(id_origen);
            queueIdVisitados.clear();
            queueNoArribarAutonomia.clear();
            float autonomiaFloat= autonomia;
            this.recorregut_pl2(start,autonomiaFloat);

            if (queueNoArribarAutonomia.size()== start.getnArrest()) {
                for(int i= 0; i< queueNoArribarAutonomia.size(); i++) {
                    llIdCamins.afegir(queueNoArribarAutonomia.get(i));
                }
            } else {
                llIdCamins= null;
            }
        } else {
            System.out.println(CrearException);
        }

        return llIdCamins;
    }
/*
    private void recorregut_pl1(Vertex start, int autonomia) {
        Arrest startHopArrest = start.getNextArrest();

        while (startHopArrest != null) {
            if (start.compareTo(start, startHopArrest.getiVex()) == 0) {
                queueIdVisitados.add((T) startHopArrest.getjVex().getId());
                float autonomiaFloat= autonomia;
                float distancia= (float) startHopArrest.getData();
                if (distancia>autonomiaFloat) {
                    queueNoArribarAutonomia.add((T) startHopArrest.getjVex().getId());
                }
                startHopArrest = startHopArrest.getNextPosA();
            } else {
                if (queueIdVisitados.contains(startHopArrest.getiVex().getId()) == false) {
                    queueIdVisitados.add((T) startHopArrest.getiVex().getId());
                    startHopArrest = startHopArrest.getNextPosB();
                }
            }
        }
    }
*/
    private void recorregut_pl2(Vertex start, float autonomia) {
        Arrest startHopArrest = start.getNextArrest();
        queueIdVisitados.add((T) start.getId());

        //0. Recorrer las aristas de ese vertice
        while (startHopArrest!= null) {
            float distancia= (float) startHopArrest.getData();

            //0.1 En caso de que iVex sea igual al vertice, evaluamos
            if (start.compareTo(start, startHopArrest.getiVex())== 0) {
                //1. Añadimos a la cola de visitados, las aristas que estamos evaluando
                //queueIdVisitados.add((T) startHopArrest.getjVex().getId());

                if (queueIdVisitados.contains(startHopArrest.getjVex().getId())== false) {
                    if (distancia<= autonomia) {
                        this.recorregut_pl2(startHopArrest.getjVex(),autonomia);
                    } else {
                        queueNoArribarAutonomia.add(startHopArrest);
                    }
                }
                startHopArrest = startHopArrest.getNextPosA();
            } else { //0.1 En caso de que jVex sea igual al vertice, evaluamos
                if (queueIdVisitados.contains(startHopArrest.getiVex().getId()) == false) {
                    if (distancia<= autonomia) {
                        this.recorregut_pl2(startHopArrest.getiVex(),autonomia);
                    } else {
                        queueNoArribarAutonomia.add(startHopArrest);
                    }
                    startHopArrest = startHopArrest.getNextPosB();
                }
            }
        }
    }
}
