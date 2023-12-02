import java.io.File

fun readFile(path: String) = File(path).bufferedReader().use { it.readText() }
