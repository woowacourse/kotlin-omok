package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.OmokGame
import omok.OmokPoint
import omok.state.BlackStoneState
import omok.state.StoneState
import omok.state.WhiteStoneState
import woowacourse.omok.db.OmokDBHelper

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame()
    private lateinit var board: TableLayout
    private lateinit var dbHelper: OmokDBHelper
    private lateinit var boardView: Sequence<ImageView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById(R.id.board)
        boardView = board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()

        dbHelper = OmokDBHelper(this)
        initData()

        boardView.forEachIndexed { index, view ->
            view.setOnClickListener {
                val point = OmokPoint.from(index)
                runCatching {
                    var color: Int? = null
                    when (omokGame.play(point)) {
                        BlackStoneState -> {
                            color = 0
                            view.setImageResource(R.drawable.black_stone)
                        }
                        WhiteStoneState -> {
                            color = 1
                            view.setImageResource(R.drawable.white_stone)
                        }
                    }
                    dbHelper.insertData(point, color!!)
                    if (!omokGame.gameState.isRunning) {
                        Toast.makeText(this, "끝", Toast.LENGTH_SHORT).show()
                    }
                }
                    .onFailure {
                        Log.e("ERROR", it.toString())
                    }
            }
        }
    }

    private fun initData() {
        val dbHelper = OmokDBHelper(this)
        val omokBoard = dbHelper.selectAll()

        omokBoard?.let {
            it.value.forEach { stone ->
                Log.i("stone Info", "point: ${stone.key}, state: ${stone.value}")
                omokGame.play(stone.key)
                eventListener(stone.key, stone.value)
            }
        }
    }

    private fun eventListener(omokPoint: OmokPoint, stoneState: StoneState) {
        boardView.forEachIndexed { index, view ->
            val point = OmokPoint.from(index)
            if (omokPoint == point) {
                when (stoneState) {
                    BlackStoneState -> view.setImageResource(R.drawable.black_stone)
                    WhiteStoneState -> view.setImageResource(R.drawable.white_stone)
                }
            }
        }
    }
}
