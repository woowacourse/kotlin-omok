package woowacourse.omok.controller

import android.content.Context
import android.widget.Toast
import woowacourse.omok.model.GameState
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.rule.LoadMap
import woowacourse.omok.model.rule.OMockRule
import woowacourse.omok.model.ruletype.FourToFourCount
import woowacourse.omok.model.ruletype.IsClearFourToFourCount
import woowacourse.omok.model.ruletype.IsReverseTwoAndThree
import woowacourse.omok.model.ruletype.ThreeToThreeCount
import woowacourse.omok.model.search.VisitedDirectionFirstClearResult
import woowacourse.omok.model.search.VisitedDirectionResult
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.view.OutputView

abstract class OMockGame(
    private val oMockRule: OMockRule =
        OMockRule(
            ruleTypes =
            listOf(
                ThreeToThreeCount,
                FourToFourCount,
                IsClearFourToFourCount,
                IsReverseTwoAndThree,
            ),
        ),
) {
    protected var board: Board = Board.from()
    private var loadMap: LoadMap = LoadMap(board.stoneStates)

    fun executePlayerPickFailStep(throwable: Throwable) {
        OutputView.outputFailureMessage(throwable)
    }

    fun showToastMessage(
        context: Context,
        throwable: Throwable,
    ) {
        executePlayerPickFailStep(throwable)
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
    }

    abstract fun executePlayerSuccessStep(
        playerStone: Stone,
        player: Player,
    )

    fun startGameBoard() {
        OutputView.outputGameStart()
    }

    fun loadNewBoard() {
        board = Board.from()
        loadMap = LoadMap(board.stoneStates)
    }

    fun playerPutStone(
        player: Player,
        playerStone: Stone,
    ): GameState.LoadStoneState {
        return board.setStoneState(player, playerStone)
    }

    fun start(
        player: Player,
        playerStone: Stone,
    ): GameState {
        return when (val playerPutResult = playerPutStone(player, playerStone)) {
            is GameState.LoadStoneState.Success -> applyPlayerSelected(player, playerStone)
            is GameState.LoadStoneState.Failure -> {
                executePlayerPickFailStep(playerPutResult.throwable)
                playerPutResult
            }
        }
    }

    fun applyPlayerSelected(
        player: Player,
        playerStone: Stone,
    ): GameState {
        val visitedDirectionResult = VisitedDirectionResult(loadMap.loadMap(playerStone))
        val visitedDirectionFirstClearResult =
            VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(playerStone))
        val checkRulesResult =
            oMockRule.checkPlayerRules(
                player,
                visitedDirectionResult,
                visitedDirectionFirstClearResult,
            )
        when (checkRulesResult) {
            is GameState.CheckRuleTypeState.Success -> {
                println("GameState.CheckRuleTypeState.Success")
                board.applyPlayerJudgement(player, visitedDirectionResult)
                executePlayerSuccessStep(playerStone, player)
            }

            is GameState.CheckRuleTypeState.Failure -> {
                println("GameState.CheckRuleTypeState.Failure")
                executePlayerTurnFailStep(playerStone, checkRulesResult.throwable)
            }
        }
        return checkRulesResult
    }

    private fun executePlayerTurnFailStep(
        playerStone: Stone,
        throwable: Throwable,
    ) {
        board.rollbackState(playerStone)
        executePlayerPickFailStep(throwable)
    }
}
