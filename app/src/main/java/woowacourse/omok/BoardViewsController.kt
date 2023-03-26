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

    private var board = Board(Stones(setOf()))

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
        board = Board(Stones(stones))

        //뷰 클릭시 돌을 놓는 리스너 설정
        setPutStoneOnClickListener()
    }

    fun resetView() {
        boardViews.forEach { view ->
            view.setImageResource(0)
        }
        board = Board(Stones(setOf()))
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
                val (stoneResource: Int?, stoneColor: String?) = getStoneResource(board)
                val blackReferee = Referee(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))
                if (putStoneAndReturnResult(board, blackReferee, point)) {
                    stoneColor?.let {
                        //db 데이터 저장하기
                        omokDB.insertStoneData(point, stoneColor)
                    }
                    stoneResource?.let { view.setImageResource(it) }
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

    private fun getStoneResource(omokBoard: Board): Pair<Int?, String?> {
        if (omokBoard.isBlackTurn()) {
            val stoneResource = R.drawable.black_stone
            val stoneColor = StoneColor.BLACK.english
            return stoneResource to stoneColor
        }
        if (omokBoard.isWhiteTurn()) {
            val stoneResource = R.drawable.white_stone
            val stoneColor = StoneColor.WHITE.english
            return stoneResource to stoneColor
        }
        return Pair(null, null)
    }

    private fun putStoneAndReturnResult(
        board: Board,
        blackReferee: Referee,
        point: Point
    ): Boolean {
        runCatching {
            board.put(
                point,
                blackReferee
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