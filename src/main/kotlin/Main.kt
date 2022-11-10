import java.util.*

fun main(args: Array<String>) {
    var listaArt = mutableListOf(Articulo(4, 40), Articulo(6, 30), Articulo(4, 50), Articulo(5, 10), Articulo(2, 40))
    var mochilaJ = Mochila(listaArt)
    mochilaJ.dentroM()
    var mochilaJa = Mochila(listaArt)
    mochilaJa.dentroM()
    /*
        var listaJugadores = mutableListOf<Persona>()
        crearPersonaje(listaJugadores, listaArt)
    */
    var vendedor = Mago("Vendedor", Estado_vital.ANCIANO, Raza.ENANO, mochilaJ)
    var comprador = Mercader("Comprador", Estado_vital.ANCIANO, Raza.ENANO, mochilaJa)
    comprador.vender(vendedor, comprador)
    println(vendedor.mochila)
    println(comprador.mochila)
    /*
    while (true) {
        println("Pregunta algo, si quieres parar escribe 1")
        var pregunta = readln()
        if (pregunta == "1") break
        listaJugadores[0].pregunta(pregunta)
    }*/
}

fun crearPersonaje(listaJug: MutableList<Persona>, listaArt: MutableList<Articulo>): MutableList<Persona> {
    var mochilaJ = Mochila(listaArt)
    mochilaJ.dentroM()
    var eve = Estado_vital.ADULTO
    var ra = Raza.ENANO
    var mochila: Mochila
    println("¿Como te llamas?")
    var nombre = readln()
    println("¿En que estado vital te encuentras?\n1.Joven   2. Adulto   3.Anciano")
    var ev = readln()
    if (ev == "1") eve = Estado_vital.JOVEN
    else if (ev == "2") eve = Estado_vital.ADULTO
    else if (ev == "3") eve = Estado_vital.ANCIANO
    println("¿A que raza perteneces?\n1.Enano   2.Elfo  3.Humano    4.Goblin")
    var raza = readln()
    if (raza == "1") ra = Raza.ENANO
    else if (raza == "2") ra = Raza.ELFO
    else if (raza == "3") ra = Raza.HUMANO
    else if (raza == "4") ra = Raza.GOBLIN
    println("¿Que clase eres?\n1.Mago   2.Guerrero  3.Berserker 4.Ladron 5.Mercader")
    var clase = readln()
    if (clase == "1") listaJug.add(Mago(nombre, eve, ra, mochilaJ))
    else if (clase == "2") listaJug.add(Guerrero(nombre, eve, ra, mochilaJ))
    else if (clase == "3") listaJug.add(Berserker(nombre, eve, ra, mochilaJ))
    else if (clase == "4") listaJug.add(Ladron(nombre, eve, ra, mochilaJ))
    else if (clase == "5") listaJug.add(Mercader(nombre, eve, ra, mochilaJ))
    return listaJug
}

open class Persona(
    var nombre: String,
    var edad: Estado_vital,
    var raza: Raza,
    var mochila: Mochila,
    var dinero: Int = 0,
    var partidas_jugadas: Int = 0,
    var horas: Int = 0,
    var kills: Int = 0,
    var deaths: Int = 0,
    var assists: Int = 0,
    var kd: Float = kills.toFloat() / deaths.toFloat()
) {
    open fun idioma(respuesta: String) {
        when (this.raza) {
            Raza.GOBLIN -> println(respuesta.rot13cifrado(13))
            Raza.ELFO -> println(respuesta.rot13cifrado(13))
            Raza.ENANO -> println(respuesta.uppercase(Locale.getDefault()))
            Raza.HUMANO -> println(respuesta)
        }
    }

    fun pregunta(pregunta: String) {
        var respuesta = ""
        respuesta = respuestasGen(pregunta)
        idioma(respuesta)
    }

    fun respuestasGen(pregunta: String): String {
        if (pregunta == "¿Como estás?") {
            return when (this.edad) {
                Estado_vital.ANCIANO -> "No me puedo mover"
                Estado_vital.ADULTO -> "En la flor de la vida, pero me empieza a doler la espalda"
                Estado_vital.JOVEN -> "De lujo"
            }
        } else if (pregunta == pregunta.uppercase() && pregunta.contains("¿") && pregunta.contains("?")) {
            return when (this.edad) {
                Estado_vital.ANCIANO -> "Que no te escucho!"
                Estado_vital.ADULTO -> "Estoy buscando la mejor solución"
                Estado_vital.JOVEN -> "Tranqui se lo que hago"
            }
        } else if (pregunta == pregunta.uppercase()) {
            return when (this.edad) {
                Estado_vital.ANCIANO -> "Háblame más alto que no te escucho"
                Estado_vital.ADULTO -> "No me levantes la voz mequetrefe"
                Estado_vital.JOVEN -> "Eh relájate"
            }
        } else if (pregunta == nombre) {
            return when (this.edad) {
                Estado_vital.ANCIANO -> "Las 5 de la tarde"
                Estado_vital.ADULTO -> "¿Necesitas algo?"
                Estado_vital.JOVEN -> "¿Qué pasa?"
            }
        } else return when (this.edad) {
            Estado_vital.ANCIANO -> "En mis tiempos esto no pasaba"
            Estado_vital.ADULTO -> "No sé de qué me estás hablando"
            Estado_vital.JOVEN -> "Yo que se"
        }
    }
}

fun String.rot13cifrado(tamañoRot: Int) = map {
    var tRot = tamañoRot
    while (tamañoRot > 26) {
        tRot = tamañoRot - 26
    }
    if ((it.code in 65..90) || (it.code in 97..122)) {
        val x = it + tRot
        when {
            it.isUpperCase() -> if (x > 'Z') x - 26 else x
            it.isLowerCase() -> if (x > 'z') x - 26 else x
            else -> it
        }
    } else it
}.toCharArray().joinToString("")

class Mago(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) : Persona(nombre, edad, raza, mochila) {}

class Berserker(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) : Persona(nombre, edad, raza, mochila) {}

class Guerrero(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) : Persona(nombre, edad, raza, mochila) {}

class Ladron(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) : Persona(nombre, edad, raza, mochila) {}

class Mercader(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) : Persona(nombre, edad, raza, mochila) {
    fun vender(vendedor: Persona, comprador: Mercader){
        println(vendedor.mochila.lista[1])
    }
}


class Mochila(var lista: MutableList<Articulo>) {
    var W = 10
    fun dentroM(): MutableList<Articulo>{
        lista.sort()
        val result = mutableListOf<Articulo>()
        var suma = 0
        while (suma < W) {
            val el = lista.removeFirstOrNull()
            if (el == null || suma + el.peso > W) break
            result.add(el)
            suma += el.peso
        }
        var qlo = lista
        lista = result
        return qlo
    }

    override fun toString(): String {
        return "Mochila(lista=$lista)"
    }
}

class Articulo(var peso: Int, var valor: Int) : Comparable<Articulo> {
    override fun compareTo(other: Articulo): Int {
        return if (peso.toDouble() / valor.toDouble() > other.peso.toDouble() / other.valor) 1
        else if (peso.toDouble() / valor.toDouble() < other.peso.toDouble() / other.valor) -1
        else 0
    }

    override fun toString(): String {
        return "Articulo(peso=$peso, valor=$valor)"
    }
}
enum class Estado_vital {
    ANCIANO, ADULTO, JOVEN
}

enum class Raza {
    ELFO, ENANO, HUMANO, GOBLIN
}