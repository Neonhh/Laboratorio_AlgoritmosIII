package ve.usb.libGrafo

/*
  Determina el orden topológico de un DAG. El ordenamiento topológico
  se determina en el constructor de la clase.  
*/
public class OrdenamientoTopologico(val g: GrafoDirigido) {

    // Retorna true si el grafo g es un DAG, de lo contrario retorna false
    fun esDAG() : Boolean { }

    // Retorna el ordenamiento topológico del grafo g. Si el grafo G no es DAG,
    // entonces se lanza una RuntineException()
    fun obtenerOrdenTopologico() : Iterable<Int> { }
}
