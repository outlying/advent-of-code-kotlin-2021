package day04

import java.util.*

class BingoGame(input: List<String>) {

    private val drawnOrder = LinkedList(input[0].split(',').map { it.toInt() })
    private val boards: List<Board>

    init {
        val collectBoards = mutableListOf<Board>()
        var boardLine = 2
        do {
            val subList = input.subList(boardLine, boardLine + 5)
            collectBoards.add(Board(subList))
            boardLine += 6
        } while (boardLine < input.size)
        boards = collectBoards
    }

    fun findWiningBoard(): Board? {
        while (drawnOrder.size > 0) {
            val number = drawnOrder.pop()
            boards.onEach { it.selected(number) }
            val winingBoard = boards.find { it.isWinning() }

            if(winingBoard != null) {
                return winingBoard
            }
        }

        return null
    }

    fun findLoosingBoard(): Board? {
        val amountOfBoards = boards.size
        var lastWiningBoard: Board? = null
        while (drawnOrder.size > 0) {
            val number = drawnOrder.pop()

            for(board in boards) {
                board.selected(number)

                val winingBoardsCount = boards.count { it.isWinning() }
                if(amountOfBoards == winingBoardsCount + 1 && lastWiningBoard == null) {
                    lastWiningBoard = boards.find { !it.isWinning() }
                }
                if(amountOfBoards == winingBoardsCount) {
                    return lastWiningBoard
                }
            }
        }

        return null
    }
}