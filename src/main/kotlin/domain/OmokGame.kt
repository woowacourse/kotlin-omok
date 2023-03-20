package domain

import domain.board.Board
import domain.player.BlackStonePlayer
import domain.player.Player
import domain.player.WhiteStonePlayer
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class OmokGame(
    private val BlackStonePlayer: BlackStonePlayer = BlackStonePlayer(),
    private val WhiteStonePlayer: WhiteStonePlayer = WhiteStonePlayer()
) {

    private var turn: Color = Color.Black
    private var omokGameState: OmokGameState = OmokGameState.Running
    private var board: Board = Board()

    private fun nextTurn(): Color {
        return when (turn) {
            Color.Black -> Color.White
            Color.White -> Color.Black
        }
    }

    private fun decidePlayerToPlace(): Player {
        return when (turn) {
            Color.Black -> BlackStonePlayer
            Color.White -> WhiteStonePlayer
        }
    }

    fun start(
        checkBoard: (currentBoard: Board) -> Unit,
        decidePoint: (latestStone: Stone?) -> Point,
    ): OmokGameState {
        while (omokGameState is OmokGameState.Running) {
            board = decidePlayerToPlace().placeStone(board, checkBoard, decidePoint)
            omokGameState = OmokGameState.valueOf(board, turn)
            turn = nextTurn()
        }
        checkBoard(board)

        return omokGameState
    }
}
