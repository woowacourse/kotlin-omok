package omock.controller

import omock.model.GameTurn
import omock.model.board.Board
import omock.model.board.LocalBoard
import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.rule.LoadMap
import omock.model.rule.OMockRule
import omock.model.ruletype.RuleType
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone
import omock.view.InputView.playerPick
import omock.view.OutputView.outputBoardForm
import omock.view.OutputView.outputFailureMessage
import omock.view.OutputView.outputGameStart
import omock.view.OutputView.outputLastStone
import omock.view.OutputView.outputSuccessOMock
import omock.view.OutputView.outputUserTurn

class OMokController(ruleTypes: List<RuleType>) {
    private val board = Board.from()
    private val oMockRule = OMockRule(ruleTypes = ruleTypes)
    private val loadMap = LoadMap(board.stoneStates)

    fun run() {
        outputGameStart()
        val blackPlayer = BlackPlayer()
        val whitePlayer = WhitePlayer()

        while (board.getTurn() != GameTurn.FINISHED) {
            outputBoardForm()
            when (board.getTurn()) {
                GameTurn.BLACK_TURN -> userTurnFlow(blackPlayer)
                GameTurn.WHITE_TURN -> userTurnFlow(whitePlayer)
                GameTurn.FINISHED -> outputSuccessOMock()
            }
        }
    }

    private fun userTurnFlow(player: Player) {
        outputUserTurn(player)
        outputLastStone(player.stoneHistory.lastOrNull())
        start(player = player)
    }

    private fun start(player: Player) {
        playerPick(player = player).onSuccess { playerStone ->
            playerTurn(player, playerStone).onSuccess {
                executePlayerSuccessStep(playerStone, player)
            }.onFailure {
                executePlayerTurnFailStep(playerStone, it)
            }
        }.onFailure {
            executePlayerPickFailStep(it)
        }
    }

    private fun executePlayerSuccessStep(
        playerStone: Stone,
        player: Player,
    ) {
        LocalBoard.setBoardIcon(playerStone, player)
        player.stoneHistory.add(playerStone)
    }

    private fun executePlayerTurnFailStep(
        playerStone: Stone,
        throwable: Throwable,
    ) {
        board.rollbackState(playerStone)
        outputFailureMessage(throwable)
    }

    private fun executePlayerPickFailStep(throwable: Throwable) {
        outputFailureMessage(throwable)
    }

    private fun playerTurn(
        player: Player,
        playerStone: Stone,
    ): Result<Unit> {
        return runCatching {
            board.setStoneState(player, playerStone)
            val visitedDirectionResult = VisitedDirectionResult(loadMap.loadMap(playerStone))
            val visitedDirectionFirstClearResult =
                VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(playerStone))
            oMockRule.checkPlayerRules(player, visitedDirectionResult, visitedDirectionFirstClearResult)
            board.applyPlayerJudgement(player, visitedDirectionResult)
        }
    }
}
