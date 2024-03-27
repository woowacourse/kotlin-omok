package omock.controller

import omock.model.GameTurn
import omock.model.board.Board
import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.rule.LoadMap
import omock.model.rule.OMockRule
import omock.model.search.VisitedDirectionFirstClearResult
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone
import omock.view.InputView.playerPick
import omock.view.OutputView.boardTable
import omock.view.OutputView.outputBoardForm
import omock.view.OutputView.outputFailureMessage
import omock.view.OutputView.outputGameStart
import omock.view.OutputView.outputLastStone
import omock.view.OutputView.outputSuccessOMock
import omock.view.OutputView.outputUserTurn

class OMokController {
    private val board = Board.from()
    private val oMockRule = OMockRule()
    private val loadMap = LoadMap(board.stoneStates)

    fun run() {
        outputGameStart()
        val blackPlayer = BlackPlayer()
        val whitePlayer = WhitePlayer()

        while (true) {
            outputBoardForm()
            when (board.getTurn()) {
                GameTurn.BLACK_TURN -> userTurnFlow(blackPlayer)
                GameTurn.WHITE_TURN -> userTurnFlow(whitePlayer)
                GameTurn.FINISHED -> outputSuccessOMock()
            }
        }
    }

    private fun userTurnFlow(player: Player) {
        outputUserTurn(Stone.getStoneName(player))
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
        boardTable[playerStone.row.toIntIndex() - 1][playerStone.column.getIndex()] =
            Stone.getStoneIcon(player)
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
            val visitedDirectionFirstClearResult = VisitedDirectionFirstClearResult(loadMap.firstClearLoadMap(playerStone))
            oMockRule.checkPlayerRules(player, visitedDirectionResult, visitedDirectionFirstClearResult)
            board.applyPlayerJudgement(player, visitedDirectionResult)
        }
    }
}
