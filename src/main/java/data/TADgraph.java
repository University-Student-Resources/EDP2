package data;
import exceptions.*;

public interface TADgraph<V,E> {
    public void crearGraf();
    public void afegirAresta(V v1, V v2, E e) throws InserirException;
    public boolean existeixAresta(V v1, V v2);
    public E valorAresta(V v1, V v2) throws ExistentException;
    public LlistaGenerica<V> adjacents(V v) throws ObtenirException;
}
