package ve.usb.libGrafo

// Retorna el digrafo inverso de un grafo
// recibe como argumeno un grafo dirigido
//complejidad O(|V|+|E|)
fun digrafoInverso(g: GrafoDirigido) : GrafoDirigido {
    var GrafoInverso = GrafoDirigido(g.numDeVertices)

    for (u in g.obtenerArregloVertices()){

        for (v in u.listaAdyacencia){
            GrafoInverso.agregarArco(Arco(v.first,u.obtenerId()))
        }
    }
    return GrafoInverso
}
