package woowacourse.omok.ui.controller

import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.place.PlaceResult
import woowacourse.omok.ui.view.InputView
import woowacourse.omok.ui.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val omokGame = OmokGame()
        outputView.displayOmokGameStart()
        outputView.displayOmokBoard(omokGame.game.board)

        var position: Position? = null

        while (true) {
            val newPosition: Position = inputView.askForPosition(omokGame.currentTurn, position)
            val playerStone = PlayerStone(omokGame.currentTurn, newPosition)
            val placeResult = omokGame.placeStone(newPosition)

            outputView.displayOmokBoard(omokGame.game.board)
            if (placeResult is PlaceResult.Failure) {
                outputView.displayErrorMessage(placeResult)
                continue
            }

            val judgeResult = omokGame.judge(playerStone)
            if (judgeResult is JudgeResult.Finished) {
                outputView.displayGameResultMessage(judgeResult)
                return
            }

            position = playerStone.position
        }
    }
}
