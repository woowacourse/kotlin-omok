package domain

import library.*

class RenjuRuleAdapter() : Rule {

    private val fourFourRule = FourFourRule(Board.getSize())
    private val threeThreeRule = ThreeThreeRule(Board.getSize())
    private val blackWinRule = BlackWinRule(Board.getSize())
    private val whiteWinRule = WhiteWinRule(Board.getSize())
    private val moreThanFiveRule = MoreThanFiveRule(Board.getSize())

    override fun getWinner(stones: Stones): Color? {
        val board = generateCustomBoard(stones)
        val lastPlacedStone = stones.getLastStone() ?: return null
        if (isBlackWin(board, lastPlacedStone)) return Color.BLACK
        if (isWhiteWin(board, lastPlacedStone)) return Color.WHITE
        return null
    }

    private fun isBlackWin(board: List<List<Int>>, lastPlacedStone: Stone): Boolean {
        if (lastPlacedStone.color != Color.BLACK) return false
        return blackWinRule.validate(
            board,
            Pair(lastPlacedStone.position.x, lastPlacedStone.position.y)
        )
    }

    private fun isWhiteWin(board: List<List<Int>>, lastPlacedStone: Stone): Boolean {
        if (lastPlacedStone.color == Color.WHITE) return whiteWinRule.validate(
            board,
            Pair(lastPlacedStone.position.x, lastPlacedStone.position.y)
        )
        return isBlackLose(board, lastPlacedStone)
    }

    private fun isBlackLose(board: List<List<Int>>, lastPlacedStone: Stone): Boolean {
        return isFourFour(board, lastPlacedStone) or isThreeThree(
            board,
            lastPlacedStone
        ) or isMoreThanFive(board, lastPlacedStone)
    }

    private fun isFourFour(board: List<List<Int>>, stone: Stone): Boolean {
        return fourFourRule.validate(
            board,
            Pair(stone.position.x, stone.position.y)
        )
    }

    private fun isThreeThree(board: List<List<Int>>, stone: Stone): Boolean {
        return threeThreeRule.validate(
            board,
            Pair(stone.position.x, stone.position.y)
        )
    }

    private fun isMoreThanFive(board: List<List<Int>>, stone: Stone): Boolean {
        return moreThanFiveRule.validate(
            board,
            Pair(stone.position.x, stone.position.y)
        )
    }

    private fun generateCustomBoard(stones: Stones): List<List<Int>> {
        val libraryBoard = List(Board.getSize()) {
            MutableList(Board.getSize()) { 0 }
        }
        stones.values.forEach {
            when (it.color) {
                Color.BLACK -> libraryBoard[it.position.y][it.position.x] = 1
                Color.WHITE -> libraryBoard[it.position.y][it.position.x] = 2
            }
        }
        return libraryBoard
    }
}
