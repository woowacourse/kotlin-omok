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
    private val blackRule: OmokRule,
    private val whiteRule: OmokRule,
) {
    fun run() {
        var players = Players(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule))
        gameEventListener.onStartGame()

        while (players.isPlaying) {
            gameEventListener.onStartTurn(players.curPlayerColor, players.getLastPoint())
            players = takeTurn(players)
            gameEventListener.onEndTurn(players)
        }
        endGame(players)
    }

    private fun takeTurn(originPlayers: Players): Players {
        val newPoint = turnEventListener.onTakeTurn(originPlayers.curPlayerColor)
        val endTurnPlayers = originPlayers.putStone(newPoint)
        if (!endTurnPlayers.isPut(originPlayers)) {
            turnEventListener.onNotPlaceable()
            return takeTurn(originPlayers)
        }
        return endTurnPlayers
    }

    private fun endGame(players: Players): Boolean = when {
        players.isFoul -> {
            gameEventListener.onEndGame(players.curPlayerColor)
            true
        }
        !players.isPlaying -> {
            gameEventListener.onEndGame(players.curPlayerColor.next())
            true
        }
        else -> false
    }

    companion object  {
        const val OMOK_BOARD_SIZE = 15
    }
}
