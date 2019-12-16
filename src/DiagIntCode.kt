package diag

import java.io.File

// https://adventofcode.com/2019/day/2
fun main(args: Array<String>) {
    var programString = String()
    File("dayfive.txt").forEachLine {
        programString = it
    }
    val program: MutableList<Int> = programString.split(",").map { it.toInt() }.toMutableList()
    runProgram(program)
}

/**
 * Runs an IntCode program.
 *
 * @param program - a mutable list representing the IntCode program.
 */
fun runProgram(program: MutableList<Int>) {
    var runPointer = 0
    while(runPointer != -1) {
        val opCodeString = program[runPointer].toString().padStart(5, '0')
        val opCode = opCodeString.takeLast(2).toInt()
        // Char -> String -> Int
        val paramModes = listOf(
            opCodeString[2].toString().toInt(),
            opCodeString[1].toString().toInt(),
            opCodeString[0].toString().toInt())
        when (opCode) {
            99 -> runPointer = -1
            1 -> {
                val param1 = if(paramModes[0] == 1) program[runPointer + 1] else program[program[runPointer + 1]]
                val param2 = if(paramModes[1] == 1) program[runPointer + 2] else program[program[runPointer + 2]]
                program[program[runPointer + 3]] = param1 + param2
                runPointer += 4
            }
            2 -> {
                val param1 = if(paramModes[0] == 1) program[runPointer + 1] else program[program[runPointer + 1]]
                val param2 = if(paramModes[1] == 1) program[runPointer + 2] else program[program[runPointer + 2]]
                program[program[runPointer + 3]] = param1 * param2
                runPointer += 4
            }
            3 -> {
                print("ID of the system to test: ")
                val inputString = readLine()
                program[program[runPointer + 1]] = inputString!!.toInt()
                runPointer += 2
            }
            4 -> {
                println("Diagnostic code: ${program[program[runPointer + 1]]}")
                runPointer += 2
            }
        }
    }
}