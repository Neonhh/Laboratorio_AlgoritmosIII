package ve.usb.libGrafo

/*
  Obtiene las componentes fuertementes conexas de un grafo 
  La componentes fuertementes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase, es decir, en el constructor.
*/
public class CFC(val g: GrafoDirigido) {

    var tiempo:Int = 0
    var CFC: MutableList<MutableSet<Int>> = mutableListOf<MutableSet<Int>>()
    var cFCId: Int = 0
    init {

        val inversoG: GrafoDirigido = ve.usb.libGrafo.digrafoInverso(g)
        val orden = OrdenamientoTopologico(g)

        for (v in g.obtenerArregloVertices()){
            v.color = Color.BLANCO
            v.pred = -1
        }
        for (v in orden.obtenerOrdenTopologico()){
            if (v.color == Color.BLANCO){
                dfsVisitTopologico(inversoG,v)
            }
        }

    }

    private fun dfsVisitTopologico(g: GrafoDirigido, v: Vertice){
        tiempo += 1 // Se empieza a explorar v
        v.tiempoInicial = tiempo // Tiempo inicial
        v.color = Color.GRIS

        if (v.pred == -1) CFC.add(mutableSetOf<Int>(v.obtenerId()))

        for (u in v.listaAdyacencia) {
            if (g.obtenerArregloVertices()[u.first].color == Color.BLANCO) {
                g.obtenerArregloVertices()[u.first].pred = v.obtenerId()
                dfsVisitTopologico(g, g.obtenerArregloVertices()[u.first])
                CFC.elementAt(cFCId).add(u.first)
            }
        }

        v.color = Color.NEGRO // Se termina de explorar v
        if (v.pred == -1) cFCId ++
        tiempo += 1
        v.tiempoFinal = tiempo
    }

    /*
     Retorna true si dos vértices están en la misma CFC y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun estanEnLaMismaCFC(v: Int, u: Int) : Boolean {
	    if (v >=  g.obtenerNumeroDeVertices() || u >= g.obtenerNumeroDeVertices())
            throw RuntimeException("Uno de los vertices no pertenece al grafo.")
        if (v == u) return true
        for (componenteCFC in CFC ) {
            if(componenteCFC.contains(v)) return componenteCFC.contains(u)

            if(componenteCFC.contains(u)) return componenteCFC.contains(v)
        }
        return false
    }

    // Indica el número de componentes fuertemente conexas del digrafo.
    fun numeroDeCFC() : Int {
        return cFCId + 1
    }

    /*
     Retorna el identificador de la componente fuertemente conexa donde está contenido 
     el vértice v. El identificador es un número en el intervalo [0 , numeroDeCFC()-1].
     Si el vértice v no pertenece al grafo g se lanza una RuntimeException
     */
    fun obtenerIdentificadorCFC(v: Int) : Int {
        if (v >=  g.obtenerNumeroDeVertices()) throw RuntimeException("El vertice no pertenece al grafo.")
        for (i in 0..cFCId){
            if (CFC.elementAt(i).contains(v)) return i
        }
        return -1
    }
    
    /*
     Retorna un objeto iterable, el cual contiene CFCs. Cada CFC esta representada
     como un  conjunto de vértices. El orden de las CFCs en el objeto iterable,
     debe corresponder al que se indica en el método obtenerIdentificadorCFC
     */
    fun  obtenerCFC() : Iterable<MutableSet<Int>> {
	    return CFC
    }

    /*
     Retorna el grafo componente asociado a las componentes fuertemente conexas. 
     El identificador de los vértices del grafo componente está asociado 
     con los orden de la CFC en el objeto iterable que se obtiene del método obtenerCFC. 
     Es decir, si por ejemplo si los vértices 5,6 y 8 de G pertenecen a la
     componentemente fuertemente conexa cero, entonces 
     obtenerIdentificadorCFC(5) = obtenerIdentificadorCFC(6) = obtenerIdentificadorCFC(8) = 0
     y se va a tener que el en grafo componente el vértice 0 
     representa a la CFC que contiene a los vértices 5,6 y 8 de G.
     */
    fun obtenerGrafoComponente() : GrafoDirigido {
	    var grafoComponente = GrafoDirigido(numeroDeCFC())
        val grafoIterador = grafoComponente.iterator()

        while (grafoIterador.hasNext()) {
            val arco: Arco = grafoIterador.next()
            val v: Int = arco.fuente()
            val u: Int = arco.sumidero()

            if (!estanEnLaMismaCFC(v, u)) {
                grafoComponente.agregarArco(Arco(obtenerIdentificadorCFC(v), obtenerIdentificadorCFC(u)))
            }
        }
        return grafoComponente
    }

}
