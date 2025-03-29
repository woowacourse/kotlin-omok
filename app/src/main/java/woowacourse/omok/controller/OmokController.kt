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
import woowacourse.omok.DbHelper
import woowacourse.omok.MainActivity
import android.content.ContentValues
import model.Col
import model.Position

class OmokController(
    private val inputView: InputView,
    private val outputView: ResultView,
    private val dbHelper: DbHelper,
    private val roomId: Int
) {
    var turnColor: StoneColor = StoneColor.BLACK
    val gameBoard = GameBoard()

    fun addValidStone(
        position: String,
    ) {
        val stone = Stone.ofOrNull(position, turnColor)
        when (val addStoneStatus = gameBoard.addStone(stone)) {
            AddStoneStatus.IsWin,
            AddStoneStatus.IsAble,
                -> run {
                outputView.printStone(addStoneStatus, turnColor, position)
                saveStoneToDatabase(stone)
                turnColor = turnColor.switch()
                outputView.printTurn(turnColor)
            }
            is AddStoneStatus.Failed -> outputView.printError(addStoneStatus)
        }
    }

    private fun saveStoneToDatabase(stone: Stone?) {
        if (stone == null) return
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("room_id", roomId)
            put("x", stone.position.row.value)
            put("y", stone.position.col.value.toString())
            put("color", stone.color.name.lowercase())
            put("turn", gameBoard.stones.size)
        }
        val result = db.insert("stones", null, values)
        if (result == -1L) {
            Log.e("OmokController", "Failed to save stone to DB")
        } else {
            Log.d("OmokController", "Stone saved to DB: $result")
        }
        db.close()
    }


}
