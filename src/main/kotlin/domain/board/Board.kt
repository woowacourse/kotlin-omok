package domain.board

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

interface Board {
    val isFinished: Boolean
    val winningColor: Color
    fun isPossiblePut(position: Position): Boolean
    fun getLatestStone(): Stone?
    fun getStones(): List<Stone>
    fun addStone(stone: Stone): Board
}
