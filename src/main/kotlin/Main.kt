import java.lang.NumberFormatException
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    var aventura1 = Aventura()
    aventura1.elegirRuta()
}

class Aventura {

    var listaArticulos = mutableListOf<Articulo>(
        Articulo(4, 45), Articulo(40, 45286457), Articulo(6, 30), Articulo(4, 50), Articulo(5, 10), Articulo(2, 40)
    )
    var listaArticulosM = mutableListOf<Articulo>()
    var listaJugadores = mutableListOf<Persona>()
    fun elegirRuta() {
        println(
            "Elige lo que quieres probar respecto al programa" + "\n1. Ver una prueba de que la conversación funciona sin meter parámetros" + "\n2. Ver una prueba de que el intercambio funciona sin meter parámetros" + "\n3. Ver una prueba de que el intercambio funciona metiendo parámetros"
        )
        var elecc = readln()
        if (elecc == "1") demostracionConversacion()
        else if (elecc == "2") demostracionIntercambios()
        else if (elecc == "3") menuCompleto()
    }

    fun menuCompleto() {
        while (true) {
            println("¿Quieres crear un nuevo personaje\n1. Si   2.No")
            var elecc = readln()
            if (elecc == "1") crearPersonaje(listaJugadores, listaArticulos, listaArticulosM)
            else if (elecc == "2") break
        }
        println("¿Quieres hacer tus propios artículos?" + "\n1. Sí    2. No")
        var elecc = readln()
        if (elecc == "1") {
            listaArticulos.removeAll(listaArticulos)
            while (true) {
                try {
                    println("Si quieres parar de crear objetos escribe 1")
                    var elecc = readln()
                    if (elecc == "1") break
                    println("Dime el peso del objeto")
                    var peso = readln()
                    println("Dime el valor del objeto")
                    var valor = readln()
                    listaArticulos.add(Articulo(peso.toInt(), valor.toInt()))
                } catch (e: NumberFormatException) {
                    println("tiene que ser un numero entero")
                }
            }
        }
        while (true) {
            println(
                "¿Que quieres hacer" + "\n1. Tener una conversación" + "\n2. Vender algo" + "\n3. Acabar el programa"
            )
            var elecc = readln()
            if (elecc == "1") conversar()
            else if (elecc == "2") venderAlgo()
            else if (elecc == "3") break
        }
    }

    fun conversar() {
        println("¿Con que jugador quieres conversar?")
        var eleccJug = readln()
        while (true) {
            try {
                println("Pregunta algo, si quieres parar escribe 1")
                var pregunta = readln()
                if (pregunta == "1") break
                listaJugadores[eleccJug.toInt()].pregunta(pregunta)
            } catch (e: Exception) {
                println("La elección de jugador tiene que ser numerica")
            }
        }
    }

    fun venderAlgo() {
        println("¿Que personaje vende algo?")
        var eleccV = readln()
        println("¿A que mercader?")
        var eleccC = readln()
        println("¿Que nº de objeto de tu inventario?")
        var nObjeto = readln()
        try {
            listaJugadores[eleccC.toInt()].vender(
                listaJugadores[eleccV.toInt()], listaJugadores[eleccC.toInt()] as Mercader, nObjeto.toInt()
            )
            println(listaJugadores[eleccV.toInt()].dinero)
        } catch (e: Exception) {
            println("Tiene que ser de persona a mercader")
        }
    }

    fun demostracionConversacion() {
        var mochilaJ = Mochila(mutableListOf())
        var Jack = Berserker("Jack", Estado_vital.ANCIANO, Raza.ENANO, mochilaJ)
        while (true) {
            println("Pregunta algo, si quieres parar escribe 1")
            var pregunta = readln()
            if (pregunta == "1") break
            Jack.pregunta(pregunta)
        }
    }

    fun demostracionIntercambios() {
        var artsMercaderes = mutableListOf<Articulo>()
        var mochilaJ = Mochila(listaArticulos)
        mochilaJ.lista.sort()
        println("Tamaño de la mochila del aventurero: ${mochilaJ.W}")
        mochilaJ.dentroM()
        var mochilaJM = Mochila(artsMercaderes)
        var vendedor = Mago("Vendedor", Estado_vital.ANCIANO, Raza.ENANO, mochilaJ)
        var comprador = Mercader("Comprador", Estado_vital.ANCIANO, Raza.ENANO, mochilaJM)
        println("Mochila del personaje no mercader: ${vendedor.mochila}")
        println("Mochila del personaje mercader: ${comprador.mochila}")
        //numero de objeto en tu inventario a vender
        comprador.vender(vendedor, comprador, 0)
        println("Monedero del vendedor: ${vendedor.dinero}")
    }

    fun crearPersonaje(
        listaJug: MutableList<Persona>, listaArticulos: MutableList<Articulo>, artsMercaderes: MutableList<Articulo>
    ): MutableList<Persona> {
        var mochilaJ = Mochila(listaArticulos)
        var mochilaJM = Mochila(artsMercaderes)
        mochilaJ.lista.sort()
        mochilaJ.dentroM()
        var eve = Estado_vital.ADULTO
        var ra = Raza.ENANO
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
        else if (clase == "5") listaJug.add(Mercader(nombre, eve, ra, mochilaJM))
        return listaJug
    }
}

open class Persona(
    var nombre: String,
    var edad: Estado_vital,
    var raza: Raza,
    var mochila: Mochila,
    var dinero: HashMap<Int, Int> = hashMapOf(1 to 0, 5 to 0, 10 to 0, 25 to 0, 100 to 0),
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

    open fun vender(persona: Persona, persona1: Mercader, i: Int) {
        println("Debe ser un jugador con un mercader")
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

class Berserker(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) :
    Persona(nombre, edad, raza, mochila) {}

class Guerrero(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) :
    Persona(nombre, edad, raza, mochila) {}

class Ladron(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) : Persona(nombre, edad, raza, mochila) {}

class Mercader(nombre: String, edad: Estado_vital, raza: Raza, mochila: Mochila) :
    Persona(nombre, edad, raza, mochila) {

    override fun vender(vendedor: Persona, comprador: Mercader, nObjeto: Int) {
        if (vendedor.mochila.lista.size < nObjeto) println("No tienes este objeto")
        else {
            var valor = vendedor.mochila.lista[nObjeto].valor
            comprador.mochila.lista.add(vendedor.mochila.lista[nObjeto])
            vendedor.mochila.lista.removeAt(nObjeto)
            var cont = 0
            while (valor > 0) {
                while (valor >= 100) {
                    cont++
                    valor -= 100
                    vendedor.dinero[100] = cont
                }
                cont = 0
                while (valor >= 25) {
                    cont++
                    valor -= 25
                    vendedor.dinero[25] = cont
                }
                cont = 0
                while (valor >= 10) {
                    cont++
                    valor -= 10
                    vendedor.dinero[10] = cont
                }
                cont = 0
                while (valor >= 5) {
                    cont++
                    valor -= 5
                    vendedor.dinero[5] = cont
                }
                cont = 0
                while (valor >= 1) {
                    cont++
                    valor -= 1
                    vendedor.dinero[1] = cont
                }
                println("Dinero sobrante de la venta: ${valor}")
            }

            println("Objeto eliminado con exito")
            println("Mochila del personaje no mercader: ${vendedor.mochila}")
            println("Mochila del personaje mercader: ${comprador.mochila}")
        }
    }
}


class Mochila(var lista: MutableList<Articulo>) {
    var W = Random().nextInt(1, 7) * 10

    fun dentroM(): MutableList<Articulo> {
        val result = mutableListOf<Articulo>()
        var suma = 0
        while (suma < W) {
            var el = lista.removeFirstOrNull()
            if (el != null && suma + el.peso > W && lista.size > 0) {
                lista.add(el)
                el = lista[1]
            }
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