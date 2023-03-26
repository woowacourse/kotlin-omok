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
import domain.stone.Stones
import woowacourse.omok.database.OmokDB

class BoardViewsController(private val activity: AppCompatActivity, private val boardViews:List<ImageView>) {

    var board = Board(Stones(setOf()))
    //boardViews
    // db 데이터를 읽어와서 오목판에 다시 돌을 둔다
    // 화면을 클릭할 때마다 화면에 돌을 두고, 둔 돌을 db에 저장한다.

    fun setDBStoneOnView(point: Pair<Int, Int>, stoneColor: String) {
        val (x, y) = point
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
    }

    fun setPutStoneOnClickListener(omokDB: OmokDB) {
        boardViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                val point: Pair<Int, Int> = calculatePoint(index)
                val (stoneResource: Int?, stoneColor: String?) = getStoneColor(board)
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

    fun resetView() {
        boardViews.forEach { view ->
            view.setImageResource(0)
        }
    }

    private fun calculatePoint(index: Int): Pair<Int, Int> {
        val x: Int = index % 15
        val y: Int = 14 - index / 15
        return Pair(x, y)
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

}