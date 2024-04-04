import library.OmokRule
import woowacourse.omok.model.Color
import woowacourse.omok.model.Rule
import woowacourse.omok.model.Stone
import woowacourse.omok.model.Stones

class RenjuRule(private val stones: Stones) : Rule {
    private val omokRule: OmokRule
        get() =
            OmokRule(
                generateCustomBoard(stones),
                colorToInt(getCurrentTurn(stones)),
                getOtherColorToInt(getCurrentTurn(stones)),
                BOARD_SIZE,
            )

    override fun checkPlaceable(stone: Stone): Boolean {
        return !checkInvalid(stone)
    }

    private fun checkInvalid(stone: Stone): Boolean {
        if (stone.color == Color.WHITE) return false
        return checkThreeThree(stone) || checkFourFour(stone) || checkMoreThanFive(stone)
    }

    private fun checkThreeThree(stone: Stone): Boolean {
        return omokRule.checkThreeThree(
            stone.coordinate.y - INDEX_ADJUSTMENT,
            stone.coordinate.x - INDEX_ADJUSTMENT,
        )
    }

    private fun checkFourFour(stone: Stone): Boolean {
        return omokRule.countFourFour(
            stone.coordinate.y - INDEX_ADJUSTMENT,
            stone.coordinate.x - INDEX_ADJUSTMENT,
        )
    }

    private fun checkMoreThanFive(stone: Stone): Boolean {
        return omokRule.checkMoreThanFive(
            stone.coordinate.y - INDEX_ADJUSTMENT,
            stone.coordinate.x - INDEX_ADJUSTMENT,
        )
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
                libraryBoard[it.coordinate.x - 1][it.coordinate.y - 1] = BLACK_STONE_VALUE
            } else {
                libraryBoard[it.coordinate.x - 1][it.coordinate.y - 1] = WHITE_STONE_VALUE
            }
        }
        return libraryBoard
    }

    private fun colorToInt(color: Color): Int {
        if (color == Color.BLACK) return BLACK_STONE_VALUE
        return WHITE_STONE_VALUE
    }

    private fun getOtherColorToInt(color: Color): Int {
        if (color == Color.BLACK) return WHITE_STONE_VALUE
        return BLACK_STONE_VALUE
    }

    companion object {
        const val BOARD_SIZE: Int = 15
        const val INDEX_ADJUSTMENT: Int = 1
        const val BLACK_STONE_VALUE: Int = 1
        const val WHITE_STONE_VALUE: Int = 2
    }
}
