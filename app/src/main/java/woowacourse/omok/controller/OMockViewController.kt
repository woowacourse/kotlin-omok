package woowacourse.omok.controller

import android.content.Context
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.core.view.children
import woowacourse.omok.model.GameState
import woowacourse.omok.model.GameTurn
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.view.OutputView

class OMockViewController : OMockGame() {
    private var lastPickImage: ImageView? = null
    private val blackPlayer = BlackPlayer()
    private val whitePlayer = WhitePlayer()

    override fun executePlayerSuccessStep(
        playerStone: Stone,
        player: Player,
    ) {
        setLastPickStone(player)
    }

    fun setBoard(
        view: ImageView,
        coordinate: Pair<Int, Int>,
    ): GameState {
        val playerPick =
            Column.transformIndex(coordinate.first) to Row.transformIndex(coordinate.second)
        setLastPickImage(view)
        OutputView.outputBoardForm()
        return processUserPick(playerPick)
    }

    private fun processUserPick(playerPick: Pair<String, String>): GameState {
        return when (board.getTurn()) {
            GameTurn.BLACK_TURN -> userPickStateFlow(blackPlayer,userTurnFlow(blackPlayer, playerPick))

            GameTurn.WHITE_TURN -> userPickStateFlow(whitePlayer,userTurnFlow(whitePlayer, playerPick))

            GameTurn.FINISHED -> GameState.Finish
        }
    }

    private fun userPickStateFlow(
        player: Player,
        gameState: GameState.LoadStone,
    ): GameState {
        return when (gameState) {
            is GameState.LoadStone.Success -> {
                println("GameState.LoadStone.Success")
                return start(player,gameState.stone)
            }

            is GameState.LoadStone.Failure -> {
                println("GameState.LoadStone.Failure")
                gameState
            }
        }
    }

    private fun setLastPickImage(imageView: ImageView) {
        lastPickImage = imageView
    }

    fun resetBoard(mainBoard: TableLayout) {
        mainBoard
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setImageResource(0) }
        loadNewBoard()
    }

    private fun userTurnFlow(
        player: Player,
        playerPick: Pair<String, String>,
    ): GameState.LoadStone {
        OutputView.outputUserTurn(player)
        OutputView.outputLastStone(player.stoneHistory.lastOrNull())
        return player.turn {
            playerPick
        }
    }

    fun finishedGameFlow(context: Context) {
        OutputView.outputSuccessOMock()
        Toast.makeText(context, OutputView.OUTPUT_SUCCESS_MESSAGE, Toast.LENGTH_LONG).show()
    }

    private fun setLastPickStone(player: Player) {
        when (player) {
            is BlackPlayer -> lastPickImage?.setImageResource(Stone.BLACK_STONE_RESOURCE)
            is WhitePlayer -> lastPickImage?.setImageResource(Stone.WHITE_STONE_RESOURCE)
        }
    }
}
