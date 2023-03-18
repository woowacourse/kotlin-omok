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

    private fun decidePlayerToPlace(): Player {
        return when (turn) {
            Color.BLACK -> blackStonePlayer
            Color.WHITE -> whiteStonePlayer
        }
    }

    fun start(
        checkBoard: (currentBoard: Board) -> Unit,
        decidePoint: (latestStone: Stone?) -> Point,
    ): OmokGameState {
        while (omokGameState == OmokGameState.Running) {
            board = decidePlayerToPlace().placeStone(board, checkBoard, decidePoint)
            omokGameState = OmokGameState.valueOf(board, turn)
            turn = nextTurn()
        }
        checkBoard(board)

        return omokGameState
    }
}
