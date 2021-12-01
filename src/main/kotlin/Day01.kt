fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(2, 1, false)
            .map { (first, second) ->
                if(second > first) 1 else 0
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(3, 1, false) {
                it.sum()
            }
            .windowed(2, 1, false)
            .map { (first, second) ->
                if (second > first) 1 else 0
            }
            .sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
