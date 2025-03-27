package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import domain.domain.Point
import domain.domain.state.BlackTurn
import domain.domain.state.Finished
import domain.domain.state.Playing
import domain.domain.state.Ready
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import domain.domain.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private var state: State = Ready()

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
        val rows = board.children.filterIsInstance<TableRow>().toList().reversed()

        rows.forEachIndexed { rowIndex, row ->
            row.children
                .filterIsInstance<ImageView>()
                .forEachIndexed { colIndex, imageView ->
                    imageView.setOnClickListener {
                        val currentState = state
                        val point = Point(colIndex, rowIndex)

                        placeStone(point, imageView, currentState)
                    }
                }
        }
    }

    private fun placeStone(
        point: Point,
        imageView: ImageView,
        currentState: State,
    ) {
        if (currentState !is Playing) return

        runCatching {
            state =
                currentState.place(
                    point,
                    15,
                    onBoardUpdated = { newBlackStones, newWhiteStones ->
                        if (point in newBlackStones) currentState.blackStones + point
                        if (point in newWhiteStones) currentState.whiteStones + point

                        displayStone(state, imageView)
                    },
                )
        }.onFailure {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
        }
        val updatedState = state

        if (updatedState is Finished.Win) {
            displayWinner(updatedState.winnerColor)
        }
    }

    private fun displayStone(
        state: State,
        imageView: ImageView,
    ) {
        when (state) {
            is BlackTurn -> imageView.setImageResource(R.drawable.black_stone)
            is WhiteTurn -> imageView.setImageResource(R.drawable.white_stone)
            else -> imageView.setImageResource(R.drawable.black_stone)
        }
    }

    private fun displayWinner(stoneColor: StoneColor) {
        when (stoneColor) {
            StoneColor.BLACK -> Toast.makeText(applicationContext, "흑돌이 우승했습니다!", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(applicationContext, "백돌이 우승했습니다!", Toast.LENGTH_LONG).show()
        }
    }
}
