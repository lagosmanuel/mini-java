//[SinErrores]
class Init{
    static void main() {
        Lista<String> lista = new Lista<>();
        for (String s:lista);
    }
}

class Lista<T> extends MiniIterable<T> {
    T next() {
        return null;
    }

    boolean hasNext() {
        return false;
    }

    void start() {

    }
}



