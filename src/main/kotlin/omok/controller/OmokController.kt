package omok.controller

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PlayingBoard
import omok.domain.placeresult.GameFinish
import omok.domain.placeresult.GameOnGoing
import omok.domain.placeresult.InvalidMove
import omok.domain.placeresult.PlaceResult
import omok.domain.rule.OmokRule
import omok.domain.service.OmokGame
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val playingBoard = PlayingBoard(OmokBoard.create(), OmokRule.rules)
        outputView.displayOmokGameStart()
        outputView.displayOmokBoard(playingBoard.board)
        OmokGame(playingBoard).start(inputView::askForPosition) { placeResult ->
            handlePlaceResult(playingBoard.board, placeResult)
        }
    }

    private fun handlePlaceResult(
        omokBoard: OmokBoard,
        placeResult: PlaceResult,
    ) {
        outputView.displayOmokBoard(omokBoard)
        when (placeResult) {
            is GameOnGoing -> return
            is GameFinish -> outputView.displayGameResultMessage(placeResult.gameResult)
            is InvalidMove.InvalidPosition, is InvalidMove.AlreadyExistStone -> outputView.displayMisPlaceMessage(placeResult)
            is InvalidMove.ExternalRenjuRule -> outputView.displayForbiddenMessage(placeResult.rule)
        }
    }
}
