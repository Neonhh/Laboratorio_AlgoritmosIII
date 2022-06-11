package ve.usb.libGrafo

/* 
   Implementación del algoritmo BFS. 
   Con la creación de la instancia, se ejecuta el algoritmo BFS
   desde el vértice s
*/
public class BFS(val g: Grafo<Vertice>, val s: Int) {

    var cola: MutableList<Vertice> = mutableListOf()
    val verticeS: Vertice = g.obtenerArregloVertices()[s]
    init {
        for(v in g.obtenerArregloVertices()){
            v.color = Color.BLANCO
            v.pred = -1
            v.distancia = -1
        }
        verticeS.distancia = 0
        verticeS.color = Color.GRIS
        cola = mutableListOf(verticeS)

        while(cola.size >= 1){
            val verticeU: Vertice = cola[0]
            cola.removeAt(0)

            if (verticeU is VerticeCosto){
                for(v in verticeU.listaAdyacenciaCosto){
                val verticeV: Vertice = g.obtenerArregloVertices()[v.first]
                    if(verticeV.color == Color.BLANCO){
                        verticeV.color = Color.GRIS
                        verticeV.distancia = verticeU.distancia + 1
                        verticeV.pred = verticeU.costoId
                        cola.add(verticeV)
                    }
                }
            }
            else{
                for(v in verticeU.listaAdyacencia){
                    val verticeV: Vertice = g.obtenerArregloVertices()[v]
                    if(verticeV.color == Color.BLANCO){
                        verticeV.color = Color.GRIS
                        verticeV.distancia = verticeU.distancia + 1
                        verticeV.pred = verticeU.id
                        cola.add(verticeV)
                    }
                }
            }
            verticeU.color = Color.NEGRO
        }
    }

    fun runtimeNoExiste(v: Int) {
        if (v>=g.numDeVertices) throw RuntimeException("El vertice ${v} no existe en el grafo.")
    }

    /*
     Retorna el predecesor de un vértice v. Si el vértice no tiene predecesor
     se retorna null. En caso de que el vértice v no exista en el grafo se lanza
     una RuntimeException.
     Parametros: v: Int, numero que identifica a un vertice en la lista de adyacencia
     Precondiciones: v representa a algun vertice en el grafo
     Postcondiciones: 0 <= obtenerPredecesor <= g.numDeVertices o obtenerPredecesor == null
     Tiempo: O(1)
     */
    fun obtenerPredecesor(v: Int) : Int? {
        runtimeNoExiste(v)
        val vertice = g.obtenerArregloVertices()[v]
        val predecesor = vertice.pred
        if (predecesor == -1) return null
        return predecesor
    }

    /*
     Retorna la distancia, del camino obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. En caso de que el vétice v no sea alcanzable desde s,
     entonces se retorna -1.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     Parametros: v: Int, id del vertice cuya distancia hasta s se quiere tomar
     Precondiciones: el vertice que tiene v como id pertenece al grafo
     Postcondiciones: obtenerDistancia <= g.obtenerNumeroDeLados()
     */
    fun obtenerDistancia(v: Int) : Int {
        runtimeNoExiste(v)
        val vertice = g.obtenerArregloVertices()[v]
        return vertice.distancia
    }

    /*
     Indica si hay camino desde el vértice inicial s hasta el vértice v.
     Si el camino existe retorna true, de lo contrario falso.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException. 
     Parametros: v: Int, id del vertice del cual se quiere conocer la existencia del camino
     Precondiciones: v representa a un vertice del grafo
     Postcondiciones: hayCaminoHasta == la distancia del vertice hasta s es >= 0
     Costo: O(1)
     */
    fun hayCaminoHasta(v: Int) : Boolean {
        runtimeNoExiste(v)
        val vertice = g.obtenerArregloVertices()[v]
        return(vertice.distancia >= 0)
    }

    /*
     Retorna el camino con menos lados, obtenido por BFS, desde el vértice inicial s 
     hasta el un vértice v. El camino es representado como un objeto iterable con
     los vértices del camino desde s hasta v.
     En caso de que el vértice v no sea alcanzable desde s, entonces se lanza una RuntimeException.
     En caso de que el vértice v no exista en el grafo se lanza una RuntimeException.

     Precondidiones: El vertice representado con v existe en el grafo y es alcanzable desde s.

     Postcondiciones: 0 <= caminoHasta.size <= g.obtenerNumeroDeLados(), todos los elementos de caminoHasta
     son enteros que hacen referencia a algun vertice que existe en el grafo.

     Costo: O(|E|**2), exagerado ya que las primeras veces que agregamos elementos al iterable la complejidad se
     acerca mas a O(1) para la operacion de agregar al camino
     */
    fun caminoHasta(v: Int) : Iterable<Int>  {
        runtimeNoExiste(v)
        var vertice = g.obtenerArregloVertices()[v]
        if (vertice.distancia == -1) throw RuntimeException("El vertice ${v} no es alcanzable desde s.")

        var camino: MutableList<Int> = mutableListOf(v)
        while(vertice.pred != s){
            val predecesor :Int = vertice.pred
            val verticepred :Vertice = g.obtenerArregloVertices()[predecesor]

            camino.add(0,predecesor)
            vertice = verticepred
        }
        camino.add(0,s)
        return camino
    }

    /* Imprime por la salida estándar el breadth-first tree
    Parametros: N/A
    Precondicion: Se ha corrido BFS al menos una vez
    Poscondicion: N/A
     */
    fun mostrarArbolBFS() {
        for(v in g.obtenerArregloVertices()){
            println("Vertice ${v.obtenerId()}, distancia a" +
                    "${verticeS.obtenerId()}: ${v.distancia}," +
                    "color: ${v.color}")
        }
    }
}
