package woowacourse.omok

import android.os.Bundle
import android.util.Log
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
    private var roomId: Int = -1

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
        roomId = intent.getIntExtra("ROOM_ID", -1)
        Log.d("roomId", roomId.toString())
        if (omokDao.hasOmokData(roomId)) {
            restoreBoard(roomId)
        }

        handleGame()
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

    private fun restoreBoard(roomId: Int) {
        val stones = omokDao.getStonesByRoomId(roomId)

        stones.forEach { entity ->
            val domainStone = entity.toDomain()
            game.applyPlacement(domainStone.position)

            val stoneRes = stoneRes(domainStone.stoneColor)
            getCell(entity.row, entity.col).setImageResource(stoneRes)
        }
    }

    private fun getCell(
        row: Int,
        col: Int,
    ): ImageView = cellMap[row][col]

    private fun handleGame() {
        cellMap.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                cell.setOnClickListener {
                    handleCellClick(rowIndex, colIndex, cell)
                }
            }
        }
    }

    private fun handleCellClick(
        rowIndex: Int,
        colIndex: Int,
        cell: ImageView,
    ) {
        val position = Position(Row(rowIndex), Col(colIndex))
        when (val result = game.playTurn(position)) {
            is PlayResult.Violation -> showViolationToast(result)
            is PlayResult.Success -> handleSuccess(cell, rowIndex, colIndex)
            is PlayResult.Win -> handleWin(cell, rowIndex, colIndex, result.winner)
        }
    }

    private fun showViolationToast(result: PlayResult.Violation) {
        Toast.makeText(this, printViolation(result), Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccess(
        cell: ImageView,
        row: Int,
        col: Int,
    ) {
        cell.setImageResource(stoneRes(game.lastStone!!.stoneColor))
        omokDao.insertOmok(roomId, row, col, game.lastStone!!.stoneColor.name)
    }

    private fun handleWin(
        cell: ImageView,
        row: Int,
        col: Int,
        winner: StoneColor?,
    ) {
        winner?.let { color ->
            cell.setImageResource(stoneRes(color))
            omokDao.insertOmok(roomId, row, col, color.name)
            showGameEndDialog(color) {
                omokDao.deleteDatabase()
                recreate()
            }
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
            .setTitle(getString(R.string.game_finished))
            .setMessage("${view.stoneStateText(winner)} ${getString(R.string.game_winner_info)}")
            .setPositiveButton(getString(R.string.game_restart)) { _, _ ->
                omokDao.deleteDatabase()
                onRestart()
            }.show()
    }

    private fun printViolation(violation: PlayResult.Violation): String =
        when (violation.error) {
            AlreadyOccupiedViolation -> getString(R.string.violation_already_occupied)
            DoubleThreeViolation -> getString(R.string.violation_double_three)
            DoubleFourViolation -> getString(R.string.violation_double_four)
            OverlineViolation -> getString(R.string.violation_overline)
            NoViolation -> ""
        }

    override fun onDestroy() {
        omokDao.dbHelper.close()

        super.onDestroy()
    }
}
