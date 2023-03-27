package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.OmokGame
import omok.domain.board.Board
import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.board.Row
import omok.domain.player.Stone

class MainActivity : AppCompatActivity() {
    private val db by lazy { BoardDBHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardView = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
        val game = initialSetUp(boardView)

        boardView.forEachIndexed { index, view ->
            view.setOnClickListener {
                playGame(game, index, view)
            }
        }
    }

    private fun initialSetUp(boardView: Sequence<ImageView>): OmokGame {
        val board = db.getBoard()
        val turn = db.getTurn()
        insertStoneToView(boardView, board)
        return OmokGame(board, turn)
    }

    private fun insertStoneToView(boardView: Sequence<ImageView>, board: Board) {
        val boardUI = boardView.toList()
        board.positions.forEach { (position, stone) ->
            if (stone != null) {
                boardUI[changePositionToIndex(position)].setImageResource(
                    changeStoneToImg(stone)
                )
            }
        }
    }

    private fun changePositionToIndex(position: Position): Int {
        var result = 0
        Column.values().forEachIndexed { index, column ->
            if (column == position.column) result += index
        }
        result += (Row.values().size - position.row.axis - 1) * Row.values().size
        return result
    }

    private fun changeStoneToImg(stone: Stone): Int {
        return when (stone) {
            Stone.BLACK -> R.drawable.black_stone
            Stone.WHITE -> R.drawable.white_stone
        }
    }

    private fun playGame(game: OmokGame, indexPosition: Int, view: ImageView) {
        val selectedPosition = changeIndexToPosition(indexPosition)
        val isSuccess = placeStone(game, selectedPosition)
        if (isSuccess) {
            db.insertData(indexPosition, game.currentStone)
            if (checkWinner(selectedPosition, game, view)) goToResultView(game.currentStone)
            return
        }
        Toast.makeText(this, "다시 놓아주세요.", Toast.LENGTH_SHORT).show()
    }

    private fun placeStone(game: OmokGame, selectedPosition: Position): Boolean {
        return runCatching {
            game.place(selectedPosition)
        }.onFailure {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
        }.getOrElse { false }
    }

    private fun checkWinner(
        selectedPosition: Position,
        game: OmokGame,
        view: ImageView
    ): Boolean {
        view.setImageResource(changeStoneToImg(game.currentStone))
        game.checkWinner(selectedPosition)
        if (game.isFinished) return true
        game.changeTurn()
        return false
    }

    private fun goToResultView(
        currentStone: Stone
    ) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("winner", currentStone.name)
        startActivity(intent)
        db.deleteData()
        finish()
    }

    companion object {
        fun changeIndexToPosition(index: Int): Position {
            val row = Row.values().size - (index / Row.values().size) - 1
            val column = index % Column.values().size
            return Position(Pair(column, row))
        }
    }
}
