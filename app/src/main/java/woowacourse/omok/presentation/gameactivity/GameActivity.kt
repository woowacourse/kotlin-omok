package woowacourse.omok.presentation.gameactivity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import domain.domain.*
import domain.library.combinerule.CombinedRuleAdapter
import woowacourse.omok.R
import woowacourse.omok.data.db.OmokDbHelperSimplify
import woowacourse.omok.data.db.entity.GameSimplify
import woowacourse.omok.presentation.finishactivity.FinishActivity
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity() {

    private val omokDbHelperSimplify = OmokDbHelperSimplify(this)
    var gameId by Delegates.notNull<Int>()
    private lateinit var omokGame: OmokGame

    private lateinit var turnView: TurnView
    private lateinit var lastPositionView: LastPositionView
    private lateinit var messageView: MessageView
    private lateinit var boardView: GameBoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        getDbGameState()
        initViewId()
    }

    private fun getDbGameState() {
        val simplifyGame = omokDbHelperSimplify.selectGame() ?: omokDbHelperSimplify.insertGame()
        gameId = simplifyGame.gameId ?: throw IllegalStateException(DB_GET_GAME_ID_ERROR)
        omokGame = OmokGame(
            board = Board(simplifyGame.board),
            gameRule = CombinedRuleAdapter(),
            initTurn = simplifyGame.turn
        )
    }

    private fun initViewId() {
        turnView = TurnView(findViewById(R.id.tv_turn), omokGame)
        lastPositionView = LastPositionView(findViewById(R.id.tv_last_position), omokGame)
        messageView = MessageView(findViewById(R.id.tv_message), omokGame)
        boardView = GameBoardView(
            findViewById<TableLayout>(R.id.board),
            omokGame.board.boardState,
            ::coordinateClickListener
        )
    }

    private fun coordinateClickListener(imageView: ImageView, rowIndex: Int, columIndex: Int) {
        val turnStoneColor = omokGame.turn
        if (progressGame(Position(rowIndex, columIndex))) {
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
            putExtra("gameId", gameId)
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        omokDbHelperSimplify.updateGame(
            GameSimplify(
                gameId = gameId,
                turn = omokGame.turn,
                board = omokGame.board.boardState
            )
        )
        super.onDestroy()
    }

    companion object {
        private const val DB_GET_GAME_ID_ERROR = "DB의 자료를 불러오는중 ID값에 문제가 생겼습니다"
    }
}
