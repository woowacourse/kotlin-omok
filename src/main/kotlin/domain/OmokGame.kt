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

    private var state: OmokGameState = OmokGameState.Running
    private var currentPlayer: Player = blackStonePlayer
    private var board = Board()

    private fun Player.toNextPlayer(): Player {
        if (this == blackStonePlayer) {
            return whiteStonePlayer
        }
        return blackStonePlayer
    }

    private fun OmokGameState.getWinner(): Color {
        if (this is OmokGameState.End) {
            return winningColor
        }
        throw IllegalStateException(GET_WINNING_COLOR_ERROR)
    }

    fun start(
        checkBoard: (currentBoard: Board) -> Unit,
        decidePoint: (latestStone: Stone?, currentColor: Color) -> Point,
    ): Color {
        while (state is OmokGameState.Running) {
            board = currentPlayer.placeStone(board, checkBoard, decidePoint)
            state = OmokGameState.valueOf(board, currentPlayer.color)
            currentPlayer = currentPlayer.toNextPlayer()
        }
        checkBoard(board)

        return state.getWinner()
    }

    companion object {
        const val GET_WINNING_COLOR_ERROR = "[ERROR]: 승자를 확인할 수 없습니다."
    }
}
