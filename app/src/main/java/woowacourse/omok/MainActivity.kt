package woowacourse.omok

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
import omok.domain.state.WhiteStoneState

class MainActivity : AppCompatActivity() {
    private val omokGameListener = object : OmokGameListener {
        override fun onOmokStart() {
            Toast.makeText(applicationContext, "게임을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onBoardShow(omokBoard: OmokBoard) {
            matrixBoard.forEach { (col, row, imageView) ->
                setStoneImage(omokBoard, imageView, OmokPoint(col + 1, row + 1))
            }
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

        omokController = OmokController(omokGameListener)
        matrixBoard.forEach { (col, row, imageView) ->
            imageView.setOnClickListener { omokController.run(OmokPoint(col + 1, row + 1)) }
        }
    }

    private fun setStoneImage(omokBoard: OmokBoard, imageView: ImageView, omokPoint: OmokPoint) {
        when (omokBoard[omokPoint]) {
            BlackStoneState -> imageView.setImageResource(R.drawable.black_stone)
            WhiteStoneState -> imageView.setImageResource(R.drawable.white_stone)
            else -> null
        }
    }
}
