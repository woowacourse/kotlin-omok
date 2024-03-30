package woowacourse.omok.controller

import android.content.Context
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.core.view.children
import woowacourse.omok.model.GameTurn
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.view.OutputView

class OMockViewController(private val context: Context) : OMockGame() {
    private var lastPickImage: ImageView? = null
    private val blackPlayer = BlackPlayer()
    private val whitePlayer = WhitePlayer()

    override fun executePlayerPickFailStep(throwable: Throwable) {
        OutputView.outputFailureMessage(throwable)
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
    }

    override fun executePlayerSuccessStep(playerStone: Stone, player: Player) {
        setLastPickStone(player)
    }

    fun setBoard(
        view: ImageView,
        coordinate: Pair<Int, Int>,
    ) {
        val playerPick =
            Column.transformIndex(coordinate.first) to Row.transformIndex(coordinate.second)
        setLastPickImage(view)
        OutputView.outputBoardForm()
        processUserPick(playerPick)
    }

    private fun processUserPick(playerPick: Pair<String, String>) {
        when (board.getTurn()) {
            GameTurn.BLACK_TURN -> userTurnFlow(blackPlayer, playerPick)
            GameTurn.WHITE_TURN -> userTurnFlow(whitePlayer, playerPick)
            GameTurn.FINISHED -> finishedGameFlow()
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
    }

    private fun userTurnFlow(
        player: Player,
        playerPick: Pair<String, String>,
    ) {
        OutputView.outputUserTurn(player)
        OutputView.outputLastStone(player.stoneHistory.lastOrNull())
        val playerStone = player.turn {
            playerPick
        }
        start(player = player, playerStone)
    }

    private fun finishedGameFlow() {
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
