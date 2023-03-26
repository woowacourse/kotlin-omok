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
import domain.Board
import domain.rule.FourFourRule
import domain.rule.LongMokRule
import domain.rule.Referee
import domain.rule.ThreeThreeRule
import domain.stone.BlackStone
import domain.stone.Stone
import domain.stone.Stones
import domain.stone.WhiteStone


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val boardViews: List<ImageView> = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()

        val omokDB = OmokDB(this)
        val restartButton = findViewById<Button>(R.id.button_restart)
        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            omokDB.deleteDB()
            finish()
        }

        //db 데이터 읽어오기
        val dbData = omokDB.getStoneData()
        val stones = mutableSetOf<Stone>()
        dbData.forEach {
            val (omokPoint, stoneColor) = it
            val (x, y) = omokPoint
            boardViews
                .forEachIndexed { index, view ->
                    if (index == ((14 - y) * 15 + x)) {
                        when (stoneColor) {
                            StoneColor.BLACK.english -> view.setImageResource(
                                StoneColor.BLACK.imageResource
                            )
                            StoneColor.WHITE.english -> view.setImageResource(
                                StoneColor.WHITE.imageResource
                            )
                        }

                    }
                }

            val stone: Stone =
                if (stoneColor == StoneColor.BLACK.english) BlackStone(
                    x,
                    y
                ) else WhiteStone(x, y)
            stones.add(stone)
        }

        val omokBoard = Board(Stones(stones))

        boardViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                val point: Pair<Int, Int> = calculatePoint(index)
                val (stoneResource: Int?, stoneColor: String?) = getStoneColor(omokBoard)
                val blackReferee = Referee(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))
                val result = putStoneAndReturnResult(omokBoard, blackReferee, point)
                if (result) {
                    stoneColor?.let {
                        //db 데이터 저장하기
                        omokDB.insertStoneData(point, stoneColor)
                    }
                    stoneResource?.let { view.setImageResource(it) }
                }
                manageFinished(omokBoard, omokDB)
            }
        }
    }

    private fun manageFinished(omokBoard: Board, omokDB: OmokDB) {
        if (omokBoard.isFinished()) {
            val intent = Intent(this, WinActivity::class.java)
            val winner: String =
                if (omokBoard.isBlackWin()) StoneColor.BLACK.english else StoneColor.WHITE.english
            intent.putExtra("winner", winner)
            startActivity(intent)
            omokDB.deleteDB()
            finish()
        }
    }

    private fun getStoneColor(omokBoard: Board): Pair<Int?, String?> {
        var stoneResource: Int? = null
        var stoneColor: String? = null
        if (omokBoard.isBlackTurn()) {
            stoneResource = R.drawable.black_stone
            stoneColor = StoneColor.BLACK.english
        }
        if (omokBoard.isWhiteTurn()) {
            stoneResource = R.drawable.white_stone
            stoneColor = StoneColor.WHITE.english
        }
        return Pair(stoneResource, stoneColor)
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
