package data;

import java.util.ArrayList;

public class LlistaGenerica<V> {
    private ArrayList<V> data= null;

    public LlistaGenerica() {
        data= new ArrayList<>();
    }

    public void afegir(V v) {
        data.add(v);
    }

    public String print() {
        String infoPrint= "";
        Arrest auxPrint;

        for(int i = 0; i < data.size(); i++) {
            auxPrint= (Arrest) data.get(i);
            infoPrint+= auxPrint.getiVex().getId()+"<--->"+auxPrint.getjVex().getId();
            infoPrint+= "\n";
        }

        return infoPrint;
    }


    public ArrayList<V> getData() {
        return data;
    }
    public void setData(ArrayList<V> data) {
        this.data = data;
    }
}
