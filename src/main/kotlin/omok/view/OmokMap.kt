package omok.view

import omok.domain.OmokBoard
import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState

object OmokMap {
    fun makeMap(omokBoard: OmokBoard): List<List<String>> {
        val omokMap = MutableList(omokBoard.ySize) { MutableList(omokBoard.xSize) { "" } }
        omokBoard.keys.forEach { point ->
            omokMap[point.y.value - 1][point.x.value - 1] = getString(omokBoard[point], point.y.value - 1, point.x.value - 1)
        }
        return omokMap
    }

    private fun getString(stoneState: StoneState, x: Int, y: Int): String =
        getFix(x, y).format(getStone(stoneState, x, y))

    private fun getFix(x: Int, y: Int): String = when {
        x == X_MIN_INDEX && y == Y_MIN_INDEX -> " %s─"
        x == X_MIN_INDEX && y == Y_MAX_INDEX -> "─%s "
        x == X_MAX_INDEX && y == Y_MIN_INDEX -> " %s─"
        x == X_MAX_INDEX && y == Y_MAX_INDEX -> "─%s "
        x == X_MIN_INDEX -> "─%s─"
        x == X_MAX_INDEX -> "─%s─"
        y == Y_MIN_INDEX -> " %s─"
        y == Y_MAX_INDEX -> "─%s "
        else -> "─%s─"
    }

    private fun getStone(stoneState: StoneState, x: Int, y: Int): String = when {
        stoneState == BlackStoneState -> "●"
        stoneState == WhiteStoneState -> "○"
        x == X_MAX_INDEX && y == Y_MIN_INDEX -> "┌"
        x == X_MAX_INDEX && y == Y_MAX_INDEX -> "┐"
        x == X_MIN_INDEX && y == Y_MIN_INDEX -> "└"
        x == X_MIN_INDEX && y == Y_MAX_INDEX -> "┘"
        x == X_MAX_INDEX -> "┬"
        x == X_MIN_INDEX -> "┴"
        y == Y_MIN_INDEX -> "├"
        y == Y_MAX_INDEX -> "┤"
        else -> "┼"
    }

    private const val X_MIN_INDEX = OmokBoard.X_MIN_RANGE - 1
    private const val X_MAX_INDEX = OmokBoard.X_MAX_RANGE - 1
    private const val Y_MIN_INDEX = OmokBoard.Y_MIN_RANGE - 1
    private const val Y_MAX_INDEX = OmokBoard.Y_MAX_RANGE - 1
}
