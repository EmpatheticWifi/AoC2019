import java.io.File
import kotlin.math.floor

// https://adventofcode.com/2019/day/1#part2
fun main(args: Array<String>) {
    var totalFuel = 0
    File("dayone.txt").forEachLine {
        totalFuel += calculateFuel(it.toDouble())
    }
    println("Total fuel for all modules: $totalFuel")
}

/**
 * Calculates the fuel required for a module, and the fuel for that fuel, and so on.
 *
 * "Fuel itself requires fuel just like a module - take its mass, divide by three, round down, and subtract 2. However,
 * that fuel also requires fuel, and that fuel requires fuel, and so on. Any mass that would require negative fuel
 * should instead be treated as if it requires zero fuel;"
 *
 * @param mass - the mass for which to calculate the fuel requirement
 * @return The amount of fuel required for that mass
 */
fun calculateFuel(mass: Double): Int {
    val fuel = (floor(mass / 3.0) - 2).toInt()
    return if (fuel <= 0) {
        0
    } else {
        fuel + calculateFuel(fuel.toDouble())
    }
}