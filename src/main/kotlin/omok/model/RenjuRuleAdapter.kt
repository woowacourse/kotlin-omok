package omok.model

import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule

class RenjuRuleAdapter() : Rule {
    private val fourFourRule = FourFourRule(Board.getSize())
    private val threeThreeRule = ThreeThreeRule(Board.getSize())
    private val moreThanFiveRule = MoreThanFiveRule(Board.getSize())

    override fun isInValid(
        stones: Stones,
        lastPlacedStone: Stone,
    ): Boolean {
        val board = generateCustomBoard(stones)
        return isFourFour(board, lastPlacedStone) or
            isThreeThree(
                board,
                lastPlacedStone,
            ) or isMoreThanFive(board, lastPlacedStone)
    }

    private fun isFourFour(
        board: Array<Array<Int>>,
        stone: Stone,
    ): Boolean {
        return fourFourRule.validate(
            board,
            Pair(stone.point.row, stone.point.col),
        )
    }

    private fun isThreeThree(
        board: Array<Array<Int>>,
        stone: Stone,
    ): Boolean {
        return threeThreeRule.validate(
            board,
            Pair(stone.point.row, stone.point.col),
        )
    }

    private fun isMoreThanFive(
        board: Array<Array<Int>>,
        stone: Stone,
    ): Boolean {
        return moreThanFiveRule.validate(
            board,
            Pair(stone.point.row, stone.point.col),
        )
    }

    private fun generateCustomBoard(stones: Stones): List<List<Int>> {
        val libraryBoard =
            List(Board.getSize()) {
                MutableList(Board.getSize()) { 0 }
            }
        stones.stones.forEach {
            when (it.color) {
                Color.BLACK -> libraryBoard[it.point.col][it.point.row] = 1
                Color.WHITE -> libraryBoard[it.point.col][it.point.row] = 2
            }
        }
        return libraryBoard
    }
}
