package omok.model.omokGame

import Controller.game
import Controller.requestPlayerMove
import GameRuleAdapter
import omok.model.board.Position
import omok.model.board.Stone

class OmokGame(private val listener: GameEventListener) {
    lateinit var currentStone: Stone
    var board = Board()
    val renjuGameRule =
        GameRuleAdapter().apply {
            setupBoard(board)
        }

    fun startGame(): Stone {
        currentStone = Stone.BLACK
        listener.onGameStart()
        requestPlayerMove(currentStone)
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
        if (renjuGameRule.isWinningMove(position.row, position.column, currentStone)) board.gameOver()
        onStonePlaced(currentStone)
    }

    private fun onStonePlaced(currentStone: Stone) {
        game.currentStone = togglePlayer(currentStone)
        listener.printBoard(
            game.board.gameBoard,
            game.board.findForbiddenPositions(
                game.currentStone,
            ),
        )
        if (game.board.isRunning()) {
            requestPlayerMove(game.currentStone)
        }
    }

    private fun togglePlayer(currentStone: Stone): Stone = if (currentStone == Stone.BLACK) Stone.WHITE else Stone.BLACK
}
