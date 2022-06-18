package ve.usb.libGrafo

/* 
   Determina la existencia o no de un ciclo en un digrafo.
   En el momento de la creación de un objeto de este tipo,
   se ejecuta una versión de  DFS que determina la existencia 
   o no de un ciclo por lo que su complejidad es O(|V|+|E|)
*/
public class CicloDigrafo(val g: GrafoDirigido) {
    var tiempo: Int = 0
    var backedges= ArrayList<Int>(g.numDeVertices)
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
            else if (g.obtenerArregloVertices()[u.first].color == Color.GRIS){
                backedges.add(u.first)
            }
        }

        v.color = Color.NEGRO // Se termina de explorar v
        tiempo += 1
        v.tiempoFinal = tiempo
    }
    // Retorna true si el digrafo G tiene un ciclo, false en caso contrario
    // complejidad O(1)
    fun existeUnCiclo() : Boolean {
        return !backedges.isEmpty()
    }

    // Si el grafo tiene un ciclo, entonces retorna la secuencia de vértices del ciclo,
    // y en caso contrario retorna una RuntineException.
    // complejidad O(1)
    fun cicloEncontrado() : Iterable<Int> {
        if (existeUnCiclo()){
            return backedges
        }
        else{
            throw RuntimeException("El grafo no tiene ciclos")
        }
    }
} 
