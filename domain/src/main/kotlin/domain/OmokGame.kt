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

    private var currentPlayer: Player = blackStonePlayer
    private var board = Board()
    private var state: OmokGameState = OmokGameState.Running

    private fun Player.toNextPlayer(): Player {
        if (this == blackStonePlayer) {
            return whiteStonePlayer
        }
        return blackStonePlayer
    }

    fun placeStone(
        checkBoard: (currentBoard: Board) -> Unit,
        decidePoint: (latestStone: Stone?, currentColor: Color) -> Point,
    ): OmokGameState {
        val point = decidePoint(board.latestStone, currentPlayer.color)

        board = currentPlayer.placeStone(board, point)
        state = OmokGameState.valueOf(board, currentPlayer.color)
        currentPlayer = currentPlayer.toNextPlayer()
        checkBoard(board)

        return state
    }
}
