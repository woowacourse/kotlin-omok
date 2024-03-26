package omok.model

import omok.library.RenjuRule

class RuleAdapter(private val boardSize: Int, private val getStones: () -> Stones) : Rule {
    private val renjuRule: RenjuRule
        get() =
            RenjuRule(
                generateCustomBoard(boardSize, getStones()),
                colorToInt(getCurrentTurn(getStones())),
                getOtherColorToInt(getCurrentTurn(getStones())),
                boardSize,
            )

    override fun checkPlaceable(stone: Stone): Boolean {
        return !checkUnable(stone)
    }

    private fun checkUnable(stone: Stone): Boolean {
        if (stone.color == Color.BLACK) return checkThreeThree(stone) || checkFourFour(stone) || checkMoreThanFive(stone)
        return false
    }

    private fun checkThreeThree(stone: Stone): Boolean {
        return renjuRule.checkThreeThree(
            stone.coordinate.col.value - INDEX_ADJUSTMENT,
            stone.coordinate.row.value - INDEX_ADJUSTMENT,
        )
    }

    private fun checkFourFour(stone: Stone): Boolean {
        return renjuRule.countFourFour(
            stone.coordinate.col.value - INDEX_ADJUSTMENT,
            stone.coordinate.row.value - INDEX_ADJUSTMENT,
        )
    }

    private fun checkMoreThanFive(stone: Stone): Boolean {
        return renjuRule.checkMoreThanFive(
            stone.coordinate.col.value - INDEX_ADJUSTMENT,
            stone.coordinate.row.value - INDEX_ADJUSTMENT,
        )
    }

    private fun getCurrentTurn(stones: Stones): Color {
        if (stones.stones.isEmpty()) return Color.BLACK
        return when (stones.stones.last().color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE -> Color.BLACK
        }
    }

    private fun generateCustomBoard(
        boardSize: Int,
        stones: Stones,
    ): List<List<Int>> {
        val libraryBoard =
            List(boardSize) {
                MutableList(boardSize) { UNPLACED_INT }
            }
        stones.stones.forEach {
            if (it.color == Color.BLACK) {
                libraryBoard[it.coordinate.row.value - INDEX_ADJUSTMENT][it.coordinate.col.value - INDEX_ADJUSTMENT] =
                    BLACK_COLOR_INT
            } else {
                libraryBoard[it.coordinate.row.value - INDEX_ADJUSTMENT][it.coordinate.col.value - INDEX_ADJUSTMENT] =
                    WHITE_COLOR_INT
            }
        }
        return libraryBoard
    }

    private fun colorToInt(color: Color): Int {
        if (color == Color.BLACK) return BLACK_COLOR_INT
        return WHITE_COLOR_INT
    }

    private fun getOtherColorToInt(color: Color): Int {
        if (color == Color.BLACK) return WHITE_COLOR_INT
        return BLACK_COLOR_INT
    }

    companion object {
        const val INDEX_ADJUSTMENT: Int = 1
        const val BLACK_COLOR_INT: Int = 1
        const val WHITE_COLOR_INT: Int = 2
        const val UNPLACED_INT: Int = 0
    }
}
