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
import woowacourse.omok.domain.model.StoneRepository
import woowacourse.omok.domain.model.position.Column
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Row
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

class MainActivity : AppCompatActivity() {
    private lateinit var boardView: TableLayout
    private lateinit var dbHelper: OmokDatabaseHelper
    private lateinit var repository: StoneRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = OmokDatabaseHelper(this)
        dbHelper.writableDatabase
        repository = StoneRepositoryImpl(StoneDao(dbHelper))
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        boardView = findViewById(R.id.board)
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
        val stones = repository.getAllInBoardSize(boardView.size)
        val omokGame =
            Game(
                OmokRule(renjuRule),
                stones,
                Turn(StoneType.BLACK),
            )

        boardView.children.filterIsInstance<TableRow>().forEachIndexed { row, tableRow ->
            tableRow.children.filterIsInstance<ImageView>().forEachIndexed { column, view ->
                view.tag = position(column, row, board)
                initStoneImage(view, stones)
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
        builder.setTitle("게임 종료")
        builder.setMessage("${stoneType.koreanName()}의 승리입니다.\n게임을 다시 시작 하시겠습니까?")
        builder.setPositiveButton("확인") { dialog, _ ->
            repository.clear()
            resetGame()
            resetView()
            dialog.dismiss()
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            repository.clear()
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun resetView() {
        boardView.children.filterIsInstance<TableRow>()
            .flatMap { it.children.filterIsInstance<ImageView>() }.forEach {
                it.setImageResource(0)
            }
    }

    private fun omokEvent(view: ImageView) =
        object : PlayEvent {
            override fun showPlaceResult(ruleResult: RuleResult) =
                when (ruleResult) {
                    RuleResult.DuplicatePosition ->
                        Toast.makeText(
                            this@MainActivity,
                            "이미 둔 곳에 둘 수 없습니다.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    RuleResult.RenJuRule ->
                        Toast.makeText(
                            this@MainActivity,
                            "흑돌은 3 3, 4 4, 장목을 둘 수 없습니다.",
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
}

private fun position(
    column: Int,
    row: Int,
    board: Board,
) = Position(Column.from(column + 1, board.column), Row.from(row + 1, board.row))

private fun StoneType.koreanName() =
    when (this) {
        StoneType.BLACK -> "흑"
        StoneType.WHITE -> "백"
    }
