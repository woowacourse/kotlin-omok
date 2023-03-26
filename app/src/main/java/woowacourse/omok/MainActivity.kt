package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokBoard
import domain.State
import domain.Stone
import domain.listener.OmokListener
import woowacourse.omok.controller.OmokGameController

class MainActivity : AppCompatActivity() {
    private lateinit var omokGameController: OmokGameController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = OmokDbHelper(this)
        val board = findViewById<TableLayout>(R.id.board)
        val boardView = board.children.filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }.toList()

        val omokGameListener = object : OmokListener {
            override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
                when (state) {
                    State.BLACK -> omokGameController.setImageViewResource(State.BLACK, boardView[stone.row][stone.column])
                    State.WHITE -> omokGameController.setImageViewResource(State.WHITE, boardView[stone.row][stone.column])
                    State.EMPTY -> null
                }
            }

            override fun onMoveFail() {
                Toast.makeText(this@MainActivity, "이미 돌이 존재합니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onForbidden() {
                Toast.makeText(this@MainActivity, "그곳은 금수 입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(state: State): State {
                Toast.makeText(this@MainActivity, "${state.name}승!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, GameOverActivity::class.java)
                startActivity(intent)
                return state
            }
        }

        omokGameController = OmokGameController(omokGameListener, db, boardView)
        addImageViewListener(boardView)
    }

    private fun addImageViewListener(boardView: List<List<ImageView>>) {
        boardView.forEachIndexed { row, imageViews ->
            imageViews.forEachIndexed { column, imageView ->
                imageView.setOnClickListener {
                    omokGameController.move(row, column)
                }
            }
        }
    }
}
