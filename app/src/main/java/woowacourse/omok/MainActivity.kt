package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val boardView = makeBoardView()
        initBoardView(boardView)
    }

    private fun makeBoardView() =
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()

    private fun initBoardView(boardView: Sequence<ImageView>) {
        boardView.forEachIndexed { index, view ->
            view.setOnClickListener {
                val position = indexToPosition(index)
                Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show()
                view.setImageResource(R.drawable.black_stone)
            }
        }
    }
}
