package woowacourse.omok.model

import woowacourse.omok.model.StoneState.FORBIDDEN
import woowacourse.omok.model.StoneState.OCCUPIED
import woowacourse.omok.model.StoneState.OUTSIDE_THE_BOARD
import woowacourse.omok.model.StoneState.PLACED

class Board {
    private var _stones: Stones = Stones()
    val stones: Stones
        get() = _stones
    private val rule = RuleAdapter(BOARD_SIZE)
    private val players = Player(Color.BLACK) to Player(Color.WHITE)

    fun setStones(stones: List<Stone>) {
        _stones = Stones(stones)
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
        coordinate: Coordinate,
    ): StoneState {
        val player = getPlayerFromTurn(turn)
        val stone = player.getStone(coordinate)
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
        return when {
            checkCoordinateIsNotOnBoard(stone.coordinate) -> OUTSIDE_THE_BOARD
            stones.checkOccupiedCoordinate(stone.coordinate) -> OCCUPIED
            else -> tryPlaceByRule(stone)
        }
    }

    private fun checkCoordinateIsNotOnBoard(coordinate: Coordinate): Boolean {
        return !(coordinate.row.value in BOARD_RANGE && coordinate.col.value in BOARD_RANGE)
    }

    private fun tryPlaceByRule(stone: Stone): StoneState {
        return when {
            !rule.checkPlaceable(stones, stone) -> FORBIDDEN
            else -> {
                stones.putStone(stone)
                PLACED
            }
        }
    }

    fun getLastStoneOrder(): Int {
        return stones.stones.size
    }

    fun getLastStoneCoordinate(): Coordinate? {
        return stones.getLastStoneCoordinate()
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

    companion object {
        private const val MIN_POSITION = 1
        const val BOARD_SIZE = 15
        val BOARD_RANGE = MIN_POSITION..BOARD_SIZE
    }
}
