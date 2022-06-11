package ve.usb.libGrafo

public open class Arista(val v: Int, val u: Int) : Lado(v, u) {

    /* Retorna el id del vertice v
     * Argumentos: N/A
     * Postcondiciones: v = primer vertice del lado
     * Costo: O(1)
    */
    fun primero() : Int {
        return v
    }
    /* Retorna el id del vertice u
     * Argumentos: N/A
     * Postcondiciones: u = segundo vertice del lado
     * Costo: O(1)
    */
    fun segundo() : Int {
        return u
    }
    /* Representación en string de la arista
     * Costo: O(1)
     */
    override fun toString() : String {
        return "(${v} --- ${u})"
    }


} 
