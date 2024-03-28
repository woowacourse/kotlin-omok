package woowacourse.omok.model.omokGame

import Controller.requestPlayerMove
import GameRuleAdapter
import android.util.Log
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

class OmokGame(private val listener: GameEventListener) {
    val board = Board()
    var currentStone = Stone.BLACK
    val renjuGameRule =
        GameRuleAdapter().apply {
            setupBoard(board)
        }

    fun startGame(currentStone: Stone): Stone {
        listener.onGameStart()
        //requestPlayerMove(currentStone)
        return currentStone
    }

    fun endGame(winner: Stone) {
        listener.onGameEnd(winner)
    }

    fun placeStone(
        position: Position,
        currentStone: Stone,
    ) {
        renjuGameRule.placeForRuleCheck(position.row, position.column, currentStone)
        board.setStone(position.row, position.column, currentStone)
        if (renjuGameRule.isWinningMove(position.row, position.column, currentStone)) {
            board.gameOver()
        }
        onStonePlaced(currentStone)
    }

    private fun onStonePlaced(currentStone: Stone) {
        Log.d("되나요", currentStone.toString())
        val changeStone = togglePlayer(currentStone)
        listener.printBoard(
            board.gameBoard,
            renjuGameRule.findForbiddenPositions(
                changeStone,
            ),
        )
        this.currentStone =changeStone
//        if (board.isRunning()) {
//            //requestPlayerMove(changeStone)
//        }
    }

    private fun togglePlayer(currentStone: Stone): Stone = if (currentStone == Stone.BLACK) Stone.WHITE else Stone.BLACK
}
