// https://adventofcode.com/2019/day/4
fun main(args: Array<String>) {
    var counter = 0
    for (password in 136760..595730) {
        if (validPassword(password.toString())) {
            counter++
        }
    }
    println(counter)
}

/**
 * Tells whether a password meets all criteria
 *
 * @param password the password to validate
 * @return true if the password is valid, false if not
 */
fun validPassword(password: String): Boolean {
    if (password.length != 6) {
        return false
    } else if (leftToRightDecreases(password)) {
        return false
    } else if (!hasDouble(password)) {
        return false
    }
    return true
}

/**
 * Validates if a String has a repeated character, but only if it repeats 2 times.
 *
 * eg. 111122 is valid
 *     111222 is not valid
 *
 *  @param password the password to validate
 *  @return true if there is a repeated character, false if not
 */
fun hasDouble(password: String): Boolean {
    val groups = getGroups(password)
    return groups.containsValue(2)
}

/**
 * Gets how many times each character in a string repeats.
 *
 * @param password the password to generate groups from
 * @return a map of each character and how many times it repeats in a row
 */
fun getGroups(password: String): Map<Char, Int> {
    val result = mutableMapOf<Char, Int>()
    for (i in password.indices) {
        if (!result.containsKey(password[i])) {
            var groupSize = 1
            for (j in i + 1 until password.length) {
                if (password[i] == password[j]) {
                    groupSize++
                } else {
                    break
                }
            }
            result[password[i]] = groupSize
        }

    }
    return result
}

/**
 * Validates whether a string of digits decreases from left to right.
 *
 * eg. 123456 = false
 *     123454 = true
 *
 * @param password the password to validate
 * @return true if the password has decreasing digits, false otherwise
 */
fun leftToRightDecreases(password: String): Boolean {
    var decreases = false
    for (i in 0..password.length - 2) {
        if (password[i + 1] < password[i]) {
            decreases = true
        }
    }
    return decreases
}