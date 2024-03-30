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
import woowacourse.omok.view.output

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
        setProgressText(omokGame.nowOrderStone(), omokGame.recentPosition())
        stoneImageView { index, view ->
            view.setStoneViewOnClickListener(omokGame, index)
        }
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
                setResultText(finishType)
            }
        }
    }

    private fun setProgressText(
        nowOrderStone: Stone,
        recentPosition: Position?,
    ) {
        var resultText = resources.getString(R.string.turn_player).format(nowOrderStone.output())
        recentPosition?.run {
            resultText += "\n"
            resultText +=
                resources.getString(R.string.last_stone_position)
                    .format(recentPosition.row, recentPosition.col)
        }
        resultTextView.text = resultText
    }

    private fun setResultText(finishType: FinishType) {
        if (finishType == FinishType.DRAW) {
            resultTextView.text = resources.getString(R.string.result_draw)
            return
        }
        resultTextView.text =
            resources.getString(R.string.result_win).format(finishType.stone.output())
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

    private fun OmokGame.progressTurn(
        stoneImageView: ImageView,
        index: Int,
    ) {
        val position = Position(index / boardSize, index % boardSize)
        val placeType = turn(position)
        when (placeType) {
            PlaceType.BLACK_PLACE -> stoneImageView.setImageResource(R.drawable.black_stone)
            PlaceType.WHITE_PLACE -> stoneImageView.setImageResource(R.drawable.white_stone)
            PlaceType.CANNOT_PLACE -> {
                showToast(resources.getString(R.string.cannot_place_position))
                return
            }
        }

        if (isFinish) return
        setProgressText(nowOrderStone(), recentPosition())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun restartGame(omokGame: OmokGame) {
        isFinish = false
        omokGame.restart()
        stoneImageView { _, view -> view.setImageResource(0) }
        setProgressText(omokGame.nowOrderStone(), omokGame.recentPosition())
    }

    private fun stoneImageView(block: (Int, ImageView) -> Unit) {
        boardView
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                block(index, view)
            }
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
