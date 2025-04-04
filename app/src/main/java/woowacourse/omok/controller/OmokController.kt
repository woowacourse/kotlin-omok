package woowacourse.omok.controller

import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.OmokResult
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.Stone
import woowacourse.omok.domain.rule.ValidationResult
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val omokGame: OmokGame,
) {
    fun play() {
        initGame()
        val result = playGame(null, omokGame.getStartingPlayer())
        endGame(result)
    }

    private fun initGame() {
        outputView.printStartMessage()
    }

    // 게임을 진행한다
    private tailrec fun playGame(
        latestPoint: Stone?,
        nowTurn: StoneColor,
    ): OmokResult {
        val thisTurnPoint = playTurn(nowTurn, latestPoint)
        if (omokGame.checkWin(nowTurn, thisTurnPoint)) return OmokResult.getWinner(nowTurn)
        if (omokGame.isBoardFull()) return OmokResult.DRAW
        return playGame(thisTurnPoint, omokGame.changeTurn(nowTurn))
    }

    // 플레이어의 한 턴을 처리한다
    private fun playTurn(
        nowTurn: StoneColor,
        latestPoint: Stone?,
    ): Stone {
        outputView.printBoardState(omokGame.grid.getStonesByColor(StoneColor.BLACK), omokGame.grid.getStonesByColor(StoneColor.WHITE))
        val point = getPointToPlace(nowTurn, latestPoint)
        omokGame.playMove(point)
        return point
    }

    // 착수할 위치를 입력 받는다
    private fun getPointToPlace(
        nowTurn: StoneColor,
        latestPoint: Stone?,
    ): Stone {
        while (true) {
            val point = inputView.getPoint(nowTurn, latestPoint)
            val omokPoint = Stone(point, nowTurn)
            val violation = omokGame.validatePoint(nowTurn, omokPoint)
            if (violation == ValidationResult.Success) return omokPoint
            outputView.printErrorMessage(violation as ValidationResult.Failure)
        }
    }

    private fun endGame(omokResult: OmokResult) {
        outputView.printBoardState(omokGame.grid.getStonesByColor(StoneColor.BLACK), omokGame.grid.getStonesByColor(StoneColor.WHITE))
        outputView.printWinner(omokResult)
    }
}
