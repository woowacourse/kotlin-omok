package woowacourse.omok

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.library.RenjuRule
import omok.model.BoardCoordinate
import omok.model.BoardPosition
import woowacourse.omok.model.OmokGame
import omok.model.OmokStone
import woowacourse.omok.db.GameDatabaseHelper

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var boardViews: Array<Array<ImageView?>>
    private lateinit var dbHelper: GameDatabaseHelper

    var positions = listOf<BoardPosition>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClear = findViewById<Button>(R.id.btnClear)
        btnClear.setOnClickListener {
            clearGameBoard()
            dbHelper.clearGameState() // 게임 상태를 초기화하는 메소드를 호출합니다.
        }


        val btnSaveAndExit = findViewById<Button>(R.id.btnSaveAndExit)
        btnSaveAndExit.setOnClickListener {
            val currentState = omokGame.saveTo()
            dbHelper.saveGameState(currentState)
            finish()
        }

        dbHelper = GameDatabaseHelper(this)
        omokGame = OmokGame(RenjuRule)

        // 게임 상태 불러오기
        val savedState = dbHelper.loadGameState()
        if (savedState.isNotEmpty()) {
            Toast.makeText(this, savedState, Toast.LENGTH_SHORT).show()
            omokGame.restoreFrom(savedState)
        }

        initializeBoard()

    }

    private fun initializeBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        boardViews = Array(15) { arrayOfNulls<ImageView>(15) }

        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, viewGroup ->
            viewGroup.children.filterIsInstance<ImageView>()
                .forEachIndexed { columnIndex, imageView ->
                    boardViews[rowIndex][columnIndex] = imageView
                    updateSingleCell(imageView,rowIndex,columnIndex)
                    imageView.setOnClickListener {
                        handleStonePlacement(rowIndex, columnIndex, it)
                    }
                }
        }
    }

    private fun handleStonePlacement(row: Int, col: Int, view: View) {

        val position = BoardPosition(BoardCoordinate(row), BoardCoordinate(col))
        if (omokGame.placeStoneOnBoard(omokGame.generateNextOmokStone(position))) {
            updateSingleCell(view,row,col)
            updateForbiddenPositions()
            checkGameOver()
        }
    }


    private fun updateForbiddenPositions() {
        positions.filter { it !in omokGame.getForbiddenPositions() }.forEach { forbiddenPosition ->
            val row = forbiddenPosition.getRow()
            val col = forbiddenPosition.getColumn()
            val imageView = boardViews[row][col]

            imageView?.apply {
                setImageResource(0)
            }
        }
        positions = omokGame.getForbiddenPositions()
        positions.forEach { forbiddenPosition ->
            val row = forbiddenPosition.getRow()
            val col = forbiddenPosition.getColumn()
            val imageView = boardViews[row][col]

            imageView?.apply {
                setImageResource(R.drawable.x)
            }
        }
    }


    private fun updateSingleCell(view: View, row : Int ,col : Int) {
        (view as ImageView).setImageResource(
            when {
                omokGame.getBoard()[row][col].isWhite-> R.drawable.white_stone
                omokGame.getBoard()[row][col].isBlack -> R.drawable.black_stone
                else -> 0 // Clear the image or set to default
            }
        )
    }

    private fun checkGameOver() {
        if (!omokGame.isRunning()) {
            clearGameBoard()
            dbHelper.clearGameState() // 게임 상태를 초기화하는 메소드를 호출합니다.
            Toast.makeText(this, "게임이 종료되었습니다. 오목판이 초기화됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearGameBoard() {
        for (row in boardViews.indices) {
            for (col in boardViews[row].indices) {
                boardViews[row][col]?.setImageResource(0) // 모든 이미지 뷰를 초기화합니다.
            }
        }
        // 게임 오브젝트를 새로운 상태로 초기화합니다.
        omokGame = OmokGame(RenjuRule)
        updateForbiddenPositions()
    }
}

