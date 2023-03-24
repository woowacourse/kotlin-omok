package domain.game

import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.point.Point
import domain.result.TurnResult

class Omok(blackPlayer: BlackPlayer, whitePlayer: WhitePlayer) {
    private var _players = Players(blackPlayer, whitePlayer)
    val players: Players
        get() = _players.copy()
    val isPlaying: Boolean
        get() = _players.isPlaying

    fun takeTurn(point: Point): TurnResult {
        val endTurnPlayers = _players.putStone(point)
        if (endTurnPlayers == _players) return TurnResult.Failure(_players)
        _players = endTurnPlayers
        return TurnResult.Success(_players)
    }

    companion object {
        const val OMOK_BOARD_SIZE = 15
    }
}
