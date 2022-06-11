package ve.usb.libGrafo

public open class Arista(val v: Int, val u: Int) : Lado(v, u) {

    // Retorna el id del vertice v complejidad: O(1)
    fun primero() : Int {
        return v
    }
    // Retorna el id vértice u complejidad: O(1)
    fun segundo() : Int {
        return u
    }
    // Representación en string de la arista
    override fun toString() : String {
        return "(${v} --- ${u})"
    }


} 
