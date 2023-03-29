package controller

import domain.game.Omok
import domain.player.BlackPlayer
import domain.player.WhitePlayer
import domain.result.TurnResult
import domain.rule.OmokRule
import view.OmokInputView
import view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    fun start(blackRule: OmokRule, whiteRule: OmokRule) {
        Omok(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule)).apply {
            var result: TurnResult = TurnResult.Playing(false, players)
            outputView.onStartGame()
            while (result is TurnResult.Playing) {
                outputView.onStartTurn(players.curPlayerColor, players.getLastPoint())
                result = takeTurn(inputView.askPosition(players.curPlayerColor))
                outputView.onEndTurn(result)
            }
            outputView.onEndGame(result)
        }
    }
}
