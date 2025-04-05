package controller

import view.InputView
import view.ResultView
import woowacourse.omok.domain.GameBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.player.Player
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.rule.adapter.RuleAdapter
import woowacourse.omok.domain.rule.lib.OmokRule
import woowacourse.omok.domain.rule.lib.RenjuRule
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class OmokController(
    private val inputView: InputView,
    private val outputView: ResultView,
) {
    fun run() {
        outputView.printGameStartMessage()
        val gameStatus = omokGameStatus()
        val gameBoard = GameBoard()
        outputView.printGameBoard()
        putStoneUntilFindWinner(gameBoard, gameStatus)
        outputView.printWinner(gameStatus.turnColor())
    }

    private fun omokGameStatus(): OmokGame {
        val players = ArrayDeque<Player>()
        players.addAll(
            listOf(
                Player(
                    stoneColor = StoneColor.BLACK,
                    rules = listOf(RuleAdapter(RenjuRule())),
                ),
                Player(
                    stoneColor = StoneColor.WHITE,
                    rules = listOf(RuleAdapter(OmokRule())),
                ),
            ),
        )
        return OmokGame(players)
    }

    private tailrec fun putStoneUntilFindWinner(
        gameBoard: GameBoard,
        omokGameStatus: OmokGame,
    ) {
        putStoneProcess(
            gameBoard = gameBoard,
            omokGameStatus,
        )
        if (omokGameStatus.gameOver(gameBoard)) return
        omokGameStatus.changeTurn()
        putStoneUntilFindWinner(gameBoard, omokGameStatus)
    }

    private fun putStoneProcess(
        gameBoard: GameBoard,
        omokGameStatus: OmokGame,
    ) {
        val position =
            readPositionUntilSuccess(
                omokGameStatus.turnColor(),
                omokGameStatus.lastStone(gameBoard),
            )
        gameBoard.putStone(position, omokGameStatus)
            .onSuccess {
                outputView.printGameBoard(gameBoard.placedAllStones())
            }.onFailure { error ->
                outputView.printErrorMessage(error)
                putStoneProcess(gameBoard, omokGameStatus)
            }
    }

    private fun readPositionUntilSuccess(
        stoneColor: StoneColor,
        lastStone: Stone?,
    ): Position =
        runCatching {
            inputView.readPosition(stoneColor, lastStone)
        }.getOrElse { error ->
            outputView.printErrorMessage(error)
            readPositionUntilSuccess(stoneColor, lastStone)
        }
}
