package controller

import android.util.Log
import model.AddStoneStatus
import model.GameBoard
import model.Stone
import model.StoneColor
import view.InputView
import view.Message
import view.Message.ERROR_FORMAT
import view.ResultView
import woowacourse.omok.MainActivity

class OmokController(
    private val inputView: InputView,
    private val outputView: ResultView,
) {
    var turnColor: StoneColor = StoneColor.BLACK
    private set
    val gameBoard = GameBoard()



    fun addValidStone(
        position: String,
    ){
        when (val addStoneStatus = gameBoard.addStone(Stone.ofOrNull(position, turnColor))) {
            AddStoneStatus.IsWin,
            AddStoneStatus.IsAble,
            -> run{
                outputView.printStone(addStoneStatus,turnColor,position)
                turnColor = turnColor.switch()
                outputView.printTurn(turnColor)
            }
            is AddStoneStatus.Failed -> outputView.printError(addStoneStatus)
        }
    }

}
