package controller

import domain.game.Omok
import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.rule.OmokRule
import listener.OmokGameEventListener
import listener.OmokTurnEventListener

class OmokController(
    private val inputView: OmokTurnEventListener,
    private val outputView: OmokGameEventListener,
) {
    fun start(blackRule: OmokRule, whiteRule: OmokRule) {
        Omok(inputView, outputView).apply {
            var players = Players(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule))
            outputView.onStartGame()

            while (players.isPlaying) {
                outputView.onStartTurn(players.curPlayerColor, players.getLastPoint())
                players = takeTurn(players)
                outputView.onEndTurn(players)
            }
            endGame(players)
        }
    }
}
