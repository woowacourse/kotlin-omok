package controller

import domain.game.Omok
import domain.player.BlackPlayer
import domain.player.WhitePlayer
import domain.rule.OmokRule
import listener.OmokGameEventListener
import listener.OmokTurnEventListener

class OmokController(
    private val inputView: OmokTurnEventListener,
    private val outputView: OmokGameEventListener,
) {
    fun start(blackRule: OmokRule, whiteRule: OmokRule) {
        Omok(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule)).apply {
            outputView.onStartGame()
            while (isPlaying) {
                outputView.onStartTurn(players.curPlayerColor, players.getLastPoint())
                val result = takeTurn(inputView.onTakeTurn(players.curPlayerColor))
                outputView.onEndTurn(result)
            }
            outputView.onEndGame(players)
        }
    }
}
