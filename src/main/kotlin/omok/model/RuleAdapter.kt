package omok.model

import library.OmokRule

class RuleAdapter(stones: Stones) : Rule {
    private val omokRule =
        OmokRule(
            generateCustomBoard(stones),
            colorToInt(getCurrentTurn(stones)),
            getOtherColorToInt(getCurrentTurn(stones)),
            BOARD_SIZE,
        )

    override fun checkThreeThree(stone: Stone): Boolean {
        return omokRule.checkThreeThree(
            stone.coordinate.col.value - INDEX_ADJUSTMENT,
            stone.coordinate.row.value - INDEX_ADJUSTMENT,
        )
    }

    override fun checkFourFour(stone: Stone): Boolean {
        return omokRule.countFourFour(
            stone.coordinate.col.value - INDEX_ADJUSTMENT,
            stone.coordinate.row.value - INDEX_ADJUSTMENT,
        )
    }

    override fun checkMoreThanFive(stone: Stone): Boolean {
        return omokRule.checkMoreThanFive(
            stone.coordinate.col.value - INDEX_ADJUSTMENT,
            stone.coordinate.row.value - INDEX_ADJUSTMENT,
        )
    }

    override fun checkInvalid(stone: Stone): Boolean {
        if (stone.color == Color.BLACK) return checkThreeThree(stone) || checkFourFour(stone) || checkMoreThanFive(stone)
        return false
    }

    private fun getCurrentTurn(stones: Stones): Color {
        if (stones.stones.isEmpty()) return Color.BLACK
        return when (stones.stones.last().color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE -> Color.BLACK
        }
    }

    private fun generateCustomBoard(stones: Stones): List<List<Int>> {
        val libraryBoard =
            List(BOARD_SIZE) {
                MutableList(BOARD_SIZE) { 0 }
            }
        stones.stones.forEach {
            if (it.color == Color.BLACK) {
                libraryBoard[it.coordinate.row.value - 1][it.coordinate.col.value - 1] = 1
            } else {
                libraryBoard[it.coordinate.row.value - 1][it.coordinate.col.value - 1] = 2
            }
        }
        return libraryBoard
    }

    private fun colorToInt(color: Color): Int {
        if (color == Color.BLACK) return 1
        return 2
    }

    private fun getOtherColorToInt(color: Color): Int {
        if (color == Color.BLACK) return 2
        return 1
    }

    companion object {
        const val BOARD_SIZE: Int = 15
        const val INDEX_ADJUSTMENT: Int = 1
    }
}
