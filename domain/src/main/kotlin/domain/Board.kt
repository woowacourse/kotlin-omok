package domain

import domain.rule.Referee
import domain.state.*
import domain.stone.Stone
import domain.stone.Stones
import java.lang.IllegalArgumentException

class Board(val stones: Stones = Stones(setOf())) {

    private var state: State =
        when (stones.blackStones.size) {
            stones.whiteStones.size + 1 -> WhiteTurn(stones)
            else -> BlackTurn(stones)
        }
    private val defaultReferee = Referee(listOf())

    fun put(point: Pair<Int, Int>, blackReferee: Referee = defaultReferee) {
        state = when {
            isBlackTurn() -> state.put(point, blackReferee)
            isWhiteTurn() -> state.put(point)
            else -> throw IllegalArgumentException("둘을 둘 수 없는 상태입니다.")
        }
    }

    fun isFinished(): Boolean = state is Finished

    fun isBlackTurn(): Boolean = state is BlackTurn
    fun isWhiteTurn(): Boolean = state is WhiteTurn

    fun isBlackWin(): Boolean = state is BlackWin

    fun blackStoneIsPlaced(stone: Stone): Boolean = state.stones.blackStones.contains(stone)

    fun whiteStoneIsPlaced(stone: Stone): Boolean = state.stones.whiteStones.contains(stone)

    companion object {
        const val MIN_VIEW_X = 'A'
        const val MAX_VIEW_X = 'O'
        const val MIN_VIEW_Y = 1
        const val MAX_VIEW_Y = 15
        const val BOARD_SIZE = 15
    }
}
