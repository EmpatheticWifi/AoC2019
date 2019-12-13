import java.io.File

// https://adventofcode.com/2019/day/2
fun main(args: Array<String>) {
    var programString = String()
    File("daytwo.txt").forEachLine {
        programString = it
    }
    val program: MutableList<Int> = programString.split(",").map { it.toInt() }.toMutableList()
    runProgram(program)
    println(program[0])
}

/**
 * Runs an IntCode program.
 *
 * @param program - a mutable list representing the IntCode program.
 */
fun runProgram(program: MutableList<Int>) {
    var runPointer = 0
    while(runPointer != -1) {
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