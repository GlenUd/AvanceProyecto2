public class ArbolBSTDonantes {

    private NodoDonanteBST root;

    public void insertar(Donante d) {
        if (d == null || d.getCedula() == null) return;
        String ced = d.getCedula().trim();
        if (ced.isEmpty()) return;

        root = insertarRec(root, d);
    }

    private NodoDonanteBST insertarRec(NodoDonanteBST nodo, Donante d) {
        if (nodo == null) return new NodoDonanteBST(d);

        String cedulaNueva = d.getCedula().trim();
        String cedulaNodo = nodo.data.getCedula().trim();

        int cmp = cedulaNueva.compareTo(cedulaNodo);

        if (cmp < 0) {
            nodo.left = insertarRec(nodo.left, d);
        } else if (cmp > 0) {
            nodo.right = insertarRec(nodo.right, d);
        } else {
            // Si ya existe la cédula, actualiza el registro
            nodo.data = d;
        }

        return nodo;
    }

    public Donante buscar(String cedula) {
        if (cedula == null) return null;
        String c = cedula.trim();
        if (c.isEmpty()) return null;

        return buscarRec(root, c);
    }

    private Donante buscarRec(NodoDonanteBST nodo, String cedula) {
        if (nodo == null) return null;

        String cedulaNodo = nodo.data.getCedula().trim();
        int cmp = cedula.compareTo(cedulaNodo);

        if (cmp == 0) return nodo.data;
        if (cmp < 0) return buscarRec(nodo.left, cedula);
        return buscarRec(nodo.right, cedula);
    }

    // (Opcional) Rebuild: útil si borras y quieres reconstruir rápido
    public void limpiar() {
        root = null;
    }
}

