package woowacourse.ui

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
import androidx.core.view.size
import rule.BlackRenjuRule
import woowacourse.omok.R
import woowacourse.omok.adapter.RenjuRuleAdapter
import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.Game
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Column
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Row
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

class MainActivity : AppCompatActivity() {
    private lateinit var boardView: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        boardView = findViewById(R.id.board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBoardTag()
    }

    private fun initBoardTag(board: Board) {
        val renjuRule = RenjuRuleAdapter(BlackRenjuRule())
        val omokGame = Game(OmokRule(renjuRule), Stones(listOf()), Turn(StoneType.BLACK))
        val board = Board(boardView.size)
        boardView.children.filterIsInstance<TableRow>().forEachIndexed { row, tableRow ->
            tableRow.children.filterIsInstance<ImageView>().forEachIndexed { column, view ->
                view.setOnClickListener {
                    positionViewOnclick(view)
                }
            }
        }
    }

    private fun positionViewOnclick(
        imageView: ImageView,
        position: Position,
        omokGame: Game,
    ) {
        val ruleResult = omokGame.canPlace(position)
        when (ruleResult) {
            RuleResult.DuplicatePosition ->
                Toast.makeText(
                    this,
                    "이미 둔 곳에 둘 수 없습니다.",
                    Toast.LENGTH_SHORT,
                ).show()

            RuleResult.OnRule -> {
                val stoneType = omokGame.currentStoneType
                omokGame.placeStone(position)
                when (stoneType) {
                    StoneType.BLACK -> imageView.setImageResource(R.drawable.black_stone)
                    StoneType.WHITE -> imageView.setImageResource(R.drawable.white_stone)
                }
                if (omokGame.isFinished()) {
                    when (omokGame.currentStoneType) {
                        StoneType.BLACK ->
                            Toast.makeText(
                                this,
                                "흑돌이 승리 했습니다.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        StoneType.WHITE ->
                            Toast.makeText(
                                this,
                                "백돌이 승리 했습니다.",
                                Toast.LENGTH_SHORT,
                            ).show()
                    }
                }
            }

            RuleResult.RenJuRule ->
                Toast.makeText(
                    this,
                    "흑돌은 3 3, 4 4, 장목을 둘 수 없습니다.",
                    Toast.LENGTH_SHORT,
                ).show()
        }
    }
}

private fun position(
    column: Int,
    row: Int,
    board: Board,
) = Position(Column.from(column + 1, board.column), Row.from(row + 1, board.row))
