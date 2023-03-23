package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
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

class RoomActivity : AppCompatActivity() {
    private val boardDb = OmokBoardDbHelper(this)

    private val omokGameListener = object : OmokGameListener {
        override fun onOmokStart() {
            Toast.makeText(applicationContext, "게임을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onBoardShow(gameState: GameState, omokPoint: OmokPoint?) {
            matrixBoard.forEach { (col, row, imageView) ->
                setStoneImage(gameState.omokBoard, imageView, OmokPoint(col + 1, row + 1))
            }
            omokPoint?.let { boardDb.insert(gameState, omokPoint, intent.getIntExtra("gameId", 0)) }
        }

        override fun onError(message: String?) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var omokController: OmokController

    private val matrixBoard get() = findViewById<TableLayout>(R.id.board)
        .children
        .filterIsInstance<TableRow>()
        .flatMapIndexed { row, tableRow ->
            tableRow.children.filterIsInstance<ImageView>()
                .mapIndexed { col, imageView -> Triple(col, row, imageView) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokController = OmokController(omokGameListener, boardDb.getGameState(intent.getIntExtra("gameId", 0)))

        addStoneListener()
        addListenerResetGameState()
        addListenerBackToRoomList()
    }

    private fun addStoneListener() {
        matrixBoard.forEach { (col, row, imageView) ->
            imageView.setOnClickListener { omokController.run(OmokPoint(col + 1, row + 1)) }
        }
    }

    private fun addListenerBackToRoomList() {
        findViewById<Button>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, RoomListActivity::class.java))
        }
    }

    private fun addListenerResetGameState() {
        findViewById<Button>(R.id.resetButton).setOnClickListener {
//            boardDb.onUpgrade(boardDb.readableDatabase, 1, 1)
            boardDb.delete(intent.getIntExtra("gameId", 0))
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
