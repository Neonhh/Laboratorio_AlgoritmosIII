package ve.usb.libGrafo
import java.io.File

public class GrafoNoDirigidoCosto: Grafo<Vertice> {

    override var numDeVertices : Int = 0
    var arregloVerticesCosto = ArrayList<VerticeCosto>(numDeVertices)
    var numDeAristas : Int = 0
    override var cabeza: Nodo<Vertice>? = null

    override fun esVacio(): Boolean = (numDeVertices == 0)


    // Se construye un grafo no dirigido con costo a partir del número de vértices complejidad: O(V)
    // recibe como argumentos un entero que debe ser el numero de vertices
    constructor(numDeVerticesIn: Int) {
        numDeVertices = numDeVerticesIn
        for (i in 0..numDeVertices-1){
            arregloVerticesCosto.add(VerticeCosto(i))
        }
    }

    /*
     Se construye un grafo no dirigido con costo a partir de un archivo, la funcion tiene como argumento un string el cual debe ser el nombre de un archivo .txt
     complejidad: O(V)
     */
    constructor(nombreArchivo: String) {
        val archivo = File(nombreArchivo)
        var contador: Int = 0


        archivo.forEachLine {
            if (contador == 0) {
                numDeVertices = it.toInt()
                for (i in 0..numDeVertices-1) {
                    arregloVerticesCosto.add(VerticeCosto(i))
                }
                contador += 1
            } else if (contador == 1) {
                val numDeLados: Int = it.toInt()
                contador+=1
            } else {
                val datosLado = it.split(" ")
                val u = datosLado.elementAt(0).toInt()
                val v = datosLado.elementAt(1).toInt()
                val costo = datosLado.elementAt(2).toDouble()
                agregarAristaCosto(AristaCosto(u, v, costo))
                contador+=1
            }
        }
    }

    // Agrega una arista al grafo recibe como argumento una Arista complejidad: O(1)
    // retorna true
    fun agregarAristaCosto(a: AristaCosto) : Boolean {

        val ver1Id: Int = a.primero()
        val ver2Id: Int = a.segundo()
        val cos: Double = a.costo()

        arregloVerticesCosto[ver1Id].aggVerticelistaAdyacencia(ver2Id,cos)
        arregloVerticesCosto[ver1Id].aumentarGradoInterior()
        arregloVerticesCosto[ver1Id].aumentarGradoExterior()

        arregloVerticesCosto[ver2Id].aggVerticelistaAdyacencia(ver1Id,cos)
        arregloVerticesCosto[ver2Id].aumentarGradoInterior()
        arregloVerticesCosto[ver2Id].aumentarGradoExterior()
        numDeAristas += 1

        return true
    }
    // Retorna el número de lados del grafo complejidad: O(1)
    override fun obtenerNumeroDeLados() : Int {
        return numDeAristas
    }

    // Retorna el número de vértices del grafo complejidad: O(1)
    override fun obtenerNumeroDeVertices() : Int {
        return numDeVertices
    }
    override fun obtenerArregloVerticesCosto(): ArrayList<VerticeCosto> {
        return arregloVerticesCosto
    }

    override fun obtenerArregloVertices(): ArrayList<Vertice> {
        TODO("Not yet implemented")
    }
    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    //override fun adyacentes(v: Int) : Iterable<AristaCosto> {
    //}

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    //fun ladosAdyacentes(l: AristaCosto) : Iterable<AristaCosto> {
    //}

    // Retorna todos los lados del grafo no dirigido
     //override operator fun iterator() : Iterator<AristaCosto> {
     //}

    // Retorna el grado de un vertice del grafo complejidad: O(1)
    // El grado es la suma del grado interior y el exterior
    // recibe como parametros un int v que debe ser el id del vertice
    override fun grado(v: Int) : Int {
        return arregloVerticesCosto[v].gradoInterior+arregloVerticesCosto[v].gradoExterior
    }

    /* Retorna un string que representa al grafo
       Da cada vertice junto a su lista de adyacencia
       cada vertice esta junto al coste de la arista
       complejidad: O(V*E)
     */
    override fun toString() : String {
        var GrafString: String = ""
        for (vertice in arregloVerticesCosto){
            GrafString += "${vertice.listaAdyacenciaCostoToString()}\n"
        }
        return GrafString
    }
}


