package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.Board
import domain.stone.BlackStone
import domain.stone.Stone
import domain.stone.Stones
import domain.stone.WhiteStone
import woowacourse.omok.database.OmokDB


class MainActivity : AppCompatActivity() {

    private lateinit var boardViewsController: BoardViewsController
    private lateinit var omokDB: OmokDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardViews: List<ImageView> = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()

        omokDB = OmokDB(this)
        boardViewsController = BoardViewsController(this, boardViews)

        initBoardView()
        boardViewsController.setPutStoneOnClickListener(omokDB)


        val restartButton = findViewById<Button>(R.id.button_restart)
        restartButton.setOnClickListener {
            boardViewsController.resetView()
            omokDB.deleteDB()
            boardViewsController.board = Board(Stones(setOf()))
        }
    }

    private fun initBoardView() {
        //db에 저장된 돌들을 오목판에 세팅
        val dbStones = omokDB.getStoneData()
        val stones = mutableSetOf<Stone>()
        dbStones.forEach {
            val (omokPoint, stoneColor) = it
            boardViewsController.setDBStoneOnView(omokPoint, stoneColor)
            val (x, y) = omokPoint
            val stone: Stone =
                if (stoneColor == StoneColor.BLACK.english) BlackStone(x, y) else WhiteStone(x, y)
            stones.add(stone)
        }
        boardViewsController.board = Board(Stones(stones))
    }
}
