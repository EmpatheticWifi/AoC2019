import java.io.File
import kotlin.math.abs

// https://adventofcode.com/2019/day/2
fun main(args: Array<String>) {
    val wiresStrings = mutableListOf<String>()
    File("daythree.txt").forEachLine {
        wiresStrings.add(it)
    }
    val wires = mutableListOf<List<String>>()
    wiresStrings.forEach {
        wires.add(it.split(","))
    }
    val coordinates1 = instructionsToCoords(wires[0])
    val coordinates2 = instructionsToCoords(wires[1])
    val expandedCoordinates1 = expandLines(coordinates1)
    val expandedCoordinates2 = expandLines(coordinates2)
    var shortestDistance = 1000000
    for (match in findIntersections(expandedCoordinates1, expandedCoordinates2)) {
        val distance = abs(match.first) + abs(match.second)
        if (distance != 0) {
            shortestDistance = if (distance < shortestDistance) distance else shortestDistance
        }
    }
    println(shortestDistance)
}

fun findIntersections(wire1: List<List<Pair<Int, Int>>>, wire2: List<List<Pair<Int, Int>>>): List<Pair<Int, Int>> {
    val results = mutableListOf<Pair<Int, Int>>()
    for (i in wire1.indices) {
        for (j in wire1[i]) {
            for (k in wire2) {
                if (j in k) {
                    results.add(j)
                }
            }
        }
    }
    return results.toList()
}

fun expandLines(coordinates: List<Pair<Int, Int>>): List<List<Pair<Int, Int>>> {
    val result = mutableListOf<List<Pair<Int, Int>>>()
    for (i in coordinates.indices) {
        if (i != coordinates.size - 1) {
            val start = coordinates[i]
            val end = coordinates[i + 1]
            result.add(drawLine(start, end))
        }
    }
    return result.toList()
}

fun drawLine(start: Pair<Int, Int>, end: Pair<Int, Int>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    // if x is staying the same (going up and down)
    if (start.first == end.first) {
        // if we are going up or down
        if (start.second > end.second) {
            for (i in end.second..start.second) {
                result.add(Pair(start.first, i))
            }
        } else {
            for (i in start.second..end.second) {
                result.add(Pair(start.first, i))
            }
        }
    } else if (start.second == end.second) {
        if (start.first > end.first) {
            for (i in end.first..start.first) {
                result.add(Pair(i, start.second))
            }
        } else {
            for (i in start.first..end.first) {
                result.add(Pair(i, start.second))
            }
        }
    }
    return result.toList()
}


fun instructionsToCoords(instructions: List<String>): List<Pair<Int, Int>> {
    var x = 0
    var y = 0
    val resultList = mutableListOf(Pair(x, y))
    instructions.forEach {
        val (digits, instruction) = it.partition { it.isDigit() }
        when (instruction) {
            "R" -> x += digits.toInt()
            "L" -> x -= digits.toInt()
            "D" -> y -= digits.toInt()
            "U" -> y += digits.toInt()
        }
        resultList.add(Pair(x, y))
    }
    return resultList.toList()
}