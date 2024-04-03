package woowacourse.omok.game.model

import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule

class RenjuRuleAdapter : Rule {
    private val fourFourRule = FourFourRule(Board.getSize())
    private val threeThreeRule = ThreeThreeRule(Board.getSize())
    private val moreThanFiveRule = MoreThanFiveRule(Board.getSize())

    private var customBoard: Array<Array<Int>> = Array(Board.getSize()) { Array(Board.getSize()) { 0 } }

    override fun isInvalid(
        size: Int,
        stones: Stones,
        lastPlacedStone: Stone,
    ): Boolean {
        updateCustomBoard(lastPlacedStone)
        if (lastPlacedStone.color.isBlack()) {
            return isFourFour(customBoard, lastPlacedStone) or
                isThreeThree(customBoard, lastPlacedStone) or
                isMoreThanFive(customBoard, lastPlacedStone)
        }
        return false
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

    private fun updateCustomBoard(stone: Stone) {
        customBoard[stone.point.row][stone.point.col] =
            (if (stone.color.isWhite()) 2 else 1)
    }

    override fun resetCustomBoard() {
        customBoard = Array(Board.getSize()) { Array(Board.getSize()) { 0 } }
    }
}
