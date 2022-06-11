package ve.usb.libGrafo

import java.io.File

public class GrafoDirigidoCosto : Grafo<Vertice> {

    override var numDeVertices: Int = 0
    var arregloVerticesCosto = ArrayList<VerticeCosto>(numDeVertices)
    var numDeLados: Int = 0
    override var cabeza: Nodo<Vertice>? = null


    /* Agrega un arco al grafo recibe como parametro un ArcoCosto complejidad: O(1)
       retorna true
     */
    /*
    override fun agregar(valor: Vertice): Grafo<Vertice> {
        val nuevo = Nodo(valor)
        if (cabeza == null) cabeza = nuevo
        else{
            nuevo.proximo = cabeza
            cabeza = nuevo
        }
        numDeVertices++
        return this
    }
    */

    fun agregarArcoCosto(a: ArcoCosto) : Boolean {
        var flag: Boolean = false
        val desde: Int = a.fuente()
        val hasta: Int = a.sumidero()
        val cos: Double = a.costo()

        arregloVerticesCosto[desde].aggVerticelistaAdyacencia(hasta, cos)
        numDeLados += 1
        arregloVerticesCosto[desde].aumentarGradoExterior()
        arregloVerticesCosto[hasta].aumentarGradoInterior()


        return flag
    }
    // Se construye un grafo dirigo con costo a partir del número de vértices
    // la funcion recibe un entero que debe ser el numero de vertices del grafo
    constructor(numDeVerticesIn: Int) {
        numDeVertices = numDeVerticesIn
        for (i in 0..numDeVertices-1){
            arregloVerticesCosto.add(VerticeCosto(i))
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
                arregloVerticesCosto = ArrayList<VerticeCosto>(numDeVertices)
                for (i in 0..numDeVertices-1) {
                    arregloVerticesCosto.add(VerticeCosto(i))
                }
                contador += 1
            } else if (contador == 1) {
                //val numDeLados: Int = it.toInt()
                contador+=1
            } else {
                val datosLado = it.split(" ")
                val u = datosLado.elementAt(0).toInt()
                val v = datosLado.elementAt(1).toInt()
                val costo = datosLado.elementAt(2).toDouble()
                agregarArcoCosto(ArcoCosto(u, v, costo))
                contador+=1
            }
        }
    }
    override fun obtenerArregloVerticesCosto(): ArrayList<VerticeCosto> {
        return arregloVerticesCosto
    }

    override fun obtenerArregloVertices(): ArrayList<Vertice> {
        TODO("Not yet implemented")
    }
    // Retorna el grado de un vertice del grafo complejidad: O(1)
    // El grado es la suma del grado interior y el exterior
    // recibe como parametros un int v que debe ser el id del vertice
    override fun grado(v: Int): Int {
        return arregloVerticesCosto[v].gradoInterior + arregloVerticesCosto[v].gradoExterior
    }

    // Retorna el grado exterior de un vertice del grafo complejidad: O(1)
    // recibe como parametros un int v que debe ser el id del vertice
    fun gradoExterior(v: Int): Int {
        return arregloVerticesCosto[v].gradoExterior
    }

    // Retorna el grado interior de un vertice del grafo complejidad: O(1)
    // recibe como parametros un int v que debe ser el id del vertice
    fun gradoInterior(v: Int): Int {
        return arregloVerticesCosto[v].gradoInterior
    }

    // Retorna el número de lados del grafo complejidad: O(1)
    override fun obtenerNumeroDeLados(): Int {
        return numDeLados
    }

    // Retorna el número de vértices del grafo complejidad: O(1)
    override fun obtenerNumeroDeVertices(): Int {
        return numDeVertices
    }

    /* Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v.
       Si el vértice no pertenece al grafo se lanza una RuntimeException*/
    //override fun adyacentes(v: Int) : Iterable<ArcoCosto> {
    //}

    /* Retorna los lados adyacentes de un lado l.
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    //fun ladosAdyacentes(l: ArcoCosto) : Iterable<ArcoCosto> {
    //}

    // Retorna todos los lados del digrafo con costo
    //override operator fun iterator() : Iterator<ArcoCosto> {
    //}


    /* Retorna un string que representa al grafo
       Da cada vertice junto a su lista de adyacencia
       cada vertice esta junto al coste del arco
       complejidad: O(V*E)
     */
    override fun toString() : String {
        var GrafString: String = ""
        for (vertice in arregloVerticesCosto){
            GrafString += "${vertice.listaAdyacenciaCostoToString()}\n"
        }
        return GrafString
    }

    override fun esVacio(): Boolean = (numDeVertices == 0)
}
