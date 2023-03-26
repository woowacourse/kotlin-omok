package woowacourse.omok.room

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.controller.OmokController
import omok.domain.OmokBoard
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.domain.gameState.GameState
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import woowacourse.omok.R
import woowacourse.omok.data.Player
import woowacourse.omok.dbHelper.OmokBoardDbHelper

class RoomActivity : AppCompatActivity() {
    private val boardDb = OmokBoardDbHelper(this)
    private val gameId: Int by lazy { intent.getIntExtra("gameId", 0) }
    private val player: Player by lazy { intent.getSerializableExtra("player") as Player }

    private val omokGameListener = object : OmokGameListener {
        override fun onStartGame() {
            Toast.makeText(applicationContext, "게임을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onProgressGame(gameState: GameState, omokPoint: OmokPoint?) {
            matrixBoard.forEach { (col, row, imageView) ->
                setStoneImage(gameState.omokBoard, imageView, OmokPoint(col + 1, row + 1))
            }
            omokPoint?.let { boardDb.insert(gameState, omokPoint, gameId) }
        }

        override fun onError(message: String?) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var omokController: OmokController

    private val listeners = listOf<() -> Unit>(
        ::addStoneListener,
        ::addListenerResetGameState,
        ::addListenerBackToRoomList,
    )

    private val matrixBoard get() = findViewById<TableLayout>(R.id.board)
        .children.filterIsInstance<TableRow>().flatMapIndexed { row, tableRow ->
            tableRow.children.filterIsInstance<ImageView>().mapIndexed { col, imageView ->
                Triple(col, row, imageView)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        omokController = OmokController(omokGameListener, boardDb.getGameState(gameId))

        setUpOpposingPlayer(player)

        listeners.forEach { listener -> listener() }
    }

    private fun setUpOpposingPlayer(player: Player) = player.run {
        findViewById<ImageView>(R.id.opposing_player_image).setImageResource(profile)
        findViewById<TextView>(R.id.opposing_player_name).text = name
        findViewById<TextView>(R.id.opposing_player_score).text = "$win 승 $lose 패 $draw 무"
    }

    private fun addStoneListener() {
        matrixBoard.forEach { (col, row, imageView) ->
            imageView.setOnClickListener { omokController.run(OmokPoint(col + 1, row + 1)) }
        }
    }

    private fun addListenerBackToRoomList() {
        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }
    }

    private fun addListenerResetGameState() {
        findViewById<Button>(R.id.resetButton).setOnClickListener {
            boardDb.delete(gameId)
            omokController = OmokController(omokGameListener)

            matrixBoard.forEach { (_, _, imageView) -> imageView.setImageResource(0) }
        }
    }

    private fun setStoneImage(omokBoard: OmokBoard, imageView: ImageView, omokPoint: OmokPoint) {
        when (omokBoard[omokPoint]) {
            BlackStoneState -> imageView.setImageResource(R.drawable.black_stone)
            WhiteStoneState -> imageView.setImageResource(R.drawable.white_stone)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        boardDb.close()
    }
}
