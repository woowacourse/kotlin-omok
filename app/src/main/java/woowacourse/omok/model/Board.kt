package woowacourse.omok.model

import woowacourse.omok.model.StoneState.BEFORE_PLACED
import woowacourse.omok.model.StoneState.FORBIDDEN
import woowacourse.omok.model.StoneState.OCCUPIED
import woowacourse.omok.model.StoneState.OUTSIDE_THE_BOARD
import woowacourse.omok.model.StoneState.PLACED

class Board(val stones: Stones = Stones()) {
    private val rule = RuleAdapter(BOARD_SIZE, ::getCurrentStones)
    private val players = listOf(Player(Color.BLACK), Player(Color.WHITE))

    fun getCurrentTurn(turn: Int): Color {
        return players[turn % PLAYER_COUNTS].color
    }

    fun takeTurn(
        turn: Int,
        getCoordinate: () -> Coordinate,
        returnCurrentState: (stoneState: StoneState) -> Unit,
    ) {
        var state: StoneState = BEFORE_PLACED
        while (checkRetryFromState(state)) {
            val stone = players[turn % PLAYER_COUNTS].getStone(getCoordinate)
            state = putStone(stone)
            returnCurrentState(state)
        }
        players[turn % PLAYER_COUNTS].checkOmok(stones, stones.stones.last())
    }

    private fun checkRetryFromState(stoneState: StoneState): Boolean {
        return when (stoneState) {
            BEFORE_PLACED -> true
            OUTSIDE_THE_BOARD -> true
            OCCUPIED -> true
            FORBIDDEN -> true
            PLACED -> false
        }
    }

    fun putStone(stone: Stone): StoneState {
        if (checkCoordinateIsNotOnBoard(stone.coordinate)) {
            return OUTSIDE_THE_BOARD
        } else if (stones.checkOccupiedCoordinate(stone.coordinate)) {
            return OCCUPIED
        }
        return tryPlaceByRule(stone)
    }

    private fun checkCoordinateIsNotOnBoard(coordinate: Coordinate): Boolean {
        return !(coordinate.row.value in BOARD_RANGE && coordinate.col.value in BOARD_RANGE)
    }

    private fun tryPlaceByRule(stone: Stone): StoneState {
        return if (rule.checkPlaceable(stones, stone)) {
            stones.putStone(stone)
            PLACED
        } else {
            return FORBIDDEN
        }
    }

    fun isPlaying(): Boolean {
        return !(players.first().isWin || players.last().isWin)
    }

    fun getWinner(): Color {
        if (!isPlaying()) {
            return checkWhoIsWinner()
        } else {
            throw IllegalStateException("게임이 아직 진행 중입니다.")
        }
    }

    private fun getCurrentStones(): Stones {
        return stones
    }

    private fun checkWhoIsWinner(): Color {
        return if (players.first().isWin) {
            players.first().color
        } else {
            players.last().color
        }
    }

    companion object {
        const val MIN_POSITION = 1
        const val BOARD_SIZE = 15
        const val PLAYER_COUNTS = 2
        val BOARD_RANGE = MIN_POSITION..BOARD_SIZE
    }
}
