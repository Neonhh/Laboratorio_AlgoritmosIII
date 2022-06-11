package ve.usb.libGrafo

import java.io.File

public class GrafoDirigido : Grafo<Vertice> {

    override var numDeVertices : Int = 0
    var arregloVertices = ArrayList<Vertice>(numDeVertices)
    var numDeLados: Int =0
    override var cabeza: Nodo<Vertice>? = null

    override fun esVacio(): Boolean = (numDeVertices == 0)

    // Se construye un grafo dirigido a partir del número de vértices complejidad: O(V)
    // recibe como argumentos un entero que debe ser el numero de vertices
    constructor(numDeVerticesIn: Int) {
        numDeVertices = numDeVerticesIn
        for (i in 0..numDeVertices-1){
            arregloVertices.add(Vertice(i))
        }
    }

    /*
     Se construye un grafo dirigido a partir de un archivo, la funcion tiene como argumento un string el cual debe ser el nombre de un archivo .txt
     complejidad: O(V)
     */
    constructor(nombreArchivo :String) {
        val archivo = File(nombreArchivo)
        var contador: Int = 0


        archivo.forEachLine {
            if (contador == 0) {
                numDeVertices = it.toInt()
                for (i in 0..numDeVertices-1) {
                    arregloVertices.add(Vertice(i))
                }
                contador += 1
            } else if (contador == 1) {
                val numDeLados: Int = it.toInt()
                contador+=1
            } else {
                val datosLado = it.split(" ")
                val u = datosLado.elementAt(0).toInt()
                val v = datosLado.elementAt(1).toInt()

                agregarArco(Arco(u, v))

                contador+=1
            }
        }
    }


    /* Agrega un arco al grafo recibe como parametro un Arco complejidad: O(1)
       retorna true
     */
    fun agregarArco(a: Arco) : Boolean {

        val desde : Int = a.fuente()
        val hasta : Int = a.sumidero()


        arregloVertices[desde].aggVerticelistaAdyacencia(hasta)
        numDeLados += 1
        arregloVertices[desde].aumentarGradoExterior()
        arregloVertices[hasta].aumentarGradoInterior()


        return true
    }

    override fun obtenerArregloVertices(): ArrayList<Vertice> {
        return arregloVertices
    }

    override fun obtenerArregloVerticesCosto(): ArrayList<VerticeCosto> {
        TODO("Not yet implemented")
    }
    // Retorna el grado de un vertice del grafo complejidad: O(1)
    // El grado es la suma del grado interior y el exterior
    // recibe como parametros un int v que debe ser el id del vertice

    override fun grado(v: Int) : Int {

        return arregloVertices[v].gradoInterior+arregloVertices[v].gradoExterior
    }

    // Retorna el grado exterior de un vertice del grafo complejidad: O(1)
    // recibe como parametros un int v que debe ser el id del vertice
    fun gradoExterior(v: Int) : Int {
        return arregloVertices[v].gradoExterior
    }

    // Retorna el grado interior de un vertice del grafo complejidad: O(1)
    // recibe como parametros un int v que debe ser el id del vertice
    fun gradoInterior(v: Int) : Int {
        return arregloVertices[v].gradoInterior
    }

    // Retorna el número de lados del grafo complejidad: O(1)
    override fun obtenerNumeroDeLados() : Int {
        return numDeLados
    }

    // Retorna el número de vértices del grafo complejidad: O(1)
    override fun obtenerNumeroDeVertices() : Int {
        return numDeVertices
    }

    /* 
     Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v. 
     Si el vértice no pertenece al grafo se lanza una RuntimeException
     */
    //override fun adyacentes(v: Int) : Iterable<Arco> {
    //}

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    //fun ladosAdyacentes(l: Arco) : Iterable<Arco> {
    //}
    
    // Retorna todos los lados del digrafo
    //override operator fun iterator() : Iterator<Arco> {
    //}
     
    /* Retorna un string que representa al grafo
       Da cada vertice junto a su lista de adyacencia
       complejidad: O(V*E)
     */
    override fun toString() : String {
        var GrafString: String = ""
        for (vertice in arregloVertices){
            GrafString += "${vertice.listaAdyacenciaToString()}\n"
        }
        return GrafString
    }
}
