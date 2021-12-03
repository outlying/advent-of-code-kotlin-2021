import day03.Solver

fun main() {

    fun List<String>.toCharArray(): Array<CharArray> {
        return this
            .map { it.toCharArray() }
            .toTypedArray()
    }

    fun Array<CharArray>.transpose(): Array<CharArray> {
        require(isNotEmpty()) { "Cannot transpose empty matrix" }

        val row = size
        val column = get(1).size

        val transpose = Array(column) { CharArray(row) }
        for (i in 0 until row) {
            for (j in 0 until column) {
                transpose[j][i] = get(i)[j]
            }
        }

        return transpose
    }

    fun Array<CharArray>.display(): Array<CharArray> {
        println("The matrix is: ")
        for (row in this) {
            for (column in row) {
                print("$column    ")
            }
            println()
        }
        return this
    }

    fun part1(input: List<String>): Int {

        val resutl = input
            .toCharArray()
            .transpose()
            .map {
                val amount = it.size
                val ones = it.count { char -> char == '1' }
                val zeros = amount - ones
                if (ones > zeros) {
                    charArrayOf('1', '0')
                } else {
                    charArrayOf('0', '1')
                }
            }
            .toTypedArray()
            .transpose()
            .map { it.joinToString(separator = "") }

        val gammaRate = resutl[0].toInt(2)
        val epsilonRate = resutl[1].toInt(2)

        return gammaRate * epsilonRate
    }


    fun part2(input: List<String>): Int {
        return Solver(input).solve()
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
