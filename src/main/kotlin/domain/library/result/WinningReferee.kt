package domain.library.result

import domain.stone.Color
import domain.stone.Stone

interface WinningReferee {
    fun checkWin(placedStones: List<Stone>, color: Color): Boolean
}
