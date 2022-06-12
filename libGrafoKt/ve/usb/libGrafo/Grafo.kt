package ve.usb.libGrafo
import java.util.LinkedList

interface Grafo : Iterable<Lado> {

    var numDeVertices: Int
    var numDeLados: Int

    /*  Retorna el número de lados del grafo
     * Parametros: N/A
     * Precondiciones: N/A
     * Postcondiciones: obtenerNumeroDeLados() = numDeLados
     * Costo: O(1)
     */
    fun obtenerNumeroDeLados() : Int

    /* Retorna el número de vértices del grafo
     * Parametros: N/A
     * Precondiciones: N/A
     * Postcondiciones: obtenerNumeroDeVertices() = numDeVertices
     * Costo: O(1)
    */
    fun obtenerNumeroDeVertices() : Int

    /*
     * Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v.
     *Si el vértice no pertenece al grafo se lanza una RuntimeException.
     * Parametros: v , el vertice cuyas adyacencias se quieren hallar
     * Precondiciones: v representa a un vertice que existe en el grafo
     * Postcondiciones: adyacentes = iterable con los lados adyacentes al vertice v
     * Costo: O(|E|)
     */
    fun adyacentes(v: Int) : Iterable<Lado>

    /* Retorna el grado del grafo
     * Parametros: v: Int
     * Precondiciones: v es vertice del grafo
     * Postcondiciones: grado() == grado maximo de todos los vertices en el grafo
     * Costo: O(|V|) 
     */ 
    fun grado(v: Int) : Int

    /* Retorna un iterador de todos los lados del grafo.
     * Parametros: N/A
     * Postcondiciones: devuelve un iterador que se puede utilizar para navegar los lados del
     * grafo mediante un ciclo.
     * Costo: O(1)
     */
    override operator fun iterator() : Iterator<Lado>
    //abstract fun vertice(any: Any?)

    fun esVacio(): Boolean
    
    fun obtenerArregloVertices() : ArrayList<Any>

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

class Nodo<Lado>(valor: Lado){
    var valor: Lado = valor
    var proximo: Nodo<Lado>? = null
}
