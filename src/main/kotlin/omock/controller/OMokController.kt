package omock.controller

import omock.model.GameTurn
import omock.model.board.Board
import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.rule.OMockRule
import omock.model.search.Direction
import omock.model.search.DirectionResult
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
    private var gameTurn = GameTurn.BLACK_TURN

    fun run() {
        outputGameStart()
        val blackPlayer = BlackPlayer()
        val whitePlayer = WhitePlayer()

        while (true) {
            outputBoardForm()
            when (gameTurn) {
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
            val visited = board.loadMap(playerStone)
            checkPlayerRules(player, visited, playerStone)
            applyPlayerJudgement(player, visited)
        }
    }

    private fun checkPlayerRules(
        player: Player,
        visited: Map<Direction, DirectionResult>,
        playerStone: Stone
    ) {
        val visitedFirstClear = board.firstClearLoadMap(playerStone)
        if (player is BlackPlayer) {
            oMockRule.checkRules(visited, visitedFirstClear)
        }
    }

    private fun applyPlayerJudgement(
        player: Player,
        visited: Map<Direction, DirectionResult>,
    ) {
        gameTurn =
            when (player.judgementResult(visited)) {
                true -> GameTurn.FINISHED
                false -> gameTurn.turnOff()
            }
    }
}
