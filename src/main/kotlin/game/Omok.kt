package game

import Board
import Stone
import StoneColor
import listener.OmokEventListener
import player.BlackPlayer
import player.WhitePlayer

class Omok(private val eventListener: OmokEventListener) {
    private val board = Board(BlackPlayer(), WhitePlayer())

    fun run() {
        eventListener.onStartGame()
        var currentStoneColor: StoneColor = StoneColor.BLACK
        var currentBoard: Board = board
        do {
            currentBoard = takeTurn(currentBoard, currentStoneColor)
            eventListener.onEndTurn(currentBoard.getPlayers())
            currentStoneColor = currentStoneColor.next()
        } while (currentBoard.isRunning())

        eventListener.onEndGame(board.getWinner())
    }

    private fun takeTurn(board: Board, stoneColor: StoneColor): Board {
        val newStone = Stone.of(eventListener.onTakeTurn(stoneColor))
        return board.putStone(stoneColor, newStone) ?: return takeTurn(board, stoneColor)
    }
}
