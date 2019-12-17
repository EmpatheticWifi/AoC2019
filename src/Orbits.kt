import java.io.File

// https://adventofcode.com/2019/day/6

val allPlanets = mutableListOf<CelestialObject>()

fun main(args: Array<String>) {
    val orbitMap = mutableListOf<String>()
    File("daysix.txt").forEachLine {
        orbitMap.add(it)
    }
    // Create the orbits
    for (relationship in orbitMap) {
        relationship.split(")").run {
            val planets = mutableListOf<CelestialObject>()
            this.forEach {
                planets.add(createAndAddPlanet(it))
            }
            planets[1].orbitList.add(planets[0])
        }
    }
    // Calculate total orbits
    var totalOrbits = 0
    allPlanets.forEach {
        totalOrbits += it.getOrbits()
    }
    println(totalOrbits)
}

/**
 * Creates a planet and adds it to the global list if none exist by the same name.
 *
 * @param: planetString the name of the planet to add
 * @return a CelestialObject with the name provided
 */
fun createAndAddPlanet(planetString: String): CelestialObject {
    var planet = allPlanets.find { it.name == planetString }
    if (planet == null) {
        planet = CelestialObject(planetString)
        allPlanets.add(planet)
    }
    return planet
}

data class CelestialObject(val name: String) {
    val orbitList = mutableListOf<CelestialObject>()

    /**
     * Gets the total number of orbits from this object to the center of mass.
     *
     * @return the number of direct and indirect orbits
     */
    fun getOrbits(): Int {
        if (name == "COM") {
            return 0
        }
        var totalOrbits = 0
        orbitList.forEach {
            totalOrbits = 1 + it.getOrbits()
        }
        return totalOrbits
    }
}