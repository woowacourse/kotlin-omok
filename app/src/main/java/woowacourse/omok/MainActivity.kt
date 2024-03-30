package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.FinishAction
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.game.OmokGame
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.model.game.PlaceType
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
    private val resultTextView by lazy { findViewById<TextView>(R.id.result_text) }
    private val restartButton by lazy { findViewById<Button>(R.id.restart_button) }
    private var isFinish = false

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
        val omokGame = OmokGame(board, omokPlayers, finishAction())
        resultTextView.text = "흑의 차례입니다."
        boardView.setBoardView(omokGame)
        restartButton.setOnClickListener {
            restartGame(omokGame)
        }
    }

    private fun finishAction(): FinishAction {
        return object : FinishAction {
            override val conditions =
                listOf(
                    FiveStonesFinishCondition(),
                    FullBoardFinishCondition(),
                    AllForbiddenPositionFinishCondition(),
                )

            override fun onFinish(finishType: FinishType) {
                isFinish = true
                when (finishType) {
                    FinishType.BLACK_PLAYER_WIN -> showToast("흑 승")
                    FinishType.WHITE_PLAYER_WIN -> showToast("백 승")
                    FinishType.DRAW -> showToast("무승부입니다")
                    FinishType.NOT_FINISH -> {}
                }
            }
        }
    }

    private fun TableLayout.setBoardView(omokGame: OmokGame) {
        children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, stoneImage ->
                stoneImage.setStoneViewOnClickListener(omokGame, index)
            }
    }

    private fun ImageView.setStoneViewOnClickListener(
        omokGame: OmokGame,
        index: Int,
    ) {
        setOnClickListener {
            Log.d(TAG, "position (${index / boardSize}, ${index % boardSize})")
            if (isFinish) return@setOnClickListener
            omokGame.progressTurn(this, index)
        }
    }

    private fun OmokGame.progressTurn(stoneImageView: ImageView, index: Int) {
        val position = Position(index / boardSize, index % boardSize)
        val placeType = turn(position)
        when (placeType) {
            PlaceType.BLACK_PLACE -> stoneImageView.setImageResource(R.drawable.black_stone)
            PlaceType.WHITE_PLACE -> stoneImageView.setImageResource(R.drawable.white_stone)
            PlaceType.CANNOT_PLACE -> {
                showToast("놓을 수 없음")
                return
            }
        }
        resultTextView.text = """
            ${if (placeType == PlaceType.BLACK_PLACE) "백" else "흑"}의 차례입니다.
            최근 위치: ${this.recentPosition()?.row}, ${this.recentPosition()?.col}
        """.trimMargin()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun restartGame(omokGame: OmokGame) {
        isFinish = false
        omokGame.restart()
        boardView
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, stoneImage ->
                stoneImage.setImageResource(0)
            }
        resultTextView.text = "흑의 차례입니다."
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
