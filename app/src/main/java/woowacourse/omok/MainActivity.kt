package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.database.DatabaseHelper
import woowacourse.omok.database.GameDao
import woowacourse.omok.database.GameDaoImpl
import woowacourse.omok.model.board.CoordsNumber
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.view.OutputView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var gameDao: GameDao
    private val outputView = OutputView()
    val omok = OmokGame(this.outputView)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val board = findViewById<TableLayout>(R.id.board)
        dbHelper = DatabaseHelper(this)
        gameDao = GameDaoImpl(dbHelper)
        omok.board.gameBoard = gameDao.loadGame()
        val loadedStoneType = gameDao.loadCurrentStone()
        if (loadedStoneType != -1) { // -1이 아니면 유효한 차례 정보가 존재함
            omok.currentStone = Stone.entries.toTypedArray()[loadedStoneType]
        }
        updateUI()

        val button = findViewById<Button>(R.id.resetButton)
        button.setOnClickListener {
            resetBoard()
        }
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rows ->
            rows.children.filterIsInstance<ImageView>().forEachIndexed { columIndex, view ->
                view.setOnClickListener {
                    val forbiddenPositions =
                        omok.renjuGameRule.findForbiddenPositions(omok.currentStone)
                    if (omok.board.isRunning()) {
                        if (requestPlayerMove(
                                omok.currentStone,
                                CoordsNumber(rowIndex),
                                CoordsNumber(columIndex),
                                forbiddenPositions
                            )
                        ) {
                            updateUI()
                        }
                    }
                    gameDao.saveGame(omok.board.gameBoard)
                    gameDao.saveCurrentStone(omok.currentStone.ordinal)
                }
            }
        }
    }

    fun resetBoard() {
        omok.resetGame()
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM GameBoard")
        updateUI()
    }

    private fun requestPlayerMove(
        currentStone: Stone,
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        forbiddenPositions: List<Position>,
    ): Boolean {
        if (rowCoords != null && columnCoords != null &&
            !omok.board.isMoveForbidden(
                rowCoords,
                columnCoords,
                forbiddenPositions,
            ) && !omok.board.isNotEmpty(rowCoords, columnCoords)
        ) {
            omok.placeStone(Position(rowCoords, columnCoords), currentStone)
            outputView.printBoard(
                omok.board.gameBoard,
                forbiddenPositions
            )
            return true
        } else {
            outputView.printForbiddenMoveMessage()
            return false
        }
    }

    private fun updateUI() {
        val board = findViewById<TableLayout>(R.id.board)
        val forbiddenPositions =
            omok.renjuGameRule.findForbiddenPositions(omok.currentStone)
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rows ->
            rows.children.filterIsInstance<ImageView>().forEachIndexed { columnIndex, view ->
                if (Position(
                        CoordsNumber(columnIndex),
                        CoordsNumber(rowIndex)
                    ) in forbiddenPositions
                ) {
                    view.setImageResource(R.drawable.x_mark)
                } else {
                    val stone = omok.board.gameBoard[columnIndex][rowIndex]
                    val resId = when (stone) {
                        Stone.BLACK -> R.drawable.black_stone
                        Stone.WHITE -> R.drawable.white_stone
                        else -> 0
                    }
                    if (resId != 0) view.setImageResource(resId)
                    else view.setImageDrawable(null)
                }
            }
        }
    }
}
