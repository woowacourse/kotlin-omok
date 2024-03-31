package omok.model

import omok.library.BlackWinRule
import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule
import omok.library.WhiteWinRule

class RenjuRuleAdapter() : Rule {
    private val fourFourRule = FourFourRule(Stones.getSize())
    private val threeThreeRule = ThreeThreeRule(Stones.getSize())
    private val moreThanFiveRule = MoreThanFiveRule(Stones.getSize())
    private val blackWinRule = BlackWinRule(Stones.getSize())
    private val whiteWinRule = WhiteWinRule(Stones.getSize())

    override fun isInValid(
        stones: List<Stone>,
        lastPlacedStone: Stone,
    ): Boolean {
        val board = generateCustomBoard(stones)
        return isFourFour(board, lastPlacedStone) or
            isThreeThree(
                board,
                lastPlacedStone,
            ) or isMoreThanFive(board, lastPlacedStone)
    }

    override fun isBlackWin(
        stones: List<Stone>,
        lastPlacedPosition: Point,
    ): Boolean {
        return blackWinRule.validate(
            generateCustomBoard(stones),
            Pair(lastPlacedPosition.row, lastPlacedPosition.col),
        )
    }

    override fun isWhiteWin(
        stones: List<Stone>,
        lastPlacedStonePosition: Point,
    ): Boolean {
        return whiteWinRule.validate(
            generateCustomBoard(stones),
            Pair(lastPlacedStonePosition.row, lastPlacedStonePosition.col),
        )
    }

    private fun isFourFour(
        board: List<List<Int>>,
        stone: Stone,
    ): Boolean {
        return fourFourRule.validate(
            board,
            Pair(stone.point.row, stone.point.col),
        )
    }

    private fun isThreeThree(
        board: List<List<Int>>,
        stone: Stone,
    ): Boolean {
        return threeThreeRule.validate(
            board,
            Pair(stone.point.row, stone.point.col),
        )
    }

    private fun isMoreThanFive(
        board: List<List<Int>>,
        stone: Stone,
    ): Boolean {
        return moreThanFiveRule.validate(
            board,
            Pair(stone.point.row, stone.point.col),
        )
    }

    private fun generateCustomBoard(stones: List<Stone>): List<List<Int>> {
        val libraryStones =
            List(Stones.getSize()) {
                MutableList(Stones.getSize()) { 0 }
            }
        stones.forEach {
            when (it.color) {
                Color.BLACK -> libraryStones[it.point.col][it.point.row] = 1
                Color.WHITE -> libraryStones[it.point.col][it.point.row] = 2
            }
        }
        return libraryStones
    }
}
