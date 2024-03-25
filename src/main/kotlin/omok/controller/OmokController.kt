package omok.controller

import omok.ExceptionHandler
import omok.model.BlackStonePlayer
import omok.model.Board
import omok.model.Color
import omok.model.Player
import omok.model.Point
import omok.model.Stone
import omok.model.Turn
import omok.model.WhiteStonePlayer
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val board = Board()
    private val whitePlayer = WhiteStonePlayer()
    private val blackPlayer = BlackStonePlayer()

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(board.customBoard)

        var turn = Turn(Color.BLACK)
        while (true) {
            if (turn.isBlack()) {
                val stone = putStone(blackPlayer, Color.BLACK)
                if (blackPlayer.checkContinuity(stone)) break
            } else {
                val stone = putStone(whitePlayer, Color.WHITE)
                if (whitePlayer.checkContinuity(stone)) break
            }
            turn.next()
        }
        outputView.showGameResult(turn)
    }

    private fun putStone(
        player: Player,
        turn: Color,
    ): Stone {
        val point = validPoint(turn)
        val stone = Stone(point = point, color = turn)
        player.add(stone)
        board.add(stone)
        printBoard()

        return stone
    }

    private fun validPoint(turn: Color): Point {
        return ExceptionHandler.handleInputValue {
            val point = readPoint(turn)
            require(board.pointEmpty(point)) { "중복된 위치입니다. 비워진 위치에 입력해주세요!" }
            if (turn == Color.BLACK) require(blackPlayer.isValidPoint(point)) { "룰 위반입니다. 해당 자리에는 수를 둘 수 없습니다. 다른 위치를 시도해주세요." }
            point
        }
    }

    private fun readPoint(turn: Color): Point {
        return ExceptionHandler.handleInputValue {
            inputView.getPointOrNull(turn, board.lastStone())
        } ?: readPoint(turn)
    }

    private fun printBoard() {
        outputView.showBoard(board.customBoard)
    }
}
