package controller

import domain.OmokGame
import domain.OmokGameState
import domain.stone.Color
import view.InputView
import view.OutputView

class OmokController(private val omokGame: OmokGame = OmokGame()) {

    fun run() {
        runCatching {
            OutputView.printGameStartMessage()
            do{
                omokGame.placeStone(OutputView::printOmokBoard, InputView::requestPoint)
            }while(omokGame.state == OmokGameState.Running)
            getResult(omokGame.state)
        }.onFailure { ex ->
            OutputView.printExceptionMessage(ex)
        }
    }

    private fun getResult(omokGameState: OmokGameState){
        if(omokGameState is OmokGameState.End){
            OutputView.printWinner(omokGameState.winningColor)
            return
        }
        throw IllegalStateException("[ERROR] 오목 게임의 결과를 알 수 없습니다.")
    }
}
