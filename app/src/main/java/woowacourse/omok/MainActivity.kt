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
import woowacourse.omok.domain.model.Stone.Companion.toStone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.database.OmokTurnDao
import woowacourse.omok.domain.model.database.OmokTurnDbHelper
import woowacourse.omok.domain.model.database.toStonePosition
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ForbiddenRules
import woowacourse.omok.domain.model.rule.RuleAdapter
import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition
import woowacourse.omok.domain.model.toOmokTurn
import woowacourse.omok.domain.view.output

class MainActivity : AppCompatActivity() {
    private lateinit var omokTurnDao: OmokTurnDao
    private lateinit var currentPlayer: Player

    private val omokBoard: Board = Board()
    private val players = renjuRulePlayers()
    private val omokGame = renjuRuleOmokGame(omokBoard, players)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        omokTurnDao = OmokTurnDao(OmokTurnDbHelper(this))
        currentPlayer = players.find(omokTurnDao.findLatestStoneColor().toStone().nextOrFirst())
        initBoard(omokTurnDao.findAll().map { it.toStonePosition() })
    }

    private fun initBoard(loadedStonePositions: List<StonePosition>) {
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { row, views ->
                views.children.filterIsInstance<ImageView>()
                    .forEachIndexed { col, view ->
                        val currentPosition = Position(row, col)
                        loadBoardData(loadedStonePositions, currentPosition, view)
                        placeStoneListener(view, currentPosition)
                    }
            }
    }

    private fun loadBoardData(
        stonePositions: List<StonePosition>,
        position: Position,
        view: ImageView,
    ) {
        stonePositions.find { it.position == position }?.let {
            changeStoneUI(view, it.stone)
        }
    }

    private fun placeStoneListener(
        view: ImageView,
        currentPosition: Position,
    ) {
        view.setOnClickListener {
            val nextPosition = nextPosition(currentPlayer, currentPosition, view) ?: return@setOnClickListener
            omokTurnDao.save(StonePosition(nextPosition, currentPlayer.stone).toOmokTurn())

            if (win(currentPlayer, nextPosition)) return@setOnClickListener
            currentPlayer = players.nextOrder(currentPlayer)
        }
    }

    private fun nextPosition(
        recentPlayer: Player,
        position: Position,
        view: ImageView,
    ) = omokGame.nextPosition(
        currentPosition = null,
        recentPlayer = recentPlayer,
        nextStonePosition = { _, _ -> position },
        nextStonePositionResult = { changeStoneUI(view, recentPlayer.stone) },
    )

    private fun win(
        recentPlayer: Player,
        nextPosition: Position,
    ): Boolean {
        if (recentPlayer.isWin(omokBoard, nextPosition)) {
            showShortToast(recentPlayer, nextPosition, "${recentPlayer.stone}이 승리했습니다.")
            omokTurnDao.drop()
            return true
        }
        return false
    }

    private fun renjuRulePlayers() =
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

    private fun renjuRuleOmokGame(
        omokBoard: Board,
        players: Players,
    ) = OmokGame(
        board = omokBoard,
        players,
        validPosition =
            listOf(
                EmptyPosition { player: Player, position: Position, message: String ->
                    showShortToast(player, position, message)
                },
                AbideForbiddenRules { player: Player, position: Position, message: String ->
                    showShortToast(player, position, message)
                },
            ),
    )

    private fun showShortToast(
        player: Player,
        position: Position,
        message: String,
    ) {
        Toast.makeText(
            this,
            "${player.stone}이 두려는 위치는 위치${position.output()}는 $message",
            Toast.LENGTH_SHORT,
        ).show()
    }

    private fun changeStoneUI(
        view: ImageView,
        recentPlayerStone: Stone,
    ) {
        view.setImageResource(
            when (recentPlayerStone) {
                Stone.BLACK -> R.drawable.black_stone
                Stone.WHITE -> R.drawable.white_stone
                Stone.NONE -> throw IllegalArgumentException("NONE은 둘 수 없습니다.")
            },
        )
    }
}
