package omok.controller

import omok.ExceptionHandler
import omok.model.Board
import omok.model.Point
import omok.model.RenjuRuleAdapter
import omok.model.Stone
import omok.model.Turn
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val board = Board(15, RenjuRuleAdapter())

    fun start() {
        outputView.showGameStartHeader()

        val turn = Turn()
        while (true) {
            val stone = putStone(turn)
            if (board.checkContinuity(stone)) break
            turn.next()
        }
        outputView.showBoard(customBoard = board.customBoard)
        outputView.showGameResult(turn)
    }

    private fun putStone(turn: Turn): Stone {
        val point = validPoint(turn)
        val stone = Stone(point = point, color = turn.color())
        board.add(stone)

        return stone
    }

    private fun validPoint(turn: Turn): Point {
        return ExceptionHandler.handleInputValue {
            val point = readPoint(turn)
            require(board.pointEmpty(point)) { "중복된 위치입니다. 비워진 위치에 입력해주세요!" }
            require(board.isValidPoint(turn, point)) { "해당 자리에는 수를 둘 수 없습니다. 다른 위치를 시도해주세요." }
            point
        }
    }

    private fun readPoint(turn: Turn): Point {
        return ExceptionHandler.handleInputValue {
            printBoard()
            inputView.getPointOrNull(turn.color(), board)
        }
    }

    private fun printBoard() {
        outputView.showBoard(board.customBoard)
    }
}
