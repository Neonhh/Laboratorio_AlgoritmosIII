package ve.usb.libGrafo

/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/

public class DFS(val g: Grafo) {
    var tiempo: Int = 0
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
            if (g.obtenerArregloVertices()[u.first].color == Color.GRIS) {
                g.obtenerArregloVertices()[u.first].pred = v.obtenerId()
                dfsVisit(g, g.obtenerArregloVertices()[u.first])
            }
        }

        v.color = Color.NEGRO // Se termina de explorar v
        tiempo += 1
        v.tiempoFinal = tiempo
    }

    fun runtimeNoExiste(v: Int) {
        if (v>=g.numDeVertices) throw RuntimeException("El vertice ${v} no existe en el grafo.")
    }
    /*
     Retorna el predecesor de un vértice v. Si el vértice no tiene predecesor 
     se retorna null. En caso de que el vértice v no exista en el grafo se lanza
     una RuntimeException

     Parametros: v: Int, numero que identifica a un vertice en la lista de adyacencia
     Precondiciones: v representa a algun vertice en el grafo
     Postcondiciones: 0 <= obtenerPredecesor <= g.numDeVertices o obtenerPredecesor == null
     Tiempo: O(1)
     */    
    fun obtenerPredecesor(v: Int) : Int? {
        runtimeNoExiste(v)
        if (g.obtenerArregloVertices()[v].pred == -1){
            return null
        }
        else {
            return g.obtenerArregloVertices()[v].pred
        }
    }

     /*
     Retorna un par con el tiempo inical y final de un vértice durante la ejecución de DFS. 
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException

     Parametros: v: Int, id del vertice que se quieren saber sus tiempos
     precondiciones: que el vertive de id v exista
     complejidad: O(1)
     */
    fun obtenerTiempos(v: Int) : Pair<Int, Int> {
         runtimeNoExiste(v)
         return Pair(g.obtenerArregloVertices()[v].tiempoInicial, g.obtenerArregloVertices()[v].tiempoFinal)
    }

    /*
     Indica si hay camino desde el vértice inicial u hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso
     En caso de que alguno de los vértices no exista en el grafo se lanza una RuntimeException

     parametros: v: Int, u: Int, son los id de dos vertices, se quiere saber si hay camino desde v hasta u
     precondicones: que los vertices cuyos id son u y v existan
     postcondicon: retornan true si  u.d < v.d < v.f < u.f, false otherwise
     complejidad: O(1)
     */ 
    fun hayCamino(u: Int, v: Int) : Boolean {
        runtimeNoExiste(v)
        runtimeNoExiste(u)
        return g.obtenerArregloVertices()[u].tiempoInicial < g.obtenerArregloVertices()[v].tiempoInicial &&
                g.obtenerArregloVertices()[v].tiempoFinal < g.obtenerArregloVertices()[u].tiempoFinal
    }

    /*
     Retorna el camino desde el vértice  u hasta el un vértice v. 
     El camino es representado como un objeto iterable con los vértices del camino desde u hasta v.
     En caso de que no exista un camino desde u hasta v, se lanza una RuntimeException. 
     En caso de que alguno de los vértices no exista en el grafo se lanza una RuntimeException.

     parametros: u: int y v: int son el id de dos vertices de los cuales se quiere conocer el camino
     precondicion: que los verrtices cuyos id son u y v existan y qye exista un camino entre ellos
     postcondicon: 0 <= camino <= numero de vertices
     complejidad: O(|E|**2), exagerado ya que las primeras veces que agregamos elementos al iterable la complejidad se
     acerca mas a O(1) para la operacion de agregar al camino
     */ 
    fun caminoDesdeHasta(u: Int, v: Int) : Iterable<Int>  {
        if (!hayCamino(u,v)){
            throw RuntimeException("No existe camino entre ${u} y ${v}.")
        }
        else {
            var vertice = g.obtenerArregloVertices()[u]
            var camino: MutableList<Int> = mutableListOf(v)

            while (vertice.pred != v) {
                val predecesor: Int = vertice.pred
                val verticepred: Vertice = g.obtenerArregloVertices()[predecesor]

                camino.add(0, predecesor)
                vertice = verticepred

            }
            camino.add(0, v)
            return camino
        }
    }

    /* Retorna true si hay lados del bosque o false en caso contrario.

        precondiciones: que el grafo g exista
        complejidad: O(|V|*|E|)
     */
    fun hayLadosDeBosque(): Boolean {
        for (v in g.obtenerArregloVertices()){
            for (x in v.listaAdyacencia){
                if ( g.obtenerArregloVertices()[x.first].tiempoInicial == v.tiempoInicial+1  ){
                    return true
                }
            }
        }
        return false
    }
    
    // Retorna los lados del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    //fun ladosDeBosque() : Iterator<Lado> { }

    // Retorna true si hay forward edges o false en caso contrario.
    //precondiciones: que el grafo g exista
    // complejidad: O(|V|*|E|)
    fun hayLadosDeIda(): Boolean {
        for (v in g.obtenerArregloVertices()){
            for (x in v.listaAdyacencia){
                if ( v.tiempoInicial+1 < g.obtenerArregloVertices()[x.first].tiempoInicial &&
                    g.obtenerArregloVertices()[x.first].tiempoFinal+1 < v.tiempoFinal ){
                    return true
                }
            }
        }
        return false
    }
    
    // Retorna los forward edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    //fun ladosDeIda() : Iterator<Lado> { }

    // Retorna true si hay back edges o false en caso contrario.
    //precondiciones: que el grafo g exista
    // complejidad: O(|V|*|E|)
    fun hayLadosDeVuelta(): Boolean {
        for (v in g.obtenerArregloVertices()){
            for (x in v.listaAdyacencia){
                if ( v.tiempoInicial > g.obtenerArregloVertices()[x.first].tiempoInicial &&
                    g.obtenerArregloVertices()[x.first].tiempoFinal > v.tiempoFinal ){
                    return true
                }
            }
        }
        return false
    }
    
    // Retorna los back edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    //fun ladosDeVuelta() : Iterator<Lado> { }

    // Retorna true si hay cross edges o false en caso contrario.
    //precondiciones: que el grafo g exista
    // complejidad: O(|V|*|E|)
    fun hayLadosCruzados(): Boolean {
        for (v in g.obtenerArregloVertices()){
            for (x in v.listaAdyacencia){
                if ( v.tiempoInicial > g.obtenerArregloVertices()[x.first].tiempoFinal ||
                    g.obtenerArregloVertices()[x.first].tiempoInicial > v.tiempoFinal ){
                    return true
                }
            }
        }
        return false
    }
    
    // Retorna los cross edges del bosque obtenido por DFS.
    // Si no existen ese tipo de lados, entonces se lanza una RuntimeException.
    //fun ladosCruzados() : Iterator<Lado> { }

    // Imprime por la salida estándar el depth-first forest.
    fun mostrarBosqueDFS() {
        for (v in g.obtenerArregloVertices()){
            println( "Vertice ${v.obtenerId()}, tiempo inicial ${v.tiempoInicial}, tiempo final ${v.tiempoFinal}, color ${v.color}")
        }
    }
    
}
