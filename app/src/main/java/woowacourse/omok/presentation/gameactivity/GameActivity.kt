package woowacourse.omok.presentation.gameactivity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import domain.domain.CoordinateState
import domain.domain.OmokGame
import domain.domain.Position
import domain.domain.ProgressState
import domain.library.combinerule.CombinedRuleAdapter
import woowacourse.omok.R
import woowacourse.omok.presentation.finishactivity.FinishActivity

class GameActivity : AppCompatActivity() {

    private val omokGame: OmokGame = OmokGame(gameRule = CombinedRuleAdapter())

    private lateinit var turnView: TurnView
    private lateinit var lastPositionView: LastPositionView
    private lateinit var messageView: MessageView
    private lateinit var boardView: GameBoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initViewId()
    }

    private fun initViewId() {
        turnView = TurnView(findViewById(R.id.tv_turn), omokGame)
        lastPositionView = LastPositionView(findViewById(R.id.tv_last_position), omokGame)
        messageView = MessageView(findViewById(R.id.tv_message), omokGame)
        boardView = GameBoardView(findViewById<TableLayout>(R.id.board), ::coordinateClickListener)
    }

    private fun coordinateClickListener(imageView: ImageView, rowIndex: Int, columIndex: Int) {
        val turnStoneColor = omokGame.turn
        if (progressGame(
                Position(rowIndex, columIndex)
            )
        ) {
            setViewState(turnStoneColor, imageView)
        }
    }

    private fun setViewState(
        turnStoneColor: CoordinateState,
        imageView: ImageView
    ) {
        if (turnStoneColor == CoordinateState.BLACK) {
            imageView.setImageResource(R.drawable.black_stone)
            turnView.turn = omokGame.turn
            lastPositionView.lastPosition = omokGame.board.lastPosition
        }
        if (turnStoneColor == CoordinateState.WHITE) {
            imageView.setImageResource(R.drawable.white_stone)
            turnView.turn = omokGame.turn
            lastPositionView.lastPosition = omokGame.board.lastPosition
        }
    }

    private fun progressGame(position: Position): Boolean =
        when (omokGame.progressTurn(position)) {
            ProgressState.ERROR -> {
                messageView.printError()
                false
            }
            ProgressState.END -> {
                finishGame(omokGame.turn)
                true
            }
            ProgressState.CONTINUE -> {
                messageView.printRequestPosition()
                true
            }
        }

    private fun finishGame(winner: CoordinateState) {
        val intent = Intent(this, FinishActivity::class.java).apply {
            putExtra("winner", winner)
            putExtra("board", omokGame.board.boardState)
        }
        startActivity(intent)
        finish()
    }
}
