package woowacourse.omok.controller

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.view.OutputView

class OMockGameController(private val context: Context) : OMockGame() {
    override fun executePlayerPickFailStep(throwable: Throwable) {
        OutputView.outputFailureMessage(throwable)
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
    }

    override fun executePlayerSuccessStep(playerStone: Stone, player: Player) {
        setLastPickStone(player)
    }

    private var lastPickImage: ImageView? = null

    fun setLastPickImage(imageView: ImageView) {
        lastPickImage = imageView
    }

    fun userTurnFlow(
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

    fun finishedGameFlow() {
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
