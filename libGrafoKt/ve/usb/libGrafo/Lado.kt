package ve.usb.libGrafo
import kotlin.random.Random
abstract class Lado(val a: Int, val b: Int) {

    /* Retorna cualquiera de los dos vértices del grafo
     * Parametros: N/A
     * Postcondiciones: cualquieraDeLosVertices() == a o cualquieraDeLosVertices() == b
     * Costo: O(1)
    */
    fun cualquieraDeLosVertices() : Int {
        val r : Int = (0..10).random()
        if (r%2 == 0){
            return a
        }
        else{
            return b
        }
    }

    /* Dado un vertice w, si w == a entonces retorna b, de lo contrario si w == b  entonces retorna a, retorna -1 si w no es ni a ni b
     *recibe un entero w que representa el id de uno de los dos vertices que forma el lado
     * Parametros: w : Int
     * Precondiciones: w es uno de los vertices del grafo
     * Postcondiciones: (elOtroVertice() == a y w == b)
     *o (elOtroVertice() == b y w == a) o elOtroVertice() == -1
     * Costo: O(1)
     */
    fun elOtroVertice(w: Int) : Int {
        if (w==a){
            return b
        }
        else if (w==b){
            return a
        }
        else{
            return -1
        }
    }
}
