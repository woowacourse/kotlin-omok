package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.rule.ban.DoubleFourForbiddenPlace
import woowacourse.omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import woowacourse.omok.model.rule.ban.OverlineForbiddenPlace
import woowacourse.omok.model.rule.finish.AllForbiddenPositionFinishCondition
import woowacourse.omok.model.rule.finish.FiveStonesFinishCondition
import woowacourse.omok.model.rule.finish.FullBoardFinishCondition

class MainActivity : AppCompatActivity() {
    private val boardSize: Int = 15
    private val omokPlayers: OmokPlayers
    private val boardView by lazy { findViewById<TableLayout>(R.id.board) }

    init {
        val blackForbiddenPlaces =
            listOf(
                DoubleFourForbiddenPlace(),
                DoubleOpenThreeForbiddenPlace(),
                OverlineForbiddenPlace(),
            )

        omokPlayers =
            OmokPlayers(
                blackStonePlayer = Player(Stone.BLACK, blackForbiddenPlaces),
                whiteStonePlayer = Player(Stone.WHITE),
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val board = Board(boardSize)
        val finishAction = object : FinishAction {
            override val conditions = listOf(
                FiveStonesFinishCondition(),
                FullBoardFinishCondition(),
                AllForbiddenPositionFinishCondition(),
            )

            override fun onFinish(finishType: FinishType) {
                when (finishType) {
                    FinishType.BLACK_PLAYER_WIN -> Toast.makeText(this@MainActivity, "흑 승", Toast.LENGTH_SHORT).show()
                    FinishType.WHITE_PLAYER_WIN -> Toast.makeText(this@MainActivity, "백 승", Toast.LENGTH_SHORT).show()
                    FinishType.DRAW -> Toast.makeText(this@MainActivity, "무승부입니다", Toast.LENGTH_SHORT).show()
                    FinishType.NOT_FINISH -> {}
                }
            }
        }
        val omokGame = OmokGame2(board, omokPlayers, finishAction)

        boardView
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { idx, view ->
                view.setOnClickListener {
                    Log.d(TAG, "position : r: ${idx / boardSize} c: ${idx % boardSize}")
                    val position = Position(idx / boardSize, idx % boardSize)
                    val placeType = omokGame.nextTurn(position)

                    when (placeType) {
                        PlaceType.BLACK_PLACE -> view.setImageResource(R.drawable.black_stone)
                        PlaceType.WHITE_PLACE -> view.setImageResource(R.drawable.white_stone)
                        PlaceType.CANNOT_PLACE -> Toast.makeText(this@MainActivity, "놓을수없음", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
