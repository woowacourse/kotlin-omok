package woowacourse.omok.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.data.DbHelper
import woowacourse.omok.data.OmokRepository
import woowacourse.omok.data.StoneLocalDataSource
import woowacourse.omok.domain.GameBoard
import woowacourse.omok.domain.player.Player
import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.rule.adapter.RuleAdapter
import woowacourse.omok.domain.rule.lib.OmokRule
import woowacourse.omok.domain.rule.lib.RenjuRule
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.domain.stone.StoneColor.BLACK
import woowacourse.omok.domain.stone.StoneColor.WHITE

class MainActivity : AppCompatActivity() {
    private lateinit var oMokRepository: OmokRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val players = ArrayDeque<Player>()
        players.add(Player(BLACK, listOf(RuleAdapter(RenjuRule()))))
        players.add(Player(WHITE, listOf(RuleAdapter(OmokRule()))))
        val service = GameBoard(players = players)

        oMokRepository = OmokRepository(StoneLocalDataSource(DbHelper(this)))

        val board = findViewById<TableLayout>(R.id.board)

        drawExistedStones(board)

        board
            .children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rowView ->
                rowView.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columnIndex, cell ->
                        val position = Position(Row.from(ROW_SIZE - rowIndex), Col.from(columnIndex + ADJUST_COL_INDEX_COUNT))
                        cell.tag = position
                        cell.setOnClickListener {
                            service.putStone { _, _ ->
                                position
                            }.onFailure { error ->
                                toastMessage(message = error.message ?: "")
                            }.onSuccess { stoneColor ->
                                if (oMokRepository.insert(stone = Stone(position, stoneColor))) {
                                    showPlacedStone(view = cell, stoneColor = stoneColor)
                                    gameJudgeProcess(service)
                                    service.nextTurn()
                                }
                            }
                        }
                    }
            }
    }

    private fun drawExistedStones(board: TableLayout) {
        val existedStones = oMokRepository.findAllStone()
        if (existedStones.isNotEmpty()) {
            board
                .children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rowView ->
                    rowView.children.filterIsInstance<ImageView>()
                        .forEachIndexed { columnIndex, cell ->
                            val position =
                                Position(
                                    Row.from(ROW_SIZE - rowIndex),
                                    Col.from(columnIndex + ADJUST_COL_INDEX_COUNT),
                                )
                            val stone =
                                existedStones.find { existedStone ->
                                    existedStone.position.isSame(position)
                                }
                            stone?.let { showPlacedStone(view = cell, stoneColor = stone.color) }
                        }
                }
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showPlacedStone(
        view: ImageView,
        stoneColor: StoneColor,
    ) {
        when (stoneColor) {
            BLACK -> view.setImageResource(R.drawable.black_stone)
            WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun gameJudgeProcess(service: GameBoard) {
        if (service.gameOver()) {
            val winnerColor: String =
                when (service.winner()) {
                    BLACK -> "흑돌"
                    WHITE -> "백돌"
                }
            toastMessage(message = getString(R.string.main_scr_winner_message_format, winnerColor))
            convertBoardTouchable(isTouchable = false)
            oMokRepository.removeAll()
        }
    }

    private fun convertBoardTouchable(isTouchable: Boolean) {
        val board = findViewById<TableLayout>(R.id.board)
        board.children.filterIsInstance<TableRow>().forEach { rowView ->
            rowView.children.forEach { cellView ->
                cellView.isClickable = isTouchable
            }
        }
    }

    companion object {
        private const val ROW_SIZE = 15
        private const val ADJUST_COL_INDEX_COUNT = 1
    }
}
