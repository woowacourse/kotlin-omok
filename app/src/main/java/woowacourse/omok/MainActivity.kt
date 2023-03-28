package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.OmokGame
import omok.OmokPoint
import omok.gameState.BlackWin
import omok.gameState.WhiteWin
import omok.state.BlackStoneState
import omok.state.EmptyStoneState
import omok.state.StoneState
import omok.state.WhiteStoneState
import woowacourse.omok.db.OmokDB

class MainActivity : AppCompatActivity() {
    private val omokGame: OmokGame by lazy {
        OmokGame(db.getBoard())
    }
    private val board: TableLayout by lazy {
        findViewById(R.id.board)
    }
    private val tvStatus: TextView by lazy {
        findViewById(R.id.tv_status)
    }
    private val btnReset: Button by lazy {
        findViewById(R.id.btn_reset)
    }
    private val boardView: List<List<ImageView>> by lazy {
        board.children
            .filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }.toList()
    }
    private val db: OmokDB by lazy {
        OmokDB(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        btnReset.setOnClickListener {
            db.clearStoneInfo()
            changeActivity(WaitingRoomActivity::class.java)
        }

        boardView.forEachIndexed { row, images ->
            images.forEachIndexed { col, view ->
                view.setOnClickListener {
                    omokEventListener(OmokPoint(row + 1, col + 1), view)
                }
            }
        }
    }

    private fun initView() {
        omokGame.gameState.omokBoard.value
            .asSequence()
            .filter { it.value != EmptyStoneState }
            .forEach {
                initBoardView(it.key, it.value)
            }
    }

    private fun initBoardView(omokPoint: OmokPoint, stoneState: StoneState) {
        boardView.forEachIndexed { row, images ->
            images.forEachIndexed { col, view ->
                if (omokPoint == OmokPoint(row + 1, col + 1)) {
                    setViewToPlaceStone(stoneState, view, OmokPoint(row + 1, col + 1))
                }
            }
        }
    }

    private fun omokEventListener(point: OmokPoint, view: ImageView) {
        runningGame(point)?.let {
            saveData(point, it)
            setViewToPlaceStone(it, view, point)
            when (omokGame.gameState) {
                is BlackWin -> tvStatus.text = "흑돌이 이겼습니다"
                is WhiteWin -> tvStatus.text = "흰돌이 이겼습니다"
            }
        }
    }

    private fun setViewToPlaceStone(stoneState: StoneState, view: ImageView, point: OmokPoint) {
        determineImageView(stoneState, view)
        tvStatus.text = "마지막 돌의 위치 (${point.x}, ${point.y})"
    }

    private fun runningGame(point: OmokPoint): StoneState? =
        runCatching {
            omokGame.play(point)
        }
            .onFailure {
                tvStatus.text = it.message.toString()
                Log.e("ERROR", it.toString())
            }
            .getOrNull()

    private fun saveData(point: OmokPoint, stoneState: StoneState) {
        when (stoneState) {
            BlackStoneState -> db.recordStoneInfo(point, 0)
            WhiteStoneState -> db.recordStoneInfo(point, 1)
        }
    }

    private fun <T> changeActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        finish()
        startActivity(intent)
    }

    private fun determineImageView(stoneState: StoneState, view: ImageView) {
        when (stoneState) {
            BlackStoneState -> view.setImageResource(R.drawable.black_stone)
            WhiteStoneState -> view.setImageResource(R.drawable.white_stone)
        }
    }
}
