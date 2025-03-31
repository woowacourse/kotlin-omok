package woowacourse.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import rule.BlackRenjuRule
import woowacourse.omok.R
import woowacourse.omok.adapter.RenjuRuleAdapter
import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.data.StoneDao
import woowacourse.omok.data.StoneRepositoryImpl
import woowacourse.omok.domain.Game
import woowacourse.omok.domain.event.GameEvent
import woowacourse.omok.domain.event.PlayEvent
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.ui.mapper.toPosition
import woowacourse.ui.model.BoardView
import woowacourse.ui.model.PositionUiModel

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: OmokDatabaseHelper
    private lateinit var boardView: BoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = OmokDatabaseHelper(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        boardView =
            BoardView(
                findViewById<TableRow>(R.id.board).children.filterIsInstance<TableRow>()
                    .map { it.children.filterIsInstance<ImageView>().toList() }.toList(),
            )

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
        Game(
            OmokRule(renjuRule),
            StoneRepositoryImpl(StoneDao(dbHelper)),
            board,
            gameEvent(),
        )
    }

    private fun showFinishDialog(
        stoneType: StoneType,
        resetGame: () -> Unit,
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.end_game))
        builder.setMessage(getString(R.string.finish_message, stoneType.koreanName()))
        builder.setPositiveButton(getString(R.string.confirm)) { dialog, _ ->
            resetGame()
            resetView()
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun resetView() {
        boardView.updateBoard { _, _, view ->
            view.setImageResource(0)
        }
    }

    private fun gameEvent() =
        object : GameEvent {
            override fun initBoard(omokGame: Game) {
                boardView.updateBoard { row, column, view ->
                    view.tag = position(column, row)
                    view.setOnClickListener {
                        if (omokGame.isFinished()) return@setOnClickListener
                        omokGame.play(playEvent(view))
                    }
                }
            }

            override fun showInitStones(stones: Stones) {
                stones.value.forEach { stone ->
                    boardView.updateBoard { row, column, view ->
                        if (stone.isSamePosition(column + 1, row + 1)) {
                            setStoneImage(
                                view,
                                stone.stoneType,
                            )
                        }
                    }
                }
            }
        }

    private fun playEvent(view: ImageView) =
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

            override fun onPosition(board: Board): Position = (view.tag as PositionUiModel).toPosition(board)

            override fun onPlace(stone: Stone) {
                setStoneImage(view, stone.stoneType)
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
    ) = PositionUiModel(column + 1, row + 1)

    private fun StoneType.koreanName() =
        when (this) {
            StoneType.BLACK -> getString(R.string.korean_black_stone)
            StoneType.WHITE -> getString(R.string.korean_white_stone)
        }
}
