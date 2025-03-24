package omok.domain

import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule

class RenjuRule : Rule {
    private val fourFourRule = FourFourRule(15)
    private val threeThreeRule = ThreeThreeRule(15)
    private val moreThanFiveRule = MoreThanFiveRule(15)

    override fun isInvalid(
        stones: Stones,
        lastPlacedStone: Stone,
        playingBoard: Array<Array<StoneType>>,
    ): Boolean {
        return isFourFour(playingBoard, lastPlacedStone) or
            isThreeThree(playingBoard, lastPlacedStone) or
            isMoreThanFive(playingBoard, lastPlacedStone)
    }

    override fun isValidPosition(
        stone: Stone,
        stones: Stones,
        playingBoard: Array<Array<StoneType>>,
    ): Boolean {
        return if (stone.color == StoneType.BLACK) {
            !isInvalid(stones, stone, playingBoard)
        } else {
            true
        }
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
