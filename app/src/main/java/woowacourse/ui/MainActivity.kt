package woowacourse.ui

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
import androidx.core.view.size
import rule.BlackRenjuRule
import woowacourse.omok.R
import woowacourse.omok.adapter.RenjuRuleAdapter
import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.data.StoneDao
import woowacourse.omok.data.StoneRepositoryImpl
import woowacourse.omok.domain.Game
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Column
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Row
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository

class MainActivity : AppCompatActivity() {
    private lateinit var boardView: TableLayout
    private lateinit var dbHelper: OmokDatabaseHelper
    private lateinit var repository: StoneRepository
    private lateinit var boardView: List<List<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = OmokDatabaseHelper(this)
        dbHelper.writableDatabase
        repository = StoneRepositoryImpl(StoneDao(dbHelper))
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        boardView = findViewById(R.id.board)
        boardView =
            findViewById<TableRow>(R.id.board).children.filterIsInstance<TableRow>()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBoardView()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun initBoardView() {
        val renjuRule = RenjuRuleAdapter(BlackRenjuRule())
        val board = Board(boardView.size)
        val stones = repository.allInBoardSize(boardView.size)
        val omokGame =
            Game(
                OmokRule(renjuRule),
                stones,
                Turn(repository.lastStoneType()),
            )

        boardView.forEachIndexed { row, tableRow ->
            tableRow.forEachIndexed { column, view ->
                view.tag = position(column, row, board)
                view.setOnClickListener {
                    if (omokGame.isFinished()) return@setOnClickListener
                    omokGame.play(omokEvent(view))
                }
            }
        }
    }

    private fun initStoneImage(
        view: ImageView,
        stones: Stones,
    ) {
        val tag = view.tag as? Position ?: return
        val stone = stones.find(tag) ?: return
        setStoneImage(view, stone.stoneType)
    }

    private fun showFinishDialog(
        stoneType: StoneType,
        resetGame: () -> Unit,
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.end_game))
        builder.setMessage(getString(R.string.finish_message, stoneType.koreanName()))
        builder.setPositiveButton(getString(R.string.confirm)) { dialog, _ ->
            repository.clear()
            resetGame()
            resetView()
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            repository.clear()
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun resetView() {
        boardView.forEach { row ->
            row.forEach {
                it.setImageResource(0)
            }
        }
    }

    private fun omokEvent(view: ImageView) =
        object : PlayEvent {
            override fun showPlaceResult(ruleResult: RuleResult) =
                when (ruleResult) {
                    RuleResult.DuplicatePosition ->
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.duplicate_position_message),
                            Toast.LENGTH_SHORT,
                        ).show()

                    RuleResult.RenJuRule ->
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.black_renju_rule_message),
                            Toast.LENGTH_SHORT,
                        ).show()

                    is RuleResult.OnRule -> Unit
                }

            override fun onPosition(): Position = view.tag as Position

            override fun onPlace(stone: Stone) {
                setStoneImage(view, stone.stoneType)
                repository.insert(stone)
            }

            override fun onFinish(
                stoneType: StoneType,
                resetGame: () -> Unit,
            ) {
                showFinishDialog(stoneType, resetGame)
            }
        }

    private fun setStoneImage(
        view: ImageView,
        stoneType: StoneType,
    ) {
        when (stoneType) {
            StoneType.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneType.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun position(
        column: Int,
        row: Int,
        board: Board,
    ) = Position(Column.from(column + 1, board.column), Row.from(row + 1, board.row))

    private fun StoneType.koreanName() =
        when (this) {
            StoneType.BLACK -> getString(R.string.korean_black_stone)
            StoneType.WHITE -> getString(R.string.korean_white_stone)
        }
}
