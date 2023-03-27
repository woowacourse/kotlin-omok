package woowacourse.omok

import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import domain.Board
import domain.rule.FourFourRule
import domain.rule.LongMokRule
import domain.rule.Referee
import domain.rule.ThreeThreeRule
import domain.stone.*
import woowacourse.omok.database.OmokDB
import woowacourse.omok.database.StoneData

class BoardViewsController(
    private val activity: AppCompatActivity,
    private val boardViews: List<ImageView>,
    private val omokDB: OmokDB
) {

    private val blackReferee = Referee(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))
    private var board = Board(Stones(setOf()), blackReferee)

    init {
        //db에 저장된 돌들을 오목판에 세팅
        val dbStones = omokDB.getStoneData()
        val stones = mutableSetOf<Stone>()
        dbStones.forEach {
            setDBStoneOnView(it)
            val stone: Stone =
                if (it.stoneColor == StoneColor.BLACK.english) BlackStone(it.point) else WhiteStone(
                    it.point
                )
            stones.add(stone)
        }
        board = Board(Stones(stones), blackReferee)

        //뷰 클릭시 돌을 놓는 리스너 설정
        setPutStoneOnClickListener()
    }

    fun resetView() {
        boardViews.forEach { view ->
            view.setImageResource(0)
        }
        board = Board(Stones(setOf()), blackReferee)
    }

    private fun setDBStoneOnView(stoneData: StoneData) {
        boardViews
            .forEachIndexed { index, view ->
                if (index == convertPointIntoIndex(stoneData.point)) {
                    setStoneImageView(stoneData.stoneColor, view)
                }
            }
    }

    private fun setStoneImageView(stoneColor: String, view: ImageView) {
        when (stoneColor) {
            StoneColor.BLACK.english -> view.setImageResource(
                StoneColor.BLACK.imageResource
            )
            StoneColor.WHITE.english -> view.setImageResource(
                StoneColor.WHITE.imageResource
            )
        }
    }

    private fun setPutStoneOnClickListener() {
        boardViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                val point: Point = calculatePoint(index)
                val stoneColor = getCurrentStoneColor(board)
                if (putStoneAndReturnResult(board, point)) {
                    stoneColor?.let {
                        //db 데이터 저장하기
                        omokDB.insertStoneData(point, it.english)
                        view.setImageResource(it.imageResource)
                    }
                }
                manageFinished(board, omokDB)
            }
        }
    }

    private fun calculatePoint(index: Int): Point {
        val x: Int = index % 15
        val y: Int = 14 - index / 15
        return Point(x, y)
    }

    private fun getCurrentStoneColor(omokBoard: Board): StoneColor? {
        if (omokBoard.isBlackTurn()) {
            return StoneColor.BLACK
        }
        if (omokBoard.isWhiteTurn()) {
            return StoneColor.WHITE
        }
        return null
    }

    private fun putStoneAndReturnResult(
        board: Board,
        point: Point
    ): Boolean {
        runCatching {
            board.put(
                point
            )
        }.onFailure {
            Toast.makeText(activity.baseContext, it.message, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun manageFinished(omokBoard: Board, omokDB: OmokDB) {
        if (omokBoard.isFinished()) {
            val intent = Intent(activity.baseContext, WinActivity::class.java)
            val winner: String =
                if (omokBoard.isBlackWin()) StoneColor.BLACK.english else StoneColor.WHITE.english
            intent.putExtra("winner", winner)
            activity.startActivity(intent)
            omokDB.deleteDB()
            activity.finish()
        }
    }

    private fun convertPointIntoIndex(point: Point): Int = ((14 - point.y) * 15 + point.x)
}