package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.controller.OMockGameController
import woowacourse.omok.model.GameTurn
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.view.OutputView

class MainActivity : AppCompatActivity() {
    private val oMockGameController = OMockGameController(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainBoard = findViewById<TableLayout>(R.id.board)
        val blackPlayer = BlackPlayer()
        val whitePlayer = WhitePlayer()

        mainBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columIndex, view ->
                        view.setOnClickListener {
                            oMockGameController.setLastPickImage(view)
                            OutputView.outputBoardForm()
                            val playerPick =
                                Column.transformIndex(columIndex) to Row.transformIndex(rowIndex)
                            when (oMockGameController.board.getTurn()) {
                                GameTurn.BLACK_TURN -> oMockGameController.userTurnFlow(blackPlayer, playerPick)
                                GameTurn.WHITE_TURN -> oMockGameController.userTurnFlow(whitePlayer, playerPick)
                                GameTurn.FINISHED -> oMockGameController.finishedGameFlow()
                            }
                        }
                    }
            }
    }
}
