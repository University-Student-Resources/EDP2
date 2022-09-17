package algorithm;

import data.Arrest;
import data.LlistaGenerica;
import exceptions.*;

public interface TADalgorithm {
    public LlistaGenerica<String> camiOptim(String identificador_origen, String indentificador_desti, int autonomia) throws CrearException;
    public LlistaGenerica<Arrest> zonesDistMaxNoGarantida(String indentificador_origen, int autonomia) throws CrearException;
}
