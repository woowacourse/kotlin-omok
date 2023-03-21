package domain

import domain.board.Board
import domain.player.BlackStonePlayer
import domain.player.Player
import domain.player.PlayerState
import domain.player.WhiteStonePlayer
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class OmokGame {
    private var currentPlayer: Player = BlackStonePlayer()
    var board = Board()
        private set
    var state: OmokGameState = OmokGameState.Running
        private set

    fun placeStone(
        checkBoard: (currentBoard: Board) -> Unit,
        decidePoint: (latestStone: Stone?, currentColor: Color) -> Point,
    ) {
        board = currentPlayer.placeStone(board, checkBoard, decidePoint)
        state = OmokGameState.valueOf(board, currentPlayer.color)
        currentPlayer = currentPlayer.toNextPlayer()
    }
}
