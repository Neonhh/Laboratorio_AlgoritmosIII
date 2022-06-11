package ve.usb.libGrafo
import java.util.LinkedList

interface Grafo<Vertice> : Iterable<Vertice> {

    var cabeza: Nodo<Vertice>?
    var numDeVertices: Int
    // Retorna el número de lados del grafo
    fun obtenerNumeroDeLados() : Int

    // Retorna el número de vértices del grafo
    fun obtenerNumeroDeVertices() : Int

    fun obtenerArregloVertices(): ArrayList<Vertice>

    fun obtenerArregloVerticesCosto(): ArrayList<VerticeCosto>
    /*
     Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v.
     Si el vértice no pertenece al grafo se lanza una RuntimeException.
     */
    //fun adyacentes(v: Int) : Iterable<Lado>

    // Retorna el grado del grafo
    fun grado(v: Int) : Int

    /* Retorna un iterador de los lados del grafo.
       El iterador debe ser creado en clase que implementa esta interface.
     */

    //abstract fun vertice(any: Any?)

    fun esVacio(): Boolean
    //fun agregar(valor: Vertice): Grafo<Vertice>

    class iteraGrafo<Vertice>(I: Grafo<Vertice>): Iterator<Vertice> {
        var actual = I.cabeza
        override fun hasNext(): Boolean = (actual != null)
        override fun next(): Vertice {
            if(actual == null) throw NoSuchElementException("No quedan elementos que iterar")
            val valor = actual!!.valor
            actual = actual?.proximo
            return valor
        }
    }
    override operator fun iterator() : Iterator<Vertice> =
        iteraGrafo(this)

}
//Clase vertice donde se esta el id del vertice y su lista de adyacencia
open class Vertice(val id: Int) : LinkedList<Int>(){
    var gradoExterior:Int =0
    var gradoInterior:Int =0
    var listaAdyacencia = LinkedList<Int>()
    var color: Color = Color.BLANCO
    var pred : Int = -1
    var tiempoInicial: Int = 0
    var tiempoFinal: Int =0
    var distancia: Int =-1
    init {
        listaAdyacencia.addFirst(id)
    }
    // Retorna el id del vertice
    fun obtenerId() : Int{
        return id
    }
    //Retorna la lista de adyacencia del vertice complejidad: O(E)
    fun obtenerAdyacencias() : LinkedList<Int> {
        return listaAdyacencia
    }
    //agrega un vertice a la lista de adyacencia complejidad: O(1)
    //recibe el int id del vertice que se quiere agregar a la lista
    //retorna true
    fun aggVerticelistaAdyacencia(vId: Int) : Boolean{
        listaAdyacencia.addLast(vId)

        return true
    }
    // Aumenta el grado exterior del vertice complejidad: O(1)
    //retorna true
    fun aumentarGradoExterior(): Boolean{
        gradoExterior += 1
        return true
    }
    //aumenta el grado interior del vertice complejidad: O(1)
    //retorna true
    fun aumentarGradoInterior(): Boolean{
        gradoInterior += 1
        return true
    }
    // Retorna un string con todos los vertices adyacentes complejidad: O(E)
    fun listaAdyacenciaToString() :String{

        var lados :String = "Vertice ${id}:  "

        for(lado in listaAdyacencia.subList(1,listaAdyacencia.size)) {
            lados += "${lado} "
        }
        return lados
    }
}
// Clase vertice usada para los grafos con costo
public class VerticeCosto(val costoId: Int) : Vertice(costoId) {

    var listaAdyacenciaCosto = LinkedList<Pair<Int,Double>>()
    init {
        listaAdyacenciaCosto.addFirst(Pair(costoId,0.0))
    }
    // agrega un vertice a la lista de adyacencia complejidad: O(1)
    // recibe como parametros el int id del vertice que se quiere agregar a la lista y un Double que es el costo
    fun aggVerticelistaAdyacencia(vId: Int, costo: Double) : Boolean{
        listaAdyacenciaCosto.addLast(Pair(vId,costo));
        return true
    }
    fun obtenerAdyacenciasCosto() : LinkedList<Pair<Int,Double>>{
        return listaAdyacenciaCosto
    }
    // retorna un string con todos los vertices adyacentes complejidad: O(E)
    fun listaAdyacenciaCostoToString() :String{

        var lados :String = "Vertice ${costoId}: "

        for(lado in listaAdyacenciaCosto.subList(1,listaAdyacenciaCosto.size)) {
            lados += "${lado.first} (${lado.second}) "
        }
        return lados
    }
}

//clase Nodo usada para iterar sobre la lista de vertices
class Nodo<Vertice>(valor: Vertice){
    var valor: Vertice = valor
    var proximo: Nodo<Vertice>? = null
}

