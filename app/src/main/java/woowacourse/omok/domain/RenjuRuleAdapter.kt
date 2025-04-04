package woowacourse.omok.domain

import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule

class RenjuRuleAdapter : Rule {
    private val fourFourRule = FourFourRule(Board.BOARD_SIZE)
    private val threeThreeRule = ThreeThreeRule(Board.BOARD_SIZE)
    private val moreThanFiveRule = MoreThanFiveRule(Board.BOARD_SIZE)

    override fun isInvalid(
        stones: Stones,
        lastPlacedStone: Stone,
        playingBoard: Array<Array<StoneType>>,
    ): Boolean {
        return isFourFour(playingBoard, lastPlacedStone) ||
            isThreeThree(playingBoard, lastPlacedStone) ||
            isMoreThanFive(playingBoard, lastPlacedStone)
    }

    private fun isFourFour(
        board: Array<Array<StoneType>>,
        stone: Stone,
    ): Boolean {
        return fourFourRule.validate(
            board,
            Pair(stone.position.row, stone.position.column),
        )
    }

    private fun isThreeThree(
        board: Array<Array<StoneType>>,
        stone: Stone,
    ): Boolean {
        return threeThreeRule.validate(
            board,
            Pair(stone.position.row, stone.position.column),
        )
    }

    private fun isMoreThanFive(
        board: Array<Array<StoneType>>,
        stone: Stone,
    ): Boolean {
        return moreThanFiveRule.validate(
            board,
            Pair(stone.position.row, stone.position.column),
        )
    }
}
