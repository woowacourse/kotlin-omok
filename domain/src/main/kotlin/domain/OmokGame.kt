package domain

import domain.board.Board
import domain.player.BlackStonePlayer
import domain.player.Player
import domain.player.WhiteStonePlayer
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class OmokGame(stones: List<Stone> = listOf()) {

    var board = Board(stones)
        private set
    var state: OmokGameState = OmokGameState.Running
        private set
    private var currentPlayer: Player = decideInitialPlayer()
    val turnColor: Color
        get() = currentPlayer.color

    private fun decideInitialPlayer(): Player {
        if(board.latestStone?.color == Color.Black){
            return WhiteStonePlayer()
        }
        return BlackStonePlayer()
    }

    fun placeStone(
        checkBoard: (currentBoard: Board) -> Unit,
        decidePoint: (latestStone: Stone?, currentColor: Color) -> Point,
    ) {
        board = currentPlayer.placeStone(board, checkBoard, decidePoint)
        state = OmokGameState.valueOf(board, turnColor)
        currentPlayer = currentPlayer.toNextPlayer()
    }
}
