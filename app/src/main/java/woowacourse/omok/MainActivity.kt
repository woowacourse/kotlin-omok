package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.board.PlaceStoneResult

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBoard()
    }

    private fun initBoard() {
        omokGame = OmokGame()
        setupBoardUI()
    }

    private fun setupBoardUI() {
        val boardView = findViewById<TableLayout>(R.id.board)
        boardView.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, imageView ->
                        imageView.setImageDrawable(null)
                        imageView.setOnClickListener {
                            playWithTurn(imageView, rowIndex + 1, colIndex + 1)
                        }
                    }
            }
    }

    private fun playWithTurn(
        view: ImageView,
        x: Int,
        y: Int,
    ) {
        val currentTurn = omokGame.currentStoneColor
        val result = omokGame.placeStone(x, y)

        when (result) {
            is PlaceStoneResult.Success -> changeBoardState(view, currentTurn)
            is PlaceStoneResult.Omok -> handleGameWin(currentTurn)
            is PlaceStoneResult.AlreadyPlaced -> showToast("중복되는 칸에 돌을 둘 수 없습니다.")
            is PlaceStoneResult.ForbiddenMove -> showToast("둘 수 없는 자리입니다.")
        }
    }

    private fun changeBoardState(
        view: ImageView,
        turn: StoneColor,
    ) {
        when (turn) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun handleGameWin(turn: StoneColor) {
        AlertDialog
            .Builder(this)
            .setTitle("게임 종료")
            .setMessage("${turn}가 우승했습니다")
            .setPositiveButton("다시 시작") { _, _ ->
                resetGame()
            }.setNegativeButton("종료") { _, _ ->
                disableBoardTouch()
            }.setCancelable(false)
            .show()
    }

    private fun resetGame() {
        omokGame.resetGame()
        setupBoardUI()
    }

    private fun disableBoardTouch() {
        val boardView = findViewById<TableLayout>(R.id.board)
        boardView.children
            .filterIsInstance<TableRow>()
            .forEach { row ->
                row.children.filterIsInstance<ImageView>().forEach { imageView ->
                    imageView.setOnClickListener(null)
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
