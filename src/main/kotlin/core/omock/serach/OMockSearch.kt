package core.omock.serach

import core.omock.Direction
import core.omock.Result

abstract class OMockSearch {
    abstract fun loadMap(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Map<Direction, Result>

    companion object Default : OMockSearch() {
        override fun loadMap(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Map<Direction, Result> = OMockSearchImpl.loadMap(stoneStates, row, column)
    }
}
