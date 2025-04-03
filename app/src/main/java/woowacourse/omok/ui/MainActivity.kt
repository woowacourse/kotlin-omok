package woowacourse.omok.ui

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
import woowacourse.App
import woowacourse.omok.R
import woowacourse.omok.adapter.RenjuRuleAdapter
import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.Play
import woowacourse.omok.domain.event.GameEvent
import woowacourse.omok.domain.event.PlayEvent
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository
import woowacourse.omok.ui.IntentKeys.GAME_ID
import woowacourse.omok.ui.mapper.toPosition
import woowacourse.omok.ui.model.BoardView
import woowacourse.omok.ui.model.PositionUiModel

class MainActivity : AppCompatActivity() {
    private lateinit var boardView: BoardView
    private lateinit var stoneRepository: StoneRepository
    private var gameId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        gameId = intent.getLongExtra(GAME_ID, -1L)
        boardView =
            BoardView(
                findViewById<TableRow>(R.id.board).children.filterIsInstance<TableRow>()
                    .map { it.children.filterIsInstance<ImageView>().toList() }.toList(),
            )
        stoneRepository = (application as App).stoneRepository
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBoardView()
    }

    private fun initBoardView() {
        val renjuRule = RenjuRuleAdapter(BlackRenjuRule())
        val board = Board(boardView.size)
        Play(
            OmokRule(renjuRule),
            stoneRepository,
            gameId,
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
            override fun initBoard(omokPlay: Play) {
                boardView.updateBoard { row, column, view ->
                    view.tag = position(column, row)
                    view.setOnClickListener {
                        if (omokPlay.isFinished()) return@setOnClickListener
                        omokPlay.play(playEvent(view))
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
