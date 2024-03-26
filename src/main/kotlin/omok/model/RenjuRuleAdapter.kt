package omok.model

import omok.library.BlackWinRule
import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule
import omok.library.WhiteWinRule

class RenjuRuleAdapter() : Rule {
    private val fourFourRule = FourFourRule(Board.getSize())
    private val threeThreeRule = ThreeThreeRule(Board.getSize())
    private val moreThanFiveRule = MoreThanFiveRule(Board.getSize())
    private val blackWinRule = BlackWinRule(Board.getSize())
    private val whiteWinRule = WhiteWinRule(Board.getSize())

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

    override fun isBlackWin(
        stones: Stones,
        lastPlacedPosition: Point,
    ): Boolean {
        return blackWinRule.validate(
            generateCustomBoard(stones),
            Pair(lastPlacedPosition.row, lastPlacedPosition.col),
        )
    }

    override fun isWhiteWin(
        stones: Stones,
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
