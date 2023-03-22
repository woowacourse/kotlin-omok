package woowacourse.omok

import android.content.ContentValues
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
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.BlackWin
import omok.domain.gameState.GameState
import omok.domain.gameState.WhiteTurn
import omok.domain.gameState.WhiteWin
import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState
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
            omokPoint?.let { saveGameState(gameState, omokPoint) }
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

        omokController = OmokController(omokGameListener, loadGameState())

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
            boardDb.onUpgrade(boardDb.readableDatabase, 1, 1)
            omokController = OmokController(omokGameListener)

            matrixBoard.forEach { (_, _, imageView) -> imageView.setImageResource(0) }
        }
    }

    private fun loadGameState(): GameState {
        val rDb = boardDb.readableDatabase
        val cursor = rDb.query(
            OmokBoardConstract.TABLE_NAME_OMOK_BOARD,
            arrayOf(
                OmokBoardConstract.TABLE_COLUMN_OMOK_COL,
                OmokBoardConstract.TABLE_COLUMN_OMOK_ROW,
                OmokBoardConstract.TABLE_COLUMN_OMOK_NEXT_TURN,
                OmokBoardConstract.TABLE_COLUMN_OMOK_STONE,
            ),
            null,
            null,
            null,
            null,
            null,
        )

        val omokMap = mutableMapOf<OmokPoint, StoneState>()

        with(cursor) {
            while (moveToNext()) {
                val col = getInt(getColumnIndexOrThrow(OmokBoardConstract.TABLE_COLUMN_OMOK_COL))
                val row = getInt(getColumnIndexOrThrow(OmokBoardConstract.TABLE_COLUMN_OMOK_ROW))
                val nextTurn =
                    getInt(getColumnIndexOrThrow(OmokBoardConstract.TABLE_COLUMN_OMOK_NEXT_TURN))
                val stone =
                    getInt(getColumnIndexOrThrow(OmokBoardConstract.TABLE_COLUMN_OMOK_STONE))

                omokMap[OmokPoint(col, row)] = when (stone) {
                    1 -> BlackStoneState
                    2 -> WhiteStoneState
                    else -> EmptyStoneState
                }

                if (isLast) {
                    when (nextTurn) {
                        1 -> BlackTurn(OmokBoard(omokMap))
                        2 -> BlackWin(OmokBoard(omokMap))
                        3 -> WhiteTurn(OmokBoard(omokMap))
                        4 -> WhiteWin(OmokBoard(omokMap))
                        else -> null
                    }?.let {
                        return it
                    }
                }
            }
        }

        return BlackTurn(OmokBoard(omokMap))
    }

    private fun saveGameState(gameState: GameState, omokPoint: OmokPoint) {
        val wDb = boardDb.writableDatabase

        val values = ContentValues()
        values.put(OmokBoardConstract.TABLE_COLUMN_OMOK_COL, omokPoint.x.value)
        values.put(OmokBoardConstract.TABLE_COLUMN_OMOK_ROW, omokPoint.y.value)
        values.put(
            OmokBoardConstract.TABLE_COLUMN_OMOK_NEXT_TURN,
            when (gameState) {
                is BlackTurn -> 1
                is BlackWin -> 2
                is WhiteTurn -> 3
                is WhiteWin -> 4
            },
        )
        values.put(
            OmokBoardConstract.TABLE_COLUMN_OMOK_STONE,
            when (gameState.omokBoard[omokPoint]) {
                BlackStoneState -> 1
                WhiteStoneState -> 2
                else -> 0
            },
        )

        wDb.insert(OmokBoardConstract.TABLE_NAME_OMOK_BOARD, null, values)
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
