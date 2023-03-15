package domain.game

import domain.board.Board
import domain.player.BlackPlayer
import domain.player.WhitePlayer
import domain.stone.Stone
import domain.stone.StoneColor
import listener.OmokStartEndEventListener
import listener.OmokTurnEventListener

class Omok(
    private val startEndEventListener: OmokStartEndEventListener,
    private val turnEventListener: OmokTurnEventListener
) {
    fun run() {
        startEndEventListener.onStartGame()
        var curStoneColor: StoneColor = StoneColor.BLACK
        var curBoard = Board(BlackPlayer(), WhitePlayer())
        do {
            curBoard = takeTurn(curBoard, curStoneColor)
            startEndEventListener.onEndTurn(curBoard.getPlayers())
            curStoneColor = curStoneColor.next()
        } while (curBoard.isRunning())
        startEndEventListener.onEndGame(curStoneColor.next())
    }

    private fun takeTurn(board: Board, stoneColor: StoneColor): Board {
        val newStone = Stone.of(turnEventListener.onTakeTurn(stoneColor))
        val newBoard = board.putStone(stoneColor, newStone)
        if (newBoard == null) {
            turnEventListener.onNotPlaceable()
            return takeTurn(board, stoneColor)
        }
        return newBoard
    }
}
