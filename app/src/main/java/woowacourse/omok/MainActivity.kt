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
import omok.mapper.BlackRuleChecker
import omok.model.game.Game
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.OutputView
import rule.BlackRenjuRule
import rule.wrapper.point.Point
import woowacourse.omok.data.OmokDao
import woowacourse.omok.model.rule.PlacementError
import woowacourse.omok.model.rule.PlacementError.AlreadyOccupiedViolation
import woowacourse.omok.model.rule.PlacementError.DoubleFourViolation
import woowacourse.omok.model.rule.PlacementError.DoubleThreeViolation
import woowacourse.omok.model.rule.PlacementError.NoViolation
import woowacourse.omok.model.rule.PlacementError.OverlineViolation

class MainActivity : AppCompatActivity() {
    private lateinit var omokDao: OmokDao
    private val view = OutputView()

    override fun onStart() {
        if (omokDao.hasOmokData()) {
            createBoard()
        }
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        omokDao = OmokDao(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val blackRuleChecker =
            BlackRuleChecker(
                rule = BlackRenjuRule(),
                mapper = { position -> Point(position.col.value + 1, position.row.value + 1) },
            )

        val game = Game(blackRuleChecker)

        val board = findViewById<TableLayout>(R.id.board)
        val rows =
            board.children
                .filterIsInstance<TableRow>()
                .toList()
                .reversed()

        rows.forEachIndexed { rowIndex, row ->
            row.children
                .filterIsInstance<ImageView>()
                .forEachIndexed { colIndex, cell ->
                    cell.setOnClickListener {
                        val position = Position(Row(rowIndex), Col(colIndex))

                        val stoneRes =
                            if (game.turn == StoneColor.BLACK) R.drawable.black_stone else R.drawable.white_stone

                        val violation = game.playTurn(position)
                        val result = printViolation(violation)

                        if (violation != NoViolation) {
                            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        cell.setImageResource(stoneRes)
                        omokDao.insertOmok(
                            rowIndex,
                            colIndex,
                            game.lastStone?.stoneColor.toString(),
                        )

                        val winColor = view.stoneStateText(game.lastStone!!.stoneColor)

                        isGameOver(game.isOmok(), game, winColor)
                    }
                }
        }
    }

    private fun isGameOver(
        isOmok: Boolean,
        game: Game,
        winColor: String,
    ) {
        if (isOmok) {
            showGameEndDialog(game.lastStone!!.stoneColor) {
                omokDao.deleteDatabase()
                recreate()
            }
            Toast
                .makeText(
                    this,
                    "${winColor}이 우승했습니다!",
                    Toast.LENGTH_LONG,
                ).show()
        }
    }

    private fun createBoard() {
        val stones = omokDao.getAllStones()
        val board = findViewById<TableLayout>(R.id.board)
        val rows =
            board.children
                .filterIsInstance<TableRow>()
                .toList()
                .reversed()

        stones.forEach { stone ->
            val rowIndex = stone.position.row.value
            val colIndex = stone.position.col.value
            val cell =
                rows[rowIndex]
                    .children
                    .filterIsInstance<ImageView>()
                    .elementAt(colIndex)

            val stoneRes =
                when (stone.stoneColor) {
                    StoneColor.BLACK -> R.drawable.black_stone
                    StoneColor.WHITE -> R.drawable.white_stone
                }
            cell.setImageResource(stoneRes)
        }
    }

    private fun showGameEndDialog(
        winner: StoneColor,
        onRestart: () -> Unit,
    ) {
        AlertDialog
            .Builder(this)
            .setTitle("게임 종료")
            .setMessage("${view.stoneStateText(winner)}이(가) 승리했습니다!\n게임을 다시 시작할까요?")
            .setPositiveButton("재시작") { _, _ ->
                omokDao.deleteDatabase()
                onRestart()
            }.show()
    }

    private fun printViolation(violation: PlacementError): String =
        when (violation) {
            AlreadyOccupiedViolation -> "현재 위치에 돌이 있습니다"
            DoubleThreeViolation -> "3-3 반칙이 발생했습니다"
            DoubleFourViolation -> "4-4 반칙이 발생했습니다"
            OverlineViolation -> "장목 반칙이 발생했습니다"
            NoViolation -> ""
        }

    override fun onDestroy() {
        omokDao.dbHelper.close()

        super.onDestroy()
    }
}
