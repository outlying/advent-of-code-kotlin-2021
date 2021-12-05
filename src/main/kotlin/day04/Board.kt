package day04

import transpose

class Board(input: List<String>) {

    private var _lastCalledNumber: Int? = null
    val lastCalledNumber: Int?
        get() = _lastCalledNumber
    private val board: List<List<Int>>
    private val selected = MutableList(5) { MutableList<Int?>(5) { null } }

    init {
        require(input.size == 5) { "Input size other than 5" }

        board = input
            .map { row ->
                row
                    .trim()
                    .split(' ')
                    .filter { it.isNotEmpty() }
                    .map { item -> item.toInt() }
            }
    }

    fun calculateResult(): Int {
        val sumUnmarked = sumUnmarked()
        val lastCalledNumber = lastCalledNumber!!
        return sumUnmarked * lastCalledNumber
    }

    fun sumUnmarked(): Int {
        var sum = 0
        for (row in 0..4) {
            for (col in 0..4) {
                if (selected[row][col] == null) {
                    sum += board[row][col]
                }
            }
        }
        return sum
    }

    fun selected(number: Int) {
        for (row in 0..4) {
            for (col in 0..4) {
                if (board[row][col] == number) {
                    selected[row][col] = number
                }
            }
        }
        _lastCalledNumber = number
    }

    fun isWinning(): Boolean {
        val rowWin = selected
            .map { row -> row.count { i: Int? -> i != null } }
            .map { it == 5 }
            .firstOrNull { it } ?: false

        val colWin = selected.transpose()
            .map { row -> row.count { i: Int? -> i != null } }
            .map { it == 5 }
            .firstOrNull { it } ?: false

        return rowWin || colWin
    }

    override fun toString(): String {
        return "Board:\n\n" + board.joinToString(separator = "\n") { row -> row.joinToString(separator = "\t") } +
                "\n\nSelected:\n\n" + selected.joinToString(separator = "\n") { row ->
            row
                .map { it ?: "-" }
                .joinToString(separator = "\t")
        } + "\n\n"
    }
}