package woowacourse.omok.controller

import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.OmokGame
import woowacourse.omok.domain.omokboard.PlayingBoard
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

        val omokGame = OmokGame(playingBoard)
        val gameResult =
            omokGame.start(inputView::askForPosition) { placeResult ->
                handlePlaceResult(playingBoard.board, placeResult)
            }
        outputView.displayGameResultMessage(gameResult)
    }

    private fun handlePlaceResult(
        omokBoard: OmokBoard,
        placeResult: PlaceResult,
    ) {
        outputView.displayOmokBoard(omokBoard)
        when (placeResult) {
            is PlaceResult.Success -> return
            is PlaceResult.Failure -> outputView.displayErrorMessage(placeResult)
        }
    }
}
