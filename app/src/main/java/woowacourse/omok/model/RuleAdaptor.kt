import library.RenjuRule
import woowacourse.omok.model.Color
import woowacourse.omok.model.Rule
import woowacourse.omok.model.Stone
import woowacourse.omok.model.Stones

class RuleAdaptor(private val stones: Stones) : Rule {
    private val renjuRule: RenjuRule
        get() =
            RenjuRule(
                generateCustomBoard(stones),
                colorToInt(getCurrentTurn(stones)),
                getOtherColorToInt(getCurrentTurn(stones)),
                BOARD_SIZE,
            )

    override fun checkPlaceable(stone: Stone): Boolean {
        return !checkInvalid(stone)
    }

    fun checkInvalid(stone: Stone): Boolean {
        if (stone.color == Color.WHITE) return false
        return checkThreeThree(stone) || checkFourFour(stone) || checkMoreThanFive(stone)
    }

    private fun checkThreeThree(stone: Stone): Boolean {
        return renjuRule.checkThreeThree(
            stone.coordinate.y - INDEX_ADJUSTMENT,
            stone.coordinate.x - INDEX_ADJUSTMENT,
        )
    }

    private fun checkFourFour(stone: Stone): Boolean {
        return renjuRule.countFourFour(
            stone.coordinate.y - INDEX_ADJUSTMENT,
            stone.coordinate.x - INDEX_ADJUSTMENT,
        )
    }

    private fun checkMoreThanFive(stone: Stone): Boolean {
        return renjuRule.checkMoreThanFive(
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
                libraryBoard[it.coordinate.x - 1][it.coordinate.y - 1] = 1
            } else {
                libraryBoard[it.coordinate.x - 1][it.coordinate.y - 1] = 2
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
