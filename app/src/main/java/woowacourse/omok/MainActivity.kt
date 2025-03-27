package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, positionView ->
                val rowIndex = abs(MAX_BOARD_INDEX - (index / BOARD_SIZE))
                val colIndex = index % BOARD_SIZE
                positionView.tag = Pair(rowIndex, colIndex)
                positionView.setOnClickListener {
                    Log.d("PositionView", "클릭 좌표 : ${positionView.tag}")
                }
            }
    }

    companion object {
        private const val BOARD_SIZE = 15
        private const val INDEX_OFFSET = 1
        private const val MAX_BOARD_INDEX = BOARD_SIZE - INDEX_OFFSET
    }
}
