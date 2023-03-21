import omok.model.external.OmokRule
import omok.model.game.Board
import omok.model.state.DoubleFour
import omok.model.state.DoubleThree
import omok.model.state.State
import omok.model.state.Stay
import omok.model.state.Win
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class OmokRuleConverter(
    private val board: Board,
    private val color: GoStoneColor
) {
    private val omokRule: OmokRule = OmokRule(board.convert(), color.convert(), color.reverse())

    fun countOpenThrees(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.countOpenThrees(pair.first, pair.second) >= 2) {
            DoubleThree(board)
        } else Stay(board)
    }

    fun countOpenFours(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.countOpenFours(pair.first, pair.second) >= 2) {
            DoubleFour(board)
        } else Stay(board)
    }

    fun checkBlackWin(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.validateBlackWin(pair.first, pair.second)) Win(board) else Stay(board)
    }

    fun checkWhiteWin(coordinate: Coordinate): State {
        val pair = coordinate.toPair()
        return if (omokRule.validateWhiteWin(pair.first, pair.second)) Win(board) else Stay(board)
    }

    private fun Coordinate.toPair(): Pair<Int, Int> {
        return this.x to this.y
    }

    companion object {
        private fun Board.convert(): List<List<Int>> {
            return this.board.map { it.map(convertColor()) }
        }

        private fun convertColor(): (GoStone?) -> Int = {
            when {
                it == null -> 0
                it.color == GoStoneColor.BLACK -> 1
                it.color == GoStoneColor.WHITE -> 2
                else -> throw IllegalArgumentException()
            }
        }

        private fun GoStoneColor.convert(): Int = when (this) {
            GoStoneColor.BLACK -> 1
            GoStoneColor.WHITE -> 2
        }

        private fun GoStoneColor.reverse(): Int = when (this) {
            GoStoneColor.BLACK -> 2
            GoStoneColor.WHITE -> 1
        }
    }
}
