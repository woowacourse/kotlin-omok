package woowacourse.omok.result

import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import domain.Position
import domain.domain.state.State
import woowacourse.omok.R
import woowacourse.omok.data.dao.BoardDao
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.home.HomeActivity.Companion.ROOM_ID
import woowacourse.omok.omokgame.OmokGameActivity.Companion.ROOM_TITLE
import woowacourse.omok.omokgame.OmokGameActivity.Companion.WINNER_NAME
import woowacourse.omok.omokgame.OmokGameUtil
import woowacourse.omok.omokgame.OmokGameUtil.loopBoardTable

class ResultActivity : AppCompatActivity() {
    private val boardDao = BoardDao(this)
    private val roomDao = RoomDao(this)
    private val roomId by lazy { intent.getIntExtra(ROOM_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        getRoom()
        clickFinish()
    }

    private fun getRoom() {
        val winnerName = intent.getStringExtra(WINNER_NAME) ?: ""
        val roomTitle = intent.getStringExtra(ROOM_TITLE) ?: ""
        val boardState = boardDao.readBoard(roomId)
        initView(roomTitle, winnerName, boardState)
    }

    private fun initView(roomTitle: String, winnerName: String, state: State) {
        findViewById<TextView>(R.id.resultTitleText).text = roomTitle
        findViewById<TextView>(R.id.resultWinnerText).text = winnerName
        val resultBoardTable = findViewById<TableLayout>(R.id.resultBoardTable)
        drawStone(resultBoardTable, state)
    }

    private fun drawStone(boardTable: TableLayout, state: State) {
        boardTable.loopBoardTable { position: Position, imageView ->
            val resId = OmokGameUtil.matchColor(state.stones[position.y, position.x])
            if (resId != null) imageView.setImageResource(resId)
        }
    }

    private fun clickFinish() {
        roomDao.deleteRoom(roomId)
        findViewById<Button>(R.id.resultFinishButton).setOnClickListener { finish() }
    }

    override fun onDestroy() {
        roomDao.deleteRoom(roomId)
        super.onDestroy()
    }
}
