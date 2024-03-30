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

class MainActivity(private val boardSize: Int = 15) : AppCompatActivity() {
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
        val omokGame = OmokGame2(board, omokPlayers, finishAction())
        boardView.setBoardView(omokGame)
    }

    private fun finishAction(): FinishAction {
        return object : FinishAction {
            override val conditions = listOf(
                FiveStonesFinishCondition(),
                FullBoardFinishCondition(),
                AllForbiddenPositionFinishCondition(),
            )

            override fun onFinish(finishType: FinishType) {
                when (finishType) {
                    FinishType.BLACK_PLAYER_WIN -> showToast("흑 승")
                    FinishType.WHITE_PLAYER_WIN -> showToast("백 승")
                    FinishType.DRAW -> showToast("무승부입니다")
                    FinishType.NOT_FINISH -> {}
                }
            }
        }
    }

    private fun TableLayout.setBoardView(omokGame: OmokGame2) {
        children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, stoneImage ->
                stoneImage.setStoneViewOnClickListener(omokGame, index)
            }
    }

    private fun ImageView.setStoneViewOnClickListener(omokGame: OmokGame2, index: Int) {
        setOnClickListener {
            Log.d(TAG, "position (${index / boardSize}, ${index % boardSize})")
            val position = Position(index / boardSize, index % boardSize)
            when (omokGame.turn(position)) {
                PlaceType.BLACK_PLACE -> setImageResource(R.drawable.black_stone)
                PlaceType.WHITE_PLACE -> setImageResource(R.drawable.white_stone)
                PlaceType.CANNOT_PLACE -> showToast("놓을 수 없음")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
