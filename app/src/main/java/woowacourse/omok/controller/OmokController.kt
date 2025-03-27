package woowacourse.omok.controller

import woowacourse.omok.domain.omokboard.PlayingBoard
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
        val playingBoard = PlayingBoard()
        outputView.displayOmokGameStart()
        outputView.displayOmokBoard(playingBoard.board)

        var position: Position? = null

        while (true) {
            val newPosition: Position = inputView.askForPosition(playingBoard.currentTurn, position)
            val playerStone = PlayerStone(playingBoard.currentTurn, newPosition)
            val placeResult = playingBoard.placeStone(position = newPosition)

            outputView.displayOmokBoard(playingBoard.board)
            if (placeResult is PlaceResult.Failure) {
                outputView.displayErrorMessage(placeResult)
                continue
            }

            val judgeResult = playingBoard.judge(playerStone = playerStone)
            if (judgeResult is JudgeResult.Finished) {
                outputView.displayGameResultMessage(judgeResult)
                return
            }

            playingBoard.reverseTurn()
            position = playerStone.position
        }
    }
}
