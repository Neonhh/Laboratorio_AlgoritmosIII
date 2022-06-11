import ve.usb.libGrafo.*

fun main(args: Array<String>) {
    var grafo: Grafo<Vertice> = GrafoDirigido(0)
    val tipoGrafo: Char = args[0].last()

    when (tipoGrafo){
        'd' -> grafo = GrafoDirigido(args[1])
        'n' -> grafo = GrafoNoDirigido(args[1])
        'c' -> grafo = GrafoDirigidoCosto(args[1])
        'p' -> grafo = GrafoNoDirigidoCosto(args[1])
        else -> println("Tipo de grafo no valido")

    }
    println("El grafo tiene ${grafo.obtenerNumeroDeVertices()} vertices y consta de ${grafo.obtenerNumeroDeLados()} lados.")
    println("Las listas de adyacencia de cada vertice son:")
    println(grafo.toString())
    println("El grado del vertice 0 es ${grafo.grado(0)}")


}