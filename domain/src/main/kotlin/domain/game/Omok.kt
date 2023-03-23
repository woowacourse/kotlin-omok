package domain.game

import domain.player.Players
import domain.point.Point
import domain.result.TurnResult

class Omok {
    fun takeTurn(point: Point, originPlayers: Players): TurnResult {
        val endTurnPlayers = originPlayers.putStone(point)
        if (endTurnPlayers == originPlayers) return TurnResult.Failure(endTurnPlayers)
        return TurnResult.Success(endTurnPlayers)
    }

    companion object {
        const val OMOK_BOARD_SIZE = 15
    }
}
