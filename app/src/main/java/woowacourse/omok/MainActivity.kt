package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.Board
import domain.rule.FourFourRule
import domain.rule.LongMokRule
import domain.rule.Referee
import domain.rule.ThreeThreeRule
import domain.stone.BlackStone
import domain.stone.Stone
import domain.stone.Stones
import domain.stone.WhiteStone

const val STONE_COLOR_VALUE_BLACK = "black"
const val STONE_COLOR_VALUE_WHITE = "white"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var omokBoard: Board
        lateinit var point: Pair<Int, Int>
        val blackReferee = Referee(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))
        val db = OmokDBHelper(this).writableDatabase
        val stones = mutableSetOf<Stone>()
        val board = findViewById<TableLayout>(R.id.board)
        val boardViews: List<ImageView> = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()

        //db 데이터 읽어오기
        val cursor = db.query(
            OmokContract.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val x =
                    getInt(getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_X_POINT))
                val y =
                    getInt(getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_Y_POINT))
                val color =
                    getString(getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_STONE_COLOR))
                boardViews
                    .forEachIndexed { index, view ->
                        if (index == ((14 - y) * 15 + x)) {
                            when (color) {
                                StoneColor.STONE_COLOR_BLACK.color -> view.setImageResource(R.drawable.black_stone)
                                StoneColor.STONE_COLOR_WHITE.color -> view.setImageResource(R.drawable.white_stone)
                            }

                        }
                    }
                val stone: Stone =
                    if (color == StoneColor.STONE_COLOR_BLACK.color) BlackStone(
                        x,
                        y
                    ) else WhiteStone(x, y)
                stones.add(stone)
            }
            close()
        }
        omokBoard = Board(Stones(stones))
        boardViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                point = calculatePoint(index)
                var stoneResource: Int? = null
                var stoneColor: String? = null
                if (omokBoard.isBlackTurn()) {
                    stoneResource = R.drawable.black_stone
                    stoneColor = StoneColor.STONE_COLOR_BLACK.color
                }
                if (omokBoard.isWhiteTurn()) {
                    stoneResource = R.drawable.white_stone
                    stoneColor = StoneColor.STONE_COLOR_WHITE.color
                }
                val result = putStoneAndReturnResult(omokBoard, blackReferee, point)
                if (result) {
                    stoneColor?.let {
                        //db 데이터 저장하기

                        val values = ContentValues()
                        values.put(OmokContract.TABLE_COLUMN_X_POINT, point.first)
                        values.put(OmokContract.TABLE_COLUMN_Y_POINT, point.second)
                        values.put(OmokContract.TABLE_COLUMN_STONE_COLOR, stoneColor)
                        db.insert(OmokContract.TABLE_NAME, null, values)
                    }
                    stoneResource?.let { view.setImageResource(it) }
                }
                if (omokBoard.isFinished()) {
                    val intent = Intent(this, WinActivity::class.java)
                    val winner: String =
                        if (omokBoard.isBlackWin()) StoneColor.STONE_COLOR_BLACK.color else StoneColor.STONE_COLOR_WHITE.color
                    intent.putExtra("winner", winner)
                    startActivity(intent)
                    db.delete(OmokContract.TABLE_NAME, null, null)
                    finish()
                }
            }
        }
    }

    private fun calculatePoint(index: Int): Pair<Int, Int> {
        val x: Int = index % 15
        val y: Int = 14 - index / 15
        return Pair(x, y)
    }

    private fun putStoneAndReturnResult(
        board: Board,
        blackReferee: Referee,
        point: Pair<Int, Int>
    ): Boolean {
        runCatching {
            board.put(
                point,
                blackReferee
            )
        }.onFailure {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
