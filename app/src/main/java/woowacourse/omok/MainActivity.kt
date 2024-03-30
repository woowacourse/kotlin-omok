package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.domain.controller.AbideForbiddenRules
import woowacourse.omok.domain.controller.EmptyPosition
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Player
import woowacourse.omok.domain.model.Players
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition
import woowacourse.omok.domain.view.output

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val omokBoard: Board = Board()
        val players =
            Players(
                blackStonePlayer =
                    Player(
                        stone = Stone.BLACK,
                        rules =
                            RuleAdapter(
                                ContinualStonesWinningCondition(
                                    ContinualStonesStandard(5),
                                    ContinualStonesCondition.EXACT,
                                ),
                                ForbiddenRules(
                                    ThreeThreeRule.forBlack(),
                                    FourFourRule.forBlack(),
                                    OverlineRule(),
                                ),
                            ),
                    ),
                whiteStonePlayer =
                    Player(
                        stone = Stone.WHITE,
                        rules =
                            RuleAdapter(
                                ContinualStonesWinningCondition(
                                    ContinualStonesStandard(5),
                                    ContinualStonesCondition.EXACT,
                                ),
                                ForbiddenRules(),
                            ),
                    ),
            )

        val omokGame =
            OmokGame(
                board = omokBoard,
                players,
                validPosition =
                    listOf(
                        EmptyPosition { player: Player, position: Position, message: String ->
                            Toast.makeText(
                                this,
                                "${player.stone}이 두려는 위치는 위치${position.output()}는 $message",
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                        AbideForbiddenRules { player: Player, position: Position, message: String ->
                            Toast.makeText(
                                this,
                                "${player.stone}이 두려는 위치는 위치${position.output()}는 $message",
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    ),
            )

        var recentPlayer = players.firstOrderedPlayer()
        var recentPosition: Position? = null

        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { row, views ->
                views.children.filterIsInstance<ImageView>()
                    .forEachIndexed { col, view ->
                        view.setOnClickListener {
                            val nextPosition =
                                omokGame.nextPosition(
                                    recentPosition,
                                    recentPlayer,
                                    nextStonePosition = { _, _ -> Position(row, col) },
                                    nextStonePositionResult = { changeTheStone(view, recentPlayer) },
                                ) ?: return@setOnClickListener

                            if (recentPlayer.isWin(omokBoard, nextPosition)) {
                                Toast.makeText(this, "${recentPlayer.stone}이 승리했습니다.", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }
                            recentPlayer = players.nextOrder(recentPlayer)
                        }
                    }
            }
    }

    private fun changeTheStone(
        view: ImageView,
        recentPlayer: Player,
    ) {
        view.setImageResource(
            when (recentPlayer.stone) {
                Stone.BLACK -> R.drawable.black_stone
                Stone.WHITE -> R.drawable.white_stone
                Stone.NONE -> throw IllegalArgumentException("NONE은 둘 수 없습니다.")
            },
        )
    }
}
