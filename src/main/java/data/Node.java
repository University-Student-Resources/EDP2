package data;

public class Node<T> {

    private T data;
    protected Node<T> previous;
    protected Node<T> next;

    /**
     * @desc Constructor de l'element Node
     */
    public Node() {
        this.setData(null);
        this.setPrevious(null);
        this.setNext(null);
    }

    /**
     * Constructor de l'element Node, pero afegint data
     * @param data
     */
    public Node(T data) {
        this.setNext(null);
        this.setPrevious(null);
        this.setData(data);
    }

    /**
     * @desc Passar per parametre el data, el Node previ i el Node seguent
     * @param data
     * @param previous
     * @param next
     */
    public Node(T data, Node<T> previous, Node<T> next) {
        this.setData(data);
        this.setPrevious(previous);
        this.setNext(next);
    }

    /**
     * @desc Comparar dos Objectes
     * @param data
     * @return
     */
    public int compareTo(T data) {
        int h1, h2, r= -1;

        if (data!= null) {
            h1= data.hashCode();
            h2= this.getData().hashCode();

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
    public Node<T> getPrevious() {
        return previous;
    }
    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }
    public Node<T> getNext() {
        return next;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node [data=" + data.toString() + "]\n";
    }
}
