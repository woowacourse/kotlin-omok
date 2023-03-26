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
    private var state: OmokGameState = OmokGameState.Running
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
    ): OmokGameState {
        if(state is OmokGameState.Running){
            board = currentPlayer.placeStone(board, checkBoard, decidePoint)
            state = OmokGameState.valueOf(board, turnColor)
            currentPlayer = currentPlayer.toNextPlayer()
            return state
        }
        throw IllegalStateException(ERROR_PLACING_STONE)
    }

    companion object{
        private const val ERROR_PLACING_STONE = "[ERROR] 게임에서 돌을 둘 수 없는 상태입니다."
    }
}
