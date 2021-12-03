package day03

class Solver(private val input: List<String>) {

    private val columns = input[0].length

    init {
        require(input.isNotEmpty()) { "Input cannot be empty" }
    }

    fun solve(): Int {

        val oxygenRating = input.subsolve('1') { it.first }
        val scrubberRating = input.subsolve('0') { it.second }

        return oxygenRating.toInt(2) * scrubberRating.toInt(2)
    }

    private fun List<String>.subsolve(
        takeCharIfEquals: Char,
        block: (Pair<List<String>, List<String>>) -> List<String>
    ): String {
        var filtering: List<String>
        var startColumn = 0
        do {
            filtering = this
            filteringLoop@ for (i in startColumn until columns) {
                filtering = block.invoke(filtering.splitByPopularity(i, takeCharIfEquals))
                println(filtering)
                if (filtering.size == 1) {
                    break@filteringLoop
                }
            }
            startColumn++
        } while (filtering.size > 1)

        return filtering.first()
    }

    private fun List<String>.splitByPopularity(column: Int, takeCharIfEquals: Char): Pair<List<String>, List<String>> {
        val countZeros = count { it[column] == '0' }
        val halfSize = size / 2
        val zerosMorePopular = countZeros > halfSize
        val areEquals = countZeros == halfSize

        val groupped = this.groupBy { it[column] }

        val zerosGroup = groupped['0'] ?: emptyList()
        val onesGroup = groupped['1'] ?: emptyList()

        return if (areEquals) {
            onesGroup to zerosGroup
        } else {
            if (zerosMorePopular) {
                zerosGroup to onesGroup
            } else {
                onesGroup to zerosGroup
            }
        }
    }
}