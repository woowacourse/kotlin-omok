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
    private lateinit var cellMap: List<List<ImageView>>

    private val blackRuleChecker =
        BlackRuleChecker(
            rule = BlackRenjuRule(),
            mapper = { position -> Point(position.col.value + 1, position.row.value + 1) },
        )

    private val game = Game(blackRuleChecker)

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

        initBoard()
        if (omokDao.hasOmokData()) {
            restoreBoard()
        }

        cellMap.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                cell.setOnClickListener {
                    val position = Position(Row(rowIndex), Col(colIndex))
                    val stoneRes = stoneRes(game.turn)

                    val violation = game.playTurn(position)
                    val result = printViolation(violation)

                    if (violation != NoViolation) {
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    cell.setImageResource(stoneRes)
                    omokDao.insertOmok(rowIndex, colIndex, game.lastStone?.stoneColor.toString())

                    handleGameOver(game.isOmok(), game)
                }
            }
        }
    }

    private fun handleGameOver(
        isOmok: Boolean,
        game: Game,
    ) {
        if (isOmok) {
            showGameEndDialog(game.lastStone!!.stoneColor) {
                omokDao.deleteDatabase()
                recreate()
            }
        }
    }

    private fun initBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        val rows =
            board.children
                .filterIsInstance<TableRow>()
                .toList()
                .asReversed()
        cellMap = rows.map { row -> row.children.filterIsInstance<ImageView>().toList() }
    }

    private fun getCell(
        row: Int,
        col: Int,
    ): ImageView = cellMap[row][col]

    private fun restoreBoard() {
        val stones = omokDao.getAllStones()

        stones.forEach { stone ->
            val rowIndex = stone.position.row.value
            val colIndex = stone.position.col.value
            game.applyPlacement(Position(Row(rowIndex), Col(colIndex)))

            val stoneRes = stoneRes(stone.stoneColor)
            getCell(rowIndex, colIndex).setImageResource(stoneRes)
        }
    }

    private fun stoneRes(stoneColor: StoneColor): Int =
        when (stoneColor) {
            StoneColor.BLACK -> R.drawable.black_stone
            StoneColor.WHITE -> R.drawable.white_stone
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
