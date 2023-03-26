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
import domain.stone.BlackStone
import domain.stone.Stone
import domain.stone.Stones
import domain.stone.WhiteStone
import woowacourse.omok.database.OmokDB

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
            val (omokPoint, stoneColor) = it
            setDBStoneOnView(omokPoint, stoneColor)
            val (x, y) = omokPoint
            val stone: Stone =
                if (stoneColor == StoneColor.BLACK.english) BlackStone(x, y) else WhiteStone(x, y)
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

    private fun setDBStoneOnView(point: Pair<Int, Int>, stoneColor: String) {
        val (x, y) = point
        boardViews
            .forEachIndexed { index, view ->
                if (index == convertPointIntoIndex(x, y)) {
                    setStoneImageView(stoneColor, view)
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
                val point: Pair<Int, Int> = calculatePoint(index)
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

    private fun calculatePoint(index: Int): Pair<Int, Int> {
        val x: Int = index % 15
        val y: Int = 14 - index / 15
        return Pair(x, y)
    }

    private fun getStoneResource(omokBoard: Board): Pair<Int?, String?> {
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

    private fun convertPointIntoIndex(x: Int, y: Int): Int = ((14 - y) * 15 + x)
}