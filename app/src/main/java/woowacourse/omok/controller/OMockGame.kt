package woowacourse.omok.controller

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
    private val oMockRule: OMockRule = OMockRule(
        ruleTypes = listOf(
            ThreeToThreeCount,
            FourToFourCount,
            IsClearFourToFourCount,
            IsReverseTwoAndThree,
        )
    )
) {
    protected val board: Board = Board.from()
    private val loadMap: LoadMap = LoadMap(board.stoneStates)

    abstract fun executePlayerPickFailStep(throwable: Throwable)
    abstract fun executePlayerSuccessStep(playerStone: Stone, player: Player)

    fun startGameBoard() {
        OutputView.outputGameStart()
    }

    fun start(
        player: Player,
        playerStone: Stone,
    ) {
        playerPutStone(player, playerStone)
            .onSuccess {
                applyPlayerSelected(player, playerStone).onSuccess {
                    executePlayerSuccessStep(playerStone, player)
                }.onFailure {
                    executePlayerTurnFailStep(playerStone, it)
                }
            }
            .onFailure {
                executePlayerPickFailStep(it)
            }
    }

    private fun playerPutStone(
        player: Player,
        playerStone: Stone,
    ): Result<Unit> {
        return runCatching {
            board.setStoneState(player, playerStone)
        }
    }

    private fun applyPlayerSelected(
        player: Player,
        playerStone: Stone,
    ): Result<Unit> {
        return runCatching {
            val visitedDirectionResult = VisitedDirectionResult(loadMap.loadMap(playerStone))
            val visitedDirectionFirstClearResult =
                VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(playerStone))
            oMockRule.checkPlayerRules(
                player,
                visitedDirectionResult,
                visitedDirectionFirstClearResult
            )
            board.applyPlayerJudgement(player, visitedDirectionResult)
        }
    }

    private fun executePlayerTurnFailStep(
        playerStone: Stone,
        throwable: Throwable,
    ) {
        board.rollbackState(playerStone)
        executePlayerPickFailStep(throwable)
    }
}
