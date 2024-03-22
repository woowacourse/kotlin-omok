package omock.controller

import omock.model.BlackPlayer
import omock.model.Board
import omock.model.GameTurn
import omock.model.Player
import omock.model.Stone
import omock.model.WhitePlayer
import omock.view.InputView.playerPick
import omock.view.OutputView.boardForm
import omock.view.OutputView.boardTable
import omock.view.OutputView.outputGameStart
import omock.view.OutputView.outputLastStone
import omock.view.OutputView.outputPrintLine
import omock.view.OutputView.outputSuccessOMock
import omock.view.OutputView.outputUserTurn

class OMokController {
    private val board = Board.from()
    private var gameTurn = GameTurn.BLACK_TURN

    fun run() {
        outputGameStart()
        boardForm.forEachIndexed { index, s ->
            if (index == boardForm.size - 1) {
                println(s)
            } else {
                println(s.format(*boardTable[index].toTypedArray()))
            }
        }

        val blackPlayer = BlackPlayer()
        val whitePlayer = WhitePlayer()

        while (true) {
            when (gameTurn) {
                GameTurn.BLACK_TURN -> {
                    outputUserTurn(Stone.getStoneName(blackPlayer))
                    whitePlayer.stoneHistory.lastOrNull()?.let { stone ->
                        outputLastStone(stone)
                    } ?: outputPrintLine()
                    start(player = blackPlayer)
                }

                GameTurn.WHITE_TURN -> {
                    outputUserTurn(Stone.getStoneName(whitePlayer))
                    blackPlayer.stoneHistory.lastOrNull()?.let { stone ->
                        outputLastStone(stone)
                    } ?: outputPrintLine()
                    start(player = whitePlayer)
                }

                GameTurn.FINISHED -> {
                    outputSuccessOMock()
                }
            }

            boardForm.forEachIndexed { index, s ->
                if (index == boardForm.size - 1) {
                    println(s)
                } else {
                    println(s.format(*boardTable[index].toTypedArray()))
                }
            }
        }
    }

    private fun start(player: Player) {
        playerPick(player = player).onSuccess { playerStone ->
            playerTurn(player, playerStone).onSuccess {
                boardTable[playerStone.row.toIntIndex() - 1][playerStone.column.getIndex()] =
                    Stone.getStoneIcon(player)
                player.stoneHistory.add(playerStone)

            }.onFailure {
                board.rollbackState(playerStone)
                println(it.message)
            }
        }.onFailure {
            println(it.message)
        }
    }


    private fun playerTurn(player: Player, playerStone: Stone): Result<Unit> {
        return runCatching {
            board.setStoneState(player, playerStone)
            val visited = board.loadMap(playerStone)

            gameTurn = when (player.judgementResult(visited)) {
                true -> GameTurn.FINISHED
                false -> gameTurn.turnOff()
            }
        }
    }
}
