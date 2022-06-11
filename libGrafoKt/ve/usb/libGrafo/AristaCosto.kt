package ve.usb.libGrafo

public class AristaCosto(val x: Int,
			 val y: Int,
			 val costo: Double) : Comparable<AristaCosto>, Arista(x, y) {

    
    // Retorna el costo del arco complejidad: O(1)
    fun costo() : Double {
         return costo
    }

    // Representación en string de la arista complejidad: O(1)
    override fun toString() : String {
         return ("(${v},${u}), Costo: ${costo}")
    }

    /* 
     Se compara dos arista con respecto a su costo.  complejidad: O(1)
     recibe como parametro uns "segunda" arista
     si el costo de la primera es mayor retorna 1
     si el costo de la segunda es mayor retorna -1
     si el costo es igual en ambas retorna 0
     */
     override fun compareTo(other: AristaCosto): Int {
        if (costo < other.costo()) {
            return -1
        }
        else if (costo > other.costo()){
            return 1
        }
        else{
            return 0
        }
    }
} 
