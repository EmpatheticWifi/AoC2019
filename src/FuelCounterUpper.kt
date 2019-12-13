import java.io.File
import kotlin.math.floor

// https://adventofcode.com/2019/day/1
fun main(args: Array<String>) {
    var totalFuel = 0
    File("dayone.txt").forEachLine {
        val moduleMass = it.toDouble()
        val fuelNeeded = floor(moduleMass / 3.0) - 2
        totalFuel += fuelNeeded.toInt()
    }
    println("Total fuel for all modules: $totalFuel")
}