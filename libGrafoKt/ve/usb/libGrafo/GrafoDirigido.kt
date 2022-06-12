package ve.usb.libGrafo

import java.io.File

public class GrafoDirigido : Grafo {
    
    override var numDeLados: Int =0
    var cabeza: Nodo<Arco>? = null
    override var numDeVertices : Int = 0
    
    var arregloVertices = ArrayList<Vertice>(numDeVertices)
    

    override fun esVacio(): Boolean = (numDeLados == 0)

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

    override fun obtenerNumeroDeLados(): Int  = numDeLados

    override fun obtenerNumeroDeVertices() : Int = numDeVertices



    override fun grado(v: Int) : Int {
        var mayorGrado :Int = 0        
        for (vertice in arregloVertices){
            val gradoVertice: Int = vertice.gradoExterior + vertice.gradoInterior
            mayorGrado = maxOf(mayorGrado,gradoVertice)
        }
        return mayorGrado
    }

    inner class iteradorDigrafo(I: GrafoDirigido): Iterator<Arco> {
        var actual = I.cabeza

        override fun hasNext(): Boolean = (actual != null)
        override fun next(): Arco {
            if(actual == null) throw NoSuchElementException("No quedan elementos que iterar")
            val valor :Arco = actual!!.valor
            actual = actual?.proximo
            return valor
        }
    }

    override operator fun iterator() : Iterator<Arco> = iteradorDigrafo(this)
    /* Agrega un arco al grafo recibe como parametro un Arco complejidad: O(1)
       retorna true
     */
    fun agregarArco(a: Arco) : Boolean {

        val desde: Int = a.fuente()
        val hasta: Int = a.sumidero()
        val arcoNodo: Nodo<Arco> = Nodo(a)

        if (cabeza == null) {
            cabeza = arcoNodo
        } else {
            arcoNodo.proximo = cabeza
            cabeza = arcoNodo
        }


        arregloVertices[desde].aggVerticelistaAdyacencia(hasta)
        numDeLados += 1
        arregloVertices[desde].aumentarGradoExterior()
        arregloVertices[hasta].aumentarGradoInterior()

        return true
    }
    override fun adyacentes(v: Int) : Iterable<Arco>{
        if (v > numDeVertices) throw RuntimeException("El vertice ${v} no existe.")
        
        var adyacentes: MutableList<Arco> = mutableListOf()
        val iteradorArcos = iterator()
        while (iteradorArcos.hasNext()){
            val valor: Arco = iteradorArcos.next()
            if (valor.fuente() == v) adyacentes.add(valor)
        }
        return adyacentes
    }
    override fun obtenerArregloVertices(): ArrayList<Vertice> {
        return arregloVertices
    }

    // Retorna el grado de un vertice del grafo complejidad: O(1)
    // El grado es la suma del grado interior y el exterior
    // recibe como parametros un int v que debe ser el id del vertice
/* 
    override fun grado(v: Int) : Int {

        return arregloVertices[v].gradoInterior+arregloVertices[v].gradoExterior
    }
*/
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

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    //fun ladosAdyacentes(l: Arco) : Iterable<Arco> {
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
