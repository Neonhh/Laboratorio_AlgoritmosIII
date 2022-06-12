package ve.usb.libGrafo

import java.io.File

public class GrafoNoDirigido: Grafo {

    override var numDeLados: Int =0
    override var numDeVertices : Int = 0
    var cabeza: Nodo<Arista>? = null
    var arregloVertices = ArrayList<Vertice>(numDeVertices)

    override fun esVacio(): Boolean = (numDeVertices == 0)

    // Se construye un grafo no dirigido a partir del número de vértices complejidad: O(V)
    // recibe como argumentos un entero que debe ser el numero de vertices
    constructor(numDeVerticesIn: Int) {
        numDeVertices =numDeVerticesIn
        for (i in 0..numDeVertices-1){
            arregloVertices.add(Vertice(i))
        }
    }


    /*
     Se construye un grafo no dirigido a partir de un archivo, la funcion tiene como argumento un string el cual debe ser el nombre de un archivo .txt
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
                contador+=1
            } else {
                val datosLado = it.split(" ")
                val u = datosLado.elementAt(0).toInt()
                val v = datosLado.elementAt(1).toInt()

                agregarArista(Arista(u, v))
                contador+=1
            }
        }
    }

    // Agrega una arista al grafo recibe como argumento una Arista complejidad: O(1)
    // retorna true
    fun agregarArista(a: Arista) : Boolean {
        val ver1Id : Int = a.primero()
        val ver2Id : Int = a.segundo()


        arregloVertices[ver1Id].aggVerticelistaAdyacencia(ver2Id)
        arregloVertices[ver1Id].aumentarGradoInterior()
        arregloVertices[ver1Id].aumentarGradoExterior()

        arregloVertices[ver2Id].aggVerticelistaAdyacencia(ver1Id)
        arregloVertices[ver2Id].aumentarGradoInterior()
        arregloVertices[ver2Id].aumentarGradoExterior()
        numDeLados += 1

        return true
    }

    override fun obtenerArregloVertices(): ArrayList<Vertice> {
        return arregloVertices
    }


    // Retorna el número de lados del grafo complejidad: O(1)
    override fun obtenerNumeroDeLados() : Int {
        return numDeLados
    }

    // Retorna el número de vértices del grafo complejidad: O(1)
    override fun obtenerNumeroDeVertices() : Int {
        return numDeVertices
    }

    override fun adyacentes(v: Int) : Iterable<Arista>{
        if (v > numDeVertices) throw RuntimeException("El vertice ${v} no existe.")
        var adyacentes: MutableList<Arista> = mutableListOf()
        val iteradorAristas = iterator()
        while (iteradorAristas.hasNext()){
            val valor: Arista = iteradorAristas.next()
            if (valor.primero() == v) adyacentes.add(valor)
        }
        return adyacentes
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    //fun ladosAdyacentes(l: Arista) : Iterable<Arista> {
    //}
    
    // Retorna todos los lados del grafo no dirigido
    //override operator fun iterator() : Iterator<Arista> {
    //}

    // Retorna el grado de un vertice del grafo complejidad: O(1)
    // El grado es la suma del grado interior y el exterior
    // recibe como parametros un int v que debe ser el id del vertice
    override fun grado(v: Int) : Int {
        return arregloVertices[v].gradoInterior+arregloVertices[v].gradoExterior
    }

     inner class iteradorGrafo(I: GrafoNoDirigido): Iterator<Arista> {
        var actual = I.cabeza

        override fun hasNext(): Boolean = (actual != null)
        override fun next(): Arista {
            if(actual == null) throw NoSuchElementException("No quedan elementos que iterar")
            val valor = actual!!.valor
            actual = actual?.proximo
            return valor
        }
    }

    override operator fun iterator() : Iterator<Arista> = iteradorGrafo(this)

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

