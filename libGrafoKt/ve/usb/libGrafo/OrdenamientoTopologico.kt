package ve.usb.libGrafo

/*
  Determina el orden topológico de un DAG. El ordenamiento topológico
  se determina en el constructor de la clase.
  complejidad: O(|V|+|E|)
*/
class OrdenamientoTopologico(val g: GrafoDirigido) {

    var tiempo: Int = 0
    var arregloOrdenamieto = ArrayList<Vertice>(g.numDeVertices)
    init {

        for (v in g.obtenerArregloVertices()){
            v.color = Color.BLANCO
            v.pred = -1
        }
        for (v in g.obtenerArregloVertices()){
            if (v.color == Color.BLANCO){
                dfsVisit(g,v)
            }
        }
    }
    private fun dfsVisit(g: Grafo, v: Vertice) {
        tiempo += 1 // Se empieza a explorar v
        v.tiempoInicial = tiempo // Tiempo inicial
        v.color = Color.GRIS

        for (u in v.listaAdyacencia) {
            if (g.obtenerArregloVertices()[u.first].color == Color.BLANCO) {
                g.obtenerArregloVertices()[u.first].pred = v.obtenerId()
                dfsVisit(g, g.obtenerArregloVertices()[u.first])
            }
        }

        v.color = Color.NEGRO // Se termina de explorar v
        tiempo += 1
        v.tiempoFinal = tiempo
        arregloOrdenamieto.add(v)
    }
    // Retorna true si el grafo g es un DAG, de lo contrario retorna false
    // Como se hace DFS para esta funcion su complejidad es O(|V|+|E|)
    fun esDAG() : Boolean {
        return (g is GrafoDirigido && !CicloDigrafo(g).existeUnCiclo())
    }

    // Retorna el ordenamiento topológico del grafo g. Si el grafo G no es DAG,
    // entonces se lanza una RuntineException()
    // Su complejidad es O(|V|+|E|) ya que llama a esDAG
    fun obtenerOrdenTopologico() : Iterable<Vertice> {
        if (!esDAG()) throw RuntimeException("El grafo no es DAG")
        else {
            return arregloOrdenamieto
        }
    }
}
