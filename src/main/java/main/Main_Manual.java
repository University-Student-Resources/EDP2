package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import com.google.gson.Gson;
import data.*;
import algorithm.*;
import exceptions.CrearException;
import exceptions.InserirException;

public class Main_Manual<T> {
    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        Graph<Object, ChargingZones,Arrest> graph= new Graph();

        Properties[] data= readJSON(gson);
        saveData(graph,data);

        graph.printa();
        unio(graph);
        menu(graph);
    }

    private static void menu(Graph<Object, ChargingZones,Arrest> graph) throws CrearException {
        String posIni,posFin;
        int autonomia;
        Algorithm algo= new Algorithm(graph);
        LlistaGenerica<String> listCamiOptim;
        LlistaGenerica<String> listNoGrantida;

        boolean s=false;
        do {
            System.out.println("\n\nINFO USUARI:\n"
                    + "============");
            System.out.print("Posició inicial -->  ");
            posIni = keyboard.next();
            System.out.print("Autonomia -->  ");
            autonomia = keyboard.nextInt();
            System.out.print("Destí -->  ");
            posFin = keyboard.next();

            listCamiOptim = algo.camiOptim(posIni, posFin, autonomia);
            listNoGrantida = algo.zonesDistMaxNoGarantida(posIni,autonomia);

            System.out.println("\n=======OUTPUT=======");
            if (listCamiOptim!= null) {
                System.out.println("\n-----Cami Optim-----");
                String printa = listCamiOptim.print();
                System.out.println(printa);
            }

            if (listNoGrantida!= null) {
                System.out.println("\n-----Zones no garantides-----");
                String printa = listNoGrantida.print();
                System.out.println(printa);
            }
        } while (!s);
    }

    private static void unio(Graph graph) throws InserirException {
        graph.unioArestes();
    }

    private static Properties[] readJSON(Gson gson) {
        String content= "";
        try (BufferedReader br= new BufferedReader(new FileReader("src/main/resources/icaen.json"))) {

            String line;
            while ((line= br.readLine()) != null) {
                content+= line;
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        Properties[] data = gson.fromJson(content, Properties[].class);

        return data;
    }

    private static void saveData(Graph graph, Properties[] data) {

        for (int i= 0; i<data.length; i++) {

            /* Save info */
            String id_estacioString= data[i].getProperty("id_estacio");
            int id_estacio= Integer.parseInt(id_estacioString);

            String idString= data[i].getProperty("id");
            int id= Integer.parseInt(idString);

            String potenciaString= data[i].getProperty("potencia");
            float potencia= Float.parseFloat(potenciaString);

            String estat= data[i].getProperty("estat");
            String tipus= data[i].getProperty("tipus");

            Endolls newEndoll= new Endolls(id,estat,potencia,tipus);

            /* IF: ChargingZone exists THEN: Check if Endoll exists--> YES: Don't add
             *                                                          NO: Add Endoll
             * IF: ChargingZone don't exist THEN: Add new ChargingZone and new Endoll */
            if (graph.comprovarVertex(id_estacio)== true) { /* Case if exists */
                Vertex auxVertex= graph.getVertexExistent(id_estacio);

                ChargingZones auxChargingZones= (ChargingZones) auxVertex.getData();

                if (auxChargingZones.comprovarEndoll(newEndoll)== false) {
                    auxChargingZones.addNewEndoll(newEndoll);
                }
            } else { /* Case if don't exists */
                /* Save info to create new ChargingZone */
                String latitudString= data[i].getProperty("latitud");
                float latitud= Float.parseFloat(latitudString);

                String longitudString= data[i].getProperty("longitud");
                float longitud= Float.parseFloat(longitudString);

                String nom= data[i].getProperty("nom");
                String carrer= data[i].getProperty("carrer");
                String ciutat = data[i].getProperty("ciutat");

                ChargingZones newChargingZones= new ChargingZones(id_estacio,latitud,longitud,nom,carrer,ciutat,newEndoll);

                Vertex newVertex= new Vertex(newChargingZones.getId_estacio(),newChargingZones);
                graph.addNewVertex(newVertex.getId(),newVertex);
            }
        }
    }
}


