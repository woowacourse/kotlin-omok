package controller

import Col
import GameBoard
import Row
import StoneColor
import rule.BlackRenjuRule
import rule.OmokRule
import view.InputView
import view.ResultView

class OmokController(
    private val inputView: InputView,
    private val outputView: ResultView,
) {
    private var turnColor: StoneColor = StoneColor.BLACK
    private var rule: OmokRule = BlackRenjuRule(Col.MAX_VALUE, Row.MAX_VALUE)

    fun run() {
        outputView.printGameStartMessage()
        val gameBoard = GameBoard()
        outputView.printGameBoard(emptyList())
        while (true) {
            putStoneProcess(
                gameBoard = gameBoard,
                rule = rule,
                showGameBoardStatus = { outputView.printGameBoard(gameBoard.blackStones + gameBoard.whiteStones) },
            )
            if (gameBoard.judge(rule)) break
            switchTurn()
        }
        outputView.printWinner(turnColor)
    }

    private fun putStoneProcess(
        gameBoard: GameBoard,
        rule: OmokRule,
        showGameBoardStatus: () -> Unit,
    ) {
        val flag =
            runCatching {
                val added =
                    gameBoard.putStone(
                        stoneColor = turnColor,
                        rule = rule,
                        onPositionReceived = { lastStone -> inputView.readInputPosition(turnColor, lastStone) },
                    )
                showGameBoardStatus()
                added
            }.onFailure { error ->
                outputView.printErrorMessage(error)
            }.getOrNull() ?: false
        if (!flag) putStoneProcess(gameBoard, rule, showGameBoardStatus)
    }

    private fun switchTurn() {
        turnColor = turnColor.switch()
        rule = rule.switch(Col.MAX_VALUE, Row.MAX_VALUE)
    }
}
