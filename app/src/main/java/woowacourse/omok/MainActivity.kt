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
import woowacourse.omok.data.DbHelper
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.state.BlackTurn
import woowacourse.omok.domain.state.Finished
import woowacourse.omok.domain.state.Playing
import woowacourse.omok.domain.state.WhiteTurn
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DbHelper(this)
        val stones = dbHelper.queryStones()
        val omokBoard = OmokBoard(stones = stones)

        val state =
            when (dbHelper.queryLastStone()?.color) {
                StoneColor.BLACK -> WhiteTurn(omokBoard)
                StoneColor.WHITE, null -> BlackTurn(omokBoard)
            }
        val omokGame = OmokGame(omokBoard, state)
        setupBoardView(omokGame, stones)
    }

    private fun setupBoardView(
        omokGame: OmokGame,
        stones: OmokStones,
    ) {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .toList()
            .reversed()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, view ->
                        view.tag = Point(rowIndex, colIndex)
                        view.setOnClickListener {
                            if (omokGame.state is Playing) {
                                playGame(omokGame, view)
                            }
                        }
                    }
            }
        stones.stones.forEach { stone ->
            val view = board.findViewWithTag<ImageView>(stone.point)
            when (stone.color) {
                StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
                StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
            }
        }
    }

    private fun playGame(
        omokGame: OmokGame,
        view: ImageView,
    ) {
        omokGame.play(
            onTurn = { _, _ -> },
            onPointSelected = { view.tag as Point },
            onForbiddenMove = { message ->
                showToast(message)
            },
            onStonePlaced = { _, stone ->
                when (stone.color) {
                    StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
                    StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
                }
                dbHelper.insertStone(stone)
            },
        )
        if (omokGame.state is Finished) {
            omokGame.finish { stoneColor ->
                showToast(
                    when (stoneColor) {
                        StoneColor.BLACK -> "흑이 승리했습니다."
                        StoneColor.WHITE -> "백이 승리했습니다."
                        null -> "더 이상 돌을 놓을 곳이 없습니다."
                    },
                )
            }
            dbHelper.deleteStones()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
