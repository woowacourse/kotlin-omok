package domain.game

import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.rule.OmokRule
import listener.OmokGameEventListener
import listener.OmokTurnEventListener

class Omok(
    private val turnEventListener: OmokTurnEventListener,
    private val gameEventListener: OmokGameEventListener,
) {
    fun takeTurn(originPlayers: Players): Players {
        val newPoint = turnEventListener.onTakeTurn(originPlayers.curPlayerColor)
        val endTurnPlayers = originPlayers.putStone(newPoint)
        if (!endTurnPlayers.isPut(originPlayers)) turnEventListener.onNotPlaceable()

        return endTurnPlayers
    }

    fun endGame(players: Players) {
        when {
            players.isFoul -> gameEventListener.onEndGame(players.curPlayerColor)
            !players.isPlaying -> gameEventListener.onEndGame(players.curPlayerColor.next())
        }
    }

    companion object {
        const val OMOK_BOARD_SIZE = 15
    }
}
