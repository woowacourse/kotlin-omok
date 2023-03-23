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
import omok.state.WhiteStoneState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val omokGame = OmokGame()
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val point = OmokPoint.from(index)
                    runCatching {
                        when (omokGame.play(point)) {
                            BlackStoneState -> view.setImageResource(R.drawable.black_stone)
                            WhiteStoneState -> view.setImageResource(R.drawable.white_stone)
                        }
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
}
