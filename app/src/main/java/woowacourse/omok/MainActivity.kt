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

        lateinit var omokBoard: Board
        lateinit var point: Pair<Int, Int>
        val blackReferee = Referee(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))
        val omokDB = OmokDB(this)
        val stones = mutableSetOf<Stone>()
        val board = findViewById<TableLayout>(R.id.board)
        val restartButton = findViewById<Button>(R.id.button_restart)
        val boardViews: List<ImageView> = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()

        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            omokDB.deleteDB()
            finish()
        }

        //db 데이터 읽어오기
        val dbData = omokDB.getData()
        dbData.forEach {
            val (omokPoint, stoneColor) = it
            val (x, y) = omokPoint
            boardViews
                .forEachIndexed { index, view ->
                    if (index == ((14 - y) * 15 + x)) {
                        when (stoneColor) {
                            StoneColor.STONE_COLOR_BLACK.color -> view.setImageResource(R.drawable.black_stone)
                            StoneColor.STONE_COLOR_WHITE.color -> view.setImageResource(R.drawable.white_stone)
                        }

                    }
                }
            val stone: Stone =
                if (stoneColor == StoneColor.STONE_COLOR_BLACK.color) BlackStone(
                    x,
                    y
                ) else WhiteStone(x, y)
            stones.add(stone)
        }

        omokBoard = Board(Stones(stones))
        boardViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                point = calculatePoint(index)
                val (stoneResource: Int?, stoneColor: String?) = getStoneColor(omokBoard)
                val result = putStoneAndReturnResult(omokBoard, blackReferee, point)
                if (result) {
                    stoneColor?.let {
                        //db 데이터 저장하기
                        omokDB.insert(point, stoneColor)
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
                if (omokBoard.isBlackWin()) StoneColor.STONE_COLOR_BLACK.color else StoneColor.STONE_COLOR_WHITE.color
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
            stoneColor = StoneColor.STONE_COLOR_BLACK.color
        }
        if (omokBoard.isWhiteTurn()) {
            stoneResource = R.drawable.white_stone
            stoneColor = StoneColor.STONE_COLOR_WHITE.color
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
