package ve.usb.libGrafo

import java.util.ArrayList

/*
    El constructor de esta clase recibe como argumento un grafo dirigido y lo que hace es
    crear arreglo con las particiones por niveles del grafo g que recibe como argumento,
    esto se efectua con una complejidad de O(max(|V|,|E|))

    para obtener el arreglo de las particiones se llama a la funcion obtenerParticiones()
    la cual retorna un arreglo<MutableSet> cuyos elementos son vertices, cada posicion del arreglo
    representa un nivel

    Para saber si el digrafo tiene algun ciclo utilizamos la funcion hayCiclo() la cual retorna true
    si hay ciclo y false si no lo hay
 */
public class ParticionNiveles(val g: GrafoDirigido) {
    var Particiones: ArrayList<MutableSet<Vertice>> = arrayListOf<MutableSet<Vertice>>()
    var nvert: Int =0
    var nivel: Int =0
    var hayCiclo: Boolean = false
    init {

        for (i in 0..g.numDeVertices - 1) {
            Particiones[i] = mutableSetOf<Vertice>()
        }
        nvert = 0
        nivel = 0
        hayCiclo = false

        for (v in g.obtenerArregloVertices()){
            if(v.gradoInterior == 0){
                Particiones[nivel].add(v)
                nvert += 1
            }
        }

        while (nvert < g.numDeVertices && Particiones[nivel].any()){
            for (u in Particiones[nivel]){
                for (v in u.listaAdyacencia){
                    g.obtenerArregloVertices()[v.first].gradoInterior -= 1

                    if(g.obtenerArregloVertices()[v.first].gradoInterior == 0){
                        Particiones[nivel+1].add( g.obtenerArregloVertices()[v.first] )
                        nvert += 1
                    }
                }
            }
            nivel += 1
        }
        hayCiclo = (nvert < g.numDeVertices)
    }
    // Retorna las particiones de niveles de los vértices del grafo de entrada
    // Si el digrafo de entrada no es DAG, entonces se lanza una RuntineException()
    // Complejidad: O(1)
    fun obtenerParticiones() : ArrayList<MutableSet<Vertice>> {
        if ( (g is GrafoDirigido) && !hayCiclo() ) {
            return Particiones
        }
        else{
            throw RuntimeException("El grafo no es DAG")
        }
    }
    
    // Retorna true si el digrafo tiene un ciclo, y false en caso contrario.
    // Complejidad: O(1)
    fun hayCiclo() : Boolean {
        return hayCiclo
    }
}
