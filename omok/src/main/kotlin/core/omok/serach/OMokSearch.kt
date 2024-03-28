package core.omok.serach

import core.omok.Direction
import core.omok.OMokResult

abstract class OMokSearch {
    abstract fun loadMap(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Map<Direction, OMokResult>

    companion object Default : OMokSearch() {
        override fun loadMap(
            stoneStates: List<List<Int>>,
            row: Int,
            column: Int,
        ): Map<Direction, OMokResult> = OMokSearchImpl.loadMap(stoneStates, row, column)
    }
}
