package ve.usb.libGrafo

/*
  Determina las componentes conexas de un grafo no dirigido usando DFS. 
  La componentes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase.
*/
public class ComponentesConexasDFS(val g: GrafoNoDirigido) {

    var tiempo: Int = 0
    var contadorCC: Int = 0

    init {
        for (vertice in g.arregloVertices){
            vertice.color = Color.BLANCO
            vertice.pred = -1
        }
        tiempo = 0
        contadorCC = 0
        for (vertice in g.arregloVertices){
            if(vertice.color == Color.BLANCO){
                contadorCC ++
                dfsVisit(g,vertice)
            }
        }
    }
    fun dfsVisit(g: GrafoNoDirigido, v: Vertice){
        tiempo += 1 // Se empieza a explorar v
        v.tiempoInicial = tiempo // Tiempo inicial
        v.cc = contadorCC
        v.color = Color.GRIS

        for (u in v.obtenerAdyacencias()) {
            if (g.obtenerArregloVertices()[u.first].color == Color.BLANCO) {
                g.obtenerArregloVertices()[u.first].pred = v.obtenerId()
                dfsVisit(g, g.obtenerArregloVertices()[u.first])
            }
        }

        v.color = Color.NEGRO // Se termina de explorar v
        tiempo += 1
        v.tiempoFinal = tiempo
    }
    /*
     Retorna true si los dos vertices están en la misma componente conexa y
     falso en caso contrario.
     Parametros: v,u, enteros
     Precondiciones: v y u representan vertices que deben pertenecer al grafo
     Postcondiciones: estanMismaComponente == (verticeU.cc == verticeV.cc)
     Costo: O(1)
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean {
        if (v >=  g.obtenerNumeroDeVertices() || u >= g.obtenerNumeroDeVertices())
            throw RuntimeException("Uno de los vertices no pertenece al grafo.")
        if (v == u) return true

        val verticesGrafo: ArrayList<Vertice> = g.obtenerArregloVertices()
        return (verticesGrafo[u].cc == verticesGrafo[v].cc)
    }

    /* Indica el número de componentes conexas
    Postcondiciones: nCC es de tipo entero, 0<= nCC <= g.numDeVertices
    Costo: O(1)
    */
    fun nCC() : Int {
	    return contadorCC
    }

    /*
     Retorna el identificador de la componente conexa donde está contenido 
     el vértice v.
     Parametros: v, entero
     Precondiciones: v representa un vertice que debe pertenecer al grafo
     Postcondiciones: obtenerComponente es un entero, 0 <= obtenerComponente <= nCC()-1
     Costo: O(1)
     */
    fun obtenerComponente(v: Int) : Int {
        if (v<0 || v >=  g.obtenerNumeroDeVertices())
            throw RuntimeException("El vertice no pertenece al grafo.")
        return g.obtenerArregloVertices()[v].cc - 1
    }

    /* Retorna el número de vértices que conforman una componente conexa dada.
     Parametros: compID, entero
     Precondiciones: compID esta en el intervalo [0 , nCC()-1] (es identificador de alguna cc)
     Postcondiciones: numVerticesDeLaComponente es un entero, 0 <= numVerticesDeLaComponente <= g.numDeVertices
     Costo: O(1)
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {
        if (compID<0 || compID >=  contadorCC)
            throw RuntimeException("El id no corresponde a ninguna componente conexa.")

        var verticesCC: Int = 0
        var contVertice: Int = 0
        while(contVertice < g.numDeVertices){
            if (obtenerComponente(contVertice++) == compID) verticesCC ++
        }
        return verticesCC

    }

}
