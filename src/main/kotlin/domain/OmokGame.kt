package domain

import domain.board.Board
import domain.player.BlackStonePlayer
import domain.player.Player
import domain.player.WhiteStonePlayer
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class OmokGame(
    private val blackStonePlayer: BlackStonePlayer = BlackStonePlayer(),
    private val whiteStonePlayer: WhiteStonePlayer = WhiteStonePlayer()
) {

    private var turn: Color = Color.BLACK
    private var omokGameState: OmokGameState = OmokGameState.Running
    private var board: Board = Board()

    private fun nextTurn(): Color {
        return when (turn) {
            Color.BLACK -> Color.WHITE
            Color.WHITE -> Color.BLACK
        }
    }

    private fun decidePlayerToPut(): Player {
        return when (turn) {
            Color.BLACK -> blackStonePlayer
            Color.WHITE -> whiteStonePlayer
        }
    }

    fun start(
        checkBoard: (currentBoard: Board) -> Unit,
        decidePosition: (latestStone: Stone?) -> Point
    ) {
        while (omokGameState == OmokGameState.Running) {
            board = decidePlayerToPut().placeStone(board, checkBoard, decidePosition)
            omokGameState = OmokGameState.valueOf(board, turn)
            turn = nextTurn()
        }
    }
}
