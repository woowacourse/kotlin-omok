package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import omok.OmokGame
import omok.OmokPoint
import omok.state.BlackStoneState
import omok.state.StoneState
import woowacourse.omok.db.OmokDB

class MainActivity : AppCompatActivity() {
    private val omokStatusView: OmokStatusView by lazy {
        OmokStatusView(findViewById(R.id.tv_status))
    }
    private val omokBoardView: BoardView by lazy {
        BoardView(board, omokGame)
    }
    private val omokGame: OmokGame by lazy {
        OmokGame(db.getBoard(), ::onSuccessProcess, omokStatusView::onFailedProcess)
    }
    private val board: TableLayout by lazy {
        findViewById(R.id.board)
    }
    private val btnReset: Button by lazy {
        findViewById(R.id.btn_reset)
    }
    private val db: OmokDB by lazy {
        OmokDB(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokBoardView.boardView.forEachIndexed { row, images ->
            images.forEachIndexed { col, view ->
                view.setOnClickListener {
                    val point = OmokPoint(row + 1, col + 1)
                    val state = omokGame.play(point)
                    if (omokGame.gameState !== state) {
                        db.recordStoneInfo(point, determineStoneColor(state.stoneState))
                    }
                }
            }
        }
        btnReset.setOnClickListener {
            db.clearStoneInfo()
            changeActivity(WaitingRoomActivity::class.java)
        }
    }

    private fun <T> changeActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        finish()
        startActivity(intent)
    }

    private fun onSuccessProcess(point: OmokPoint, stoneState: StoneState) {
        omokStatusView.onSuccessProcess(point, stoneState)
        omokBoardView.onSuccessProcess(point, stoneState)
    }

    private fun determineStoneColor(stoneState: StoneState): Int {
        return when (stoneState) {
            BlackStoneState -> 0
            else -> 1
        }
    }
}
