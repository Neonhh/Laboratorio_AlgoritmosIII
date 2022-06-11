package ve.usb.libGrafo

open class Arco(val inicio: Int, val fin: Int) : Lado(inicio, fin) {

    // Retorna el vértice inicial del arco complejidad: O(1)
    fun fuente() : Int {
        return inicio
    }

    // Retorna el vértice final del arco complejidad: O(1)
    fun sumidero() : Int {
        return fin
    }

    // Representación del arco complejidad: O(1)
    override fun toString() : String {
        return ("(${inicio} --> ${fin})")
    }
} 
