package omok.view

import omok.configure.Constants.X_MAX_RANGE
import omok.configure.Constants.X_MIN_RANGE
import omok.configure.Constants.Y_MAX_RANGE
import omok.configure.Constants.Y_MIN_RANGE
import omok.domain.OmokBoard
import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState

object OmokMapUIModel {
    fun adaptMap(omokBoard: OmokBoard): List<List<String>> {
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

    private const val X_MIN_INDEX = 0
    private const val X_MAX_INDEX = X_MAX_RANGE - X_MIN_RANGE
    private const val Y_MIN_INDEX = 0
    private const val Y_MAX_INDEX = Y_MAX_RANGE - Y_MIN_RANGE
}
