package omok.model

import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule

class RenjuRuleAdapter : Rule {
    private val fourFourRule = FourFourRule(Board.getSize())
    private val threeThreeRule = ThreeThreeRule(Board.getSize())
    private val moreThanFiveRule = MoreThanFiveRule(Board.getSize())

    override fun isInvalid(
        stones: Stones,
        lastPlacedStone: Stone,
        customBoard: Array<Array<Int>>,
    ): Boolean {
        return isFourFour(customBoard, lastPlacedStone) or
            isThreeThree(customBoard, lastPlacedStone) or
            isMoreThanFive(customBoard, lastPlacedStone)
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
}
