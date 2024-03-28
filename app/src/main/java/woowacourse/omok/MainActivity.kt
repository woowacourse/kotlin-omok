package woowacourse.omok

import Controller
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.omok.model.board.CoordsNumber
import woowacourse.omok.omok.model.board.Position
import woowacourse.omok.omok.model.board.Stone
import woowacourse.omok.omok.model.omokGame.OmokGame
import woowacourse.omok.omok.model.omokGame.OmokGameState
import woowacourse.omok.omok.view.OutputView

class MainActivity : AppCompatActivity() {
    private val outputView = OutputView()
    val omok = OmokGame(outputView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val board = findViewById<TableLayout>(R.id.board)
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rows ->
            rows.children.filterIsInstance<ImageView>().forEachIndexed { columIndex, view ->
                    view.setOnClickListener {
                        if (omok.board.isRunning()) {
                        if (requestPlayerMove(
                                omok.currentStone, CoordsNumber(rowIndex), CoordsNumber(columIndex)
                            )
                        ) {
                            if (omok.currentStone != Stone.BLACK) view.setImageResource(R.drawable.black_stone)
                            else view.setImageResource(R.drawable.white_stone)
                        }
                    }
                }
            }
        }
    }

    fun start(currentStone: Stone) {
        omok.endGame(omok.startGame(currentStone))
    }

    fun requestPlayerMove(
        currentStone: Stone,
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
    ): Boolean {
        if (rowCoords != null && columnCoords != null &&
            !omok.board.isMoveForbidden(
                rowCoords,
                columnCoords,
                omok.renjuGameRule.findForbiddenPositions(currentStone),
            ) && !omok.board.isNotEmpty(rowCoords, columnCoords)
        ) {
            omok.placeStone(Position(rowCoords, columnCoords), currentStone)
            outputView.printBoard(
                omok.board.gameBoard,
                omok.board.findForbiddenPositions(currentStone)
            )
            return true
        } else {
            outputView.printForbiddenMoveMessage()
            return false
        }
    }
}
