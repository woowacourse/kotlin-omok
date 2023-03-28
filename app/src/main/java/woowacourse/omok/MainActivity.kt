package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
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

        omokGameController = OmokGameController(this, db, boardView)
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
