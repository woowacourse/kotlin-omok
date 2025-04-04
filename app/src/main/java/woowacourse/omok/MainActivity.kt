package woowacourse.omok

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.data.OmokDao
import woowacourse.omok.data.OmokDbHelper
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.PositionOccupiedException
import woowacourse.omok.domain.StoneType
import woowacourse.omok.domain.Turn
import woowacourse.omok.domain.TurnViolationException
import woowacourse.omok.library.BudoolRenjuRule

class MainActivity : AppCompatActivity() {
    private lateinit var dao: OmokDao
    private lateinit var board: Board
    private lateinit var turn: Turn
    private lateinit var renjuRule: BudoolRenjuRule

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = OmokDbHelper(this)
        dao = OmokDao(dbHelper)
        renjuRule = BudoolRenjuRule()
        initializeGame()

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    handleStonePlacement(view)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun handleStonePlacement(view: ImageView) {
        val position = getPositionFromView(view) ?: return

        try {
            turn.validate(turn.current)
            board.put(position, turn.current)
            updateStoneUI(view, turn.current)
            checkGameResult(position)
            turn.switch()
        } catch (e: PositionOccupiedException) {
            showToast(e.toString())
        } catch (e: TurnViolationException) {
            showToast(e.toString())
        }
    }

    private fun getPositionFromView(view: View): Position? {
        val tableRow = view.parent as? TableRow ?: return null
        val tableLayout = findViewById<TableLayout>(R.id.board)

        val row =
            (0 until tableLayout.childCount)
                .firstOrNull { index -> tableLayout.getChildAt(index) == tableRow }
                ?.plus(1)
                ?: return null

        val col =
            (0 until tableRow.childCount)
                .firstOrNull { index -> tableRow.getChildAt(index) == view }
                ?.plus(1)
                ?: return null

        return Position(row, col)
    }

    private fun updateStoneUI(
        view: ImageView,
        stoneType: StoneType,
    ) {
        view.setImageResource(
            when (stoneType) {
                StoneType.BLACK -> R.drawable.black_stone
                StoneType.WHITE -> R.drawable.white_stone
                else -> throw IllegalArgumentException()
            },
        )
    }

    private fun checkGameResult(position: Position) {
        when {
            renjuRule.checkWin(board, position) -> {
                showToast("${turn.current.name} 승리!")
            }
            renjuRule.checkOverline(board, position) -> {
                showToast("오버라인 금수")
                resetGame()
            }
            renjuRule.checkDoubleThreeFoul(board, position) -> {
                showToast("3-3 금수")
                resetGame()
            }
            renjuRule.checkDoubleFourFoul(board, position) -> {
                showToast("4-4 금수")
                resetGame()
            }
        }
    }

    private fun initializeGame() {
        val savedStones = dao.getAllStones()
        board = if (savedStones.isNotEmpty()) Board(savedStones) else Board.initial()
        turn =
            Turn().apply {
                currentPlayer = savedStones.lastOrNull()?.color ?: StoneType.BLACK
            }
        drawSavedStones()
    }

    private fun drawSavedStones() {
        board.stones.forEach { stone ->
            if (stone.color != StoneType.EMPTY) {
                findViewById<TableRow>(resources.getIdentifier("row_${stone.position.x - 1}", "id", packageName))
                    ?.findViewById<ImageView>(resources.getIdentifier("cell_${stone.position.y - 1}", "id", packageName))
                    ?.setImageResource(
                        when (stone.color) {
                            StoneType.BLACK -> R.drawable.black_stone
                            StoneType.WHITE -> R.drawable.white_stone
                            else -> 0
                        },
                    )
            }
        }
    }

    private fun resetGame() {
        dao.clearBoard()
        board = Board.initial()
        turn = Turn()
        clearBoardUI()
    }

    private fun clearBoardUI() {
        findViewById<TableLayout>(R.id.board)
            .children
            .flatMap { (it as TableRow).children }
            .filterIsInstance<ImageView>()
            .forEach { it.setImageResource(0) }
    }

    override fun onPause() {
        super.onPause()
        dao.clearBoard()
        board.stones.filter { it.color != StoneType.EMPTY }
            .forEach { dao.insertStone(it.position, it.color) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
