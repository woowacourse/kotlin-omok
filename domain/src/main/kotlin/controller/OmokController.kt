package controller

import domain.game.Omok
import domain.player.BlackPlayer
import domain.player.WhitePlayer
import domain.rule.OmokRule
import listener.OmokTurnEventListener
import view.OmokInputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokTurnEventListener,
) {
    fun start(blackRule: OmokRule, whiteRule: OmokRule) {
        Omok(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule)).apply {
            outputView.onStartGame()
            while (isPlaying) {
                outputView.onStartTurn(players.curPlayerColor, players.getLastPoint())
                val result = takeTurn(inputView.askPosition(players.curPlayerColor))
                outputView.onEndTurn(result)
            }
            outputView.onEndGame(players)
        }
    }
}
