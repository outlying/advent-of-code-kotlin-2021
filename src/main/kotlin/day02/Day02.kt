import day02.AdvancedSubmarine
import day02.ISubmarine
import day02.SimpleSubmarine

fun main() {

    fun control(submarine: ISubmarine, input: List<String>) {
        input
            .map { it.split(' ') }
            .map { it[0] to it[1].toInt() }
            .onEach { (direction, amount) ->
                when(direction) {
                    "forward" -> submarine.forward(amount)
                    "down" -> submarine.down(amount)
                    "up" -> submarine.up(amount)
                }
            }
    }

    fun part1(input: List<String>): Int {
        val submarine = SimpleSubmarine()
        control(submarine, input)
        return submarine.distance()
    }

    fun part2(input: List<String>): Int {
        val submarine = AdvancedSubmarine()
        control(submarine, input)
        return submarine.distance()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
