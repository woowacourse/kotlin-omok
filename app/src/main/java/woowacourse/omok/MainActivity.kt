package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.BlackStonePlayer
import omok.model.Color
import omok.model.Player
import omok.model.Point
import omok.model.Stone
import omok.model.Stones
import omok.model.WhiteStonePlayer

class MainActivity : AppCompatActivity() {
    private lateinit var stones: Stones
    private lateinit var blackStonePlayer: BlackStonePlayer
    private lateinit var whiteStonePlayer: WhiteStonePlayer
    private lateinit var currentPlayer: Player

    private lateinit var stoneDao: StoneDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initGameSetting()

        val board = findViewById<TableLayout>(R.id.board)
        val dbStones = stoneDao.stones()

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                restoreStones(view, index, dbStones)
                view.setOnClickListener { putStone(view, index) }
            }
    }

    private fun restoreStones(
        view: ImageView,
        index: Int,
        dbStones: List<Stone>,
    ) {
        val stone = dbStones.firstOrNull { it.point == index.toPoint() }
        if (stone != null) {
            view.setImageResource(if (stone.color == Color.BLACK) R.drawable.black_stone else R.drawable.white_stone)
        }
    }

    private fun initGameSetting() {
        stones = Stones()
        blackStonePlayer = BlackStonePlayer(stones)
        whiteStonePlayer = WhiteStonePlayer(stones)
        currentPlayer = blackStonePlayer
        stoneDao = StoneDao(this)
    }

    private fun putStone(
        view: ImageView,
        index: Int,
    ) {
        playerTurn(index.toPoint(), view)
        currentPlayer = currentPlayer.next()
    }

    private fun playerTurn(
        point: Point,
        view: ImageView,
    ) = runCatching {
        currentPlayer.add(point)
    }.onSuccess {
        putStone(view, point)
        showGameResult()
    }.onFailure {
        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
    }

    private fun putStone(
        view: ImageView,
        point: Point,
    ) {
        if (currentPlayer.color == Color.BLACK) {
            view.setImageResource(R.drawable.black_stone)
            stoneDao.insert(Stone(point, Color.BLACK))
        } else if (currentPlayer.color == Color.WHITE) {
            view.setImageResource(R.drawable.white_stone)
            stoneDao.insert(Stone(point, Color.WHITE))
        }
    }

    private fun showGameResult() {
        if (currentPlayer.isWin()) {
            setContentView(R.layout.activity_main)
            stones.clear()
            stoneDao.deleteAll()
            Snackbar.make(
                findViewById(R.id.board),
                "${if (currentPlayer.color == Color.WHITE) "백" else "흑"}의 승리입니다. 축하합니다.",
                Snackbar.LENGTH_SHORT,
            ).apply {
                anchorView = findViewById(R.id.iv_table_center)
            }.show()
        }
    }

    private fun Player.next(): Player {
        if (this.color == Color.BLACK) return whiteStonePlayer
        return blackStonePlayer
    }

    private fun Int.toPoint(): Point = Point(this % BOARD_SIZE, MAX_POINT_VALUE - this / BOARD_SIZE)

    companion object {
        private const val BOARD_SIZE = 15
        private const val MAX_POINT_VALUE = 14
    }
}
