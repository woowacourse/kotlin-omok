package woowacourse.omok.model

import woowacourse.omok.model.StoneState.FORBIDDEN
import woowacourse.omok.model.StoneState.OCCUPIED
import woowacourse.omok.model.StoneState.OUTSIDE_THE_BOARD
import woowacourse.omok.model.StoneState.PLACED

class Board(val stones: Stones = Stones()) {
    private val rule = RuleAdapter(BOARD_SIZE)
    private val players = Player(Color.BLACK) to Player(Color.WHITE)

    fun setStones(stones: List<Stone>) {
        stones.forEach { stone ->
            this.stones.putStone(stone)
        }
    }

    fun getNextTurn(): Color {
        return when (stones.getLastStoneColor()) {
            null -> Color.BLACK
            Color.WHITE -> Color.BLACK
            Color.BLACK -> Color.WHITE
        }
    }

    fun takeTurn(
        turn: Color,
        row: Int,
        col: Int,
    ): StoneState {
        val player = getPlayerFromTurn(turn)
        val stone = player.getStone(row, col)
        val state = putStone(stone)
        if (stones.findOmok(stone)) {
            player.win()
        }
        return state
    }

    private fun getPlayerFromTurn(turn: Color): Player {
        return when (turn) {
            Color.BLACK -> players.first
            Color.WHITE -> players.second
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

    fun getLastStoneOrder(): Int {
        return stones.stones.size
    }

    fun isPlaying(): Boolean {
        return !(players.first.isWin || players.second.isWin)
    }

    fun getWinner(): Color {
        if (!isPlaying()) {
            return checkWhoIsWinner()
        } else {
            throw IllegalStateException("게임이 아직 진행 중입니다.")
        }
    }

    private fun checkWhoIsWinner(): Color {
        return if (players.first.isWin) {
            players.first.color
        } else {
            players.second.color
        }
    }

    fun resetBoard() {
        stones.clearStones()
        players.first.resetWinState()
        players.second.resetWinState()
    }

    companion object {
        const val MIN_POSITION = 1
        const val BOARD_SIZE = 15
        val BOARD_RANGE = MIN_POSITION..BOARD_SIZE
    }
}
