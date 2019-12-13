package reverse

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

// https://adventofcode.com/2019/day/2
fun main(args: Array<String>) = runBlocking {
    var programString = String()
    File("daytwo.txt").forEachLine {
        programString = it
    }
    val program: MutableList<Int> = programString.split(",").map { it.toInt() }.toMutableList()
    val allPrograms = generatePrograms(program.toList(), 0, 99)
    allPrograms.forEach {
        launch {
            val resultList = it.toMutableList()
            runProgram(resultList)
            if (resultList[0] == 19690720) {
                println(100 * resultList[1] + resultList[2])
            }
        }
    }
}

/**
 * Generates IntCode programs replacing the 1st and 2nd with each value from min to max inclusive.
 *
 * eg.
 *   program = [0, 0, 0, 0]
 *   min = 0
 *   max = 3
 *   result = [
 *      [0, 0, 0, 0],
 *      [0, 0, 1, 0],
 *      [0, 0, 2, 0],
 *      [0, 0, 3, 0],
 *      ...
 *      [0, 3, 0, 0],
 *      [0, 3, 1, 0],
 *      [0, 3, 2, 0],
 *      [0, 3, 3, 0],
 *   ]
 *
 * @param program the program to generate from
 * @param min the starting number to generate from
 * @param max the maximum number to generate to
 */
fun generatePrograms(program: List<Int>, min: Int, max: Int): List<List<Int>> {
    val resultList = mutableListOf<List<Int>>()
    var noun = min
    for (i in min..max) {
        var verb = min
        for (j in min..max) {
            val tempProgram = program.toMutableList()
            tempProgram[1] = noun
            tempProgram[2] = verb
            resultList.add(tempProgram.toList())
            verb++
        }
        noun++
    }
    return resultList.toList()
}

/**
 * Runs an IntCode program.
 *
 * @param program - a mutable list representing the IntCode program.
 */
fun runProgram(program: MutableList<Int>) {
    var runPointer = 0
    while (runPointer != -1) {
        when (program[runPointer]) {
            99 -> runPointer = -1
            1 -> {
                program[program[runPointer + 3]] = program[program[runPointer + 1]] + program[program[runPointer + 2]]
                runPointer += 4
            }
            2 -> {
                program[program[runPointer + 3]] = program[program[runPointer + 1]] * program[program[runPointer + 2]]
                runPointer += 4
            }
        }
    }
}