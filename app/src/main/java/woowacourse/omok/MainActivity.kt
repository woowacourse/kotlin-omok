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
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.OutputView
import rule.BlackRenjuRule
import rule.wrapper.point.Point
import woowacourse.omok.data.dao.OmokDao
import woowacourse.omok.data.mapper.toDomain
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.model.game.Game
import woowacourse.omok.model.game.PlayResult
import woowacourse.omok.model.rule.PlacementError.AlreadyOccupiedViolation
import woowacourse.omok.model.rule.PlacementError.DoubleFourViolation
import woowacourse.omok.model.rule.PlacementError.DoubleThreeViolation
import woowacourse.omok.model.rule.PlacementError.NoViolation
import woowacourse.omok.model.rule.PlacementError.OverlineViolation
import woowacourse.omok.model.stone.StoneColor

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
                    val result = game.playTurn(position)

                    when (result) {
                        is PlayResult.Violation -> {
                            Toast.makeText(this, printViolation(result), Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        is PlayResult.Success -> {
                            cell.setImageResource(stoneRes(game.lastStone!!.stoneColor))
                            omokDao.insertOmok(
                                rowIndex,
                                colIndex,
                                game.lastStone?.stoneColor.toString(),
                            )
                        }

                        is PlayResult.Win -> {
                            cell.setImageResource(stoneRes(result.winner))
                            omokDao.insertOmok(rowIndex, colIndex, result.winner.toString())
                            showGameEndDialog(result.winner) {
                                omokDao.deleteDatabase()
                                recreate()
                            }
                        }
                    }
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

        stones.forEach { entity ->
            val domainStone = entity.toDomain()
            game.applyPlacement(domainStone.position)

            val stoneRes = stoneRes(domainStone.stoneColor)
            getCell(entity.row, entity.col).setImageResource(stoneRes)
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

    private fun printViolation(violation: PlayResult.Violation): String =
        when (violation.error) {
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
