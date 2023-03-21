package woowacourse.omok

import android.content.ContentValues
import android.os.Bundle
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
import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_COL
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_ROW
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_STONE
import woowacourse.omok.OmokBoardConstract.TABLE_NAME_OMOK_BOARD

class MainActivity : AppCompatActivity() {
    private val boardDb = OmokBoardDbHelper(this)

    private val omokGameListener = object : OmokGameListener {
        override fun onOmokStart() {
            Toast.makeText(applicationContext, "게임을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onBoardShow(omokBoard: OmokBoard, omokPoint: OmokPoint?) {
            matrixBoard.forEach { (col, row, imageView) ->
                setStoneImage(omokBoard, imageView, OmokPoint(col + 1, row + 1))
            }
            saveOmokBoard(omokBoard, omokPoint)
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
            tableRow.children.filterIsInstance<ImageView>().mapIndexed { col, imageView -> Triple(col, row, imageView) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokController = OmokController(omokGameListener, loadOmokBoard())

        matrixBoard.forEach { (col, row, imageView) ->
            imageView.setOnClickListener { omokController.run(OmokPoint(col + 1, row + 1)) }
        }
    }

    private fun loadOmokBoard(): OmokBoard {
        val rDb = boardDb.readableDatabase
        val cursor = rDb.query(
            TABLE_NAME_OMOK_BOARD,
            arrayOf(TABLE_COLUMN_OMOK_COL, TABLE_COLUMN_OMOK_ROW, TABLE_COLUMN_OMOK_STONE),
            null,
            null,
            null,
            null,
            null,
        )

        val omokMap = mutableMapOf<OmokPoint, StoneState>()
        with(cursor) {
            while (moveToNext()) {
                val col = getInt(getColumnIndexOrThrow(TABLE_COLUMN_OMOK_COL))
                val row = getInt(getColumnIndexOrThrow(TABLE_COLUMN_OMOK_ROW))
                val stone = getInt(getColumnIndexOrThrow(TABLE_COLUMN_OMOK_STONE))

                omokMap[OmokPoint(col, row)] = when (stone) {
                    1 -> BlackStoneState
                    2 -> WhiteStoneState
                    else -> EmptyStoneState
                }
            }
        }

        return OmokBoard(omokMap)
    }

    private fun saveOmokBoard(omokBoard: OmokBoard, omokPoint: OmokPoint?) {
        val wDb = boardDb.writableDatabase

        omokPoint?.let {
            val values = ContentValues()
            values.put(TABLE_COLUMN_OMOK_COL, omokPoint.x.value)
            values.put(TABLE_COLUMN_OMOK_ROW, omokPoint.y.value)
            values.put(
                TABLE_COLUMN_OMOK_STONE,
                when (omokBoard[omokPoint]) {
                    BlackStoneState -> 1
                    WhiteStoneState -> 2
                    else -> 0
                },
            )

            wDb.insert(TABLE_NAME_OMOK_BOARD, null, values)
        }
    }
    private fun setStoneImage(omokBoard: OmokBoard, imageView: ImageView, omokPoint: OmokPoint) {
        when (omokBoard[omokPoint]) {
            BlackStoneState -> imageView.setImageResource(R.drawable.black_stone)
            WhiteStoneState -> imageView.setImageResource(R.drawable.white_stone)
            else -> null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        boardDb.close()
    }
}
