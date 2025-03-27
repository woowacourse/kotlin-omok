package woowacourse.omok.controller

import woowacourse.omok.domain.omokboard.OmokGame
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.rule.judge.JudgeResult
import woowacourse.omok.domain.rule.place.PlaceResult
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val omokGame = OmokGame()
        outputView.displayOmokGameStart()
        outputView.displayOmokBoard(omokGame.board)

        var position: Position? = null

        while (true) {
            val newPosition: Position = inputView.askForPosition(omokGame.currentTurn, position)
            val playerStone = PlayerStone(omokGame.currentTurn, newPosition)
            val placeResult = omokGame.placeStone(position = newPosition)

            outputView.displayOmokBoard(omokGame.board)
            if (placeResult is PlaceResult.Failure) {
                outputView.displayErrorMessage(placeResult)
                continue
            }

            val judgeResult = omokGame.judge(playerStone = playerStone)
            if (judgeResult is JudgeResult.Finished) {
                outputView.displayGameResultMessage(judgeResult)
                return
            }

            omokGame.reverseTurn()
            position = playerStone.position
        }
    }
}
