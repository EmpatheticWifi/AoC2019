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
    while (runPointer != -1) {
        runPointer = handleOpCode(runPointer, program)
    }
}

fun handleOpCode(runPointer: Int, program: MutableList<Int>): Int {
    val opCodeString = program[runPointer].toString().padStart(5, '0')
    val opCode = opCodeString.takeLast(2).toInt()
    if (opCode == 99) {
        return -1
    }
    // Char -> String -> Int
    val paramModes = listOf(
        opCodeString[2].toString().toInt(),
        opCodeString[1].toString().toInt(),
        opCodeString[0].toString().toInt()
    )
    val param1 = if (paramModes[0] == 1) program[runPointer + 1] else program[program[runPointer + 1]]
    val param2 = if (paramModes[1] == 1) program[runPointer + 2] else program[program[runPointer + 2]]

    //println("RunPointer $runPointer Running OpCodeString: $opCodeString with Params ${program[runPointer + 1]} ${program[runPointer + 2]} ${program[runPointer + 3]}")
    when (opCode) {
        // Add
        1 -> {
            program[program[runPointer + 3]] = param1 + param2
            return runPointer + 4
        }
        // Multiply
        2 -> {
            program[program[runPointer + 3]] = param1 * param2
            return runPointer + 4
        }
        // Input
        3 -> {
            print("ID of the system to test: ")
            val inputString = readLine()
            program[program[runPointer + 1]] = inputString!!.toInt()
            return runPointer + 2
        }
        // Output
        4 -> {
            println("Diagnostic code: ${program[program[runPointer + 1]]}")
            return runPointer + 2
        }
        // Jump-if-true
        5 -> {
            return if (param1 != 0) param2 else runPointer + 3
        }
        // Jump-if-false
        6 -> {
            return if (param1 == 0) param2 else runPointer + 3
        }
        // Less than
        7 -> {
            if (param1 < param2) {
                program[program[runPointer + 3]] = 1
            } else {
                program[program[runPointer + 3]] = 0
            }
            return runPointer + 4
        }
        // Equals
        8 -> {
            if (param1 == param2) {
                program[program[runPointer + 3]] = 1
            } else {
                program[program[runPointer + 3]] = 0
            }
            return runPointer + 4
        }
        else -> return 0
    }
}