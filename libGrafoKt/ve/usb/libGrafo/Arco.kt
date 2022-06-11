package ve.usb.libGrafo

open class Arco(val inicio: Int, val fin: Int) : Lado(inicio, fin) {

    /* Retorna el id del vertice v
     * Argumentos: N/A
     * Postcondiciones: v = vertice donde empieza el lado
     * Costo: O(1)
    */
    fun fuente() : Int {
        return inicio
    }

    /* Retorna el id del vertice u
     * Argumentos: N/A
     * Postcondiciones: u = vertice a donde apunta el lado
     * Costo: O(1)
    */
    fun sumidero() : Int {
        return fin
    }

    // Representación del arco complejidad: O(1)
    override fun toString() : String {
        return ("(${inicio} --> ${fin})")
    }
} 
