package domain.game

import domain.board.Board
import domain.player.BlackPlayer
import domain.player.WhitePlayer
import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneColor
import listener.OmokGameEventListener
import listener.OmokTurnEventListener

class Omok(
    private val turnListener: OmokTurnEventListener,
    private val gameListener: OmokGameEventListener,
    private val rule: OmokRule
) {
    fun run() {
        gameListener.onStartGame()
        var curStoneColor = StoneColor.BLACK
        var curBoard = Board(BlackPlayer(), WhitePlayer(), rule)
        while (curBoard.isRunning) {
            curBoard = takeTurn(curBoard, curStoneColor)
            gameListener.onEndTurn(curBoard.getPlayers())
            if (isEndGame(curBoard, curStoneColor)) break
            curStoneColor = curStoneColor.next()
        }
    }

    private fun takeTurn(board: Board, color: StoneColor): Board {
        val newStone = Stone.of(turnListener.onTakeTurn(color))
        val newBoard = board.putStone(color, newStone)
        return newBoard ?: run {
            turnListener.onStoneNotPlaceable()
            takeTurn(board, color)
        }
    }

    private fun isEndGame(curBoard: Board, curStoneColor: StoneColor): Boolean = when {
        curBoard.isFouling -> {
            gameListener.onEndGame(winnerStoneColor = curStoneColor.next())
            true
        }

        !curBoard.isRunning -> {
            gameListener.onEndGame(winnerStoneColor = curStoneColor)
            true
        }

        else -> false
    }
}
