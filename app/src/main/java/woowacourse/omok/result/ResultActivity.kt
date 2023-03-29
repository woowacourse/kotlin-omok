package woowacourse.omok.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import domain.Position
import domain.domain.state.State
import woowacourse.omok.R
import woowacourse.omok.data.dao.BoardDao
import woowacourse.omok.home.HomeActivity.Companion.ROOM_ID
import woowacourse.omok.omokgame.OmokGameUtil
import woowacourse.omok.omokgame.OmokGameUtil.loopBoardTable

class ResultActivity : AppCompatActivity() {
    private val resultService = ResultService(this)
    private val roomId by lazy { intent.getIntExtra(ROOM_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        getRoom()
        clickFinish()
    }

    private fun getRoom() {
        val boardDao = BoardDao(this)
        val winnerName = intent.getStringExtra(WINNER_NAME) ?: ""
        val roomTitle = intent.getStringExtra(ROOM_TITLE) ?: ""
        val boardState = boardDao.readBoard(roomId)
        initView(roomTitle, winnerName, boardState)
        boardDao.closeDb()
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
        findViewById<Button>(R.id.resultFinishButton).setOnClickListener {
            resultService.deleteRoom(roomId)
            finish()
        }
    }

    override fun onDestroy() {
        resultService.deleteRoom(roomId)
        resultService.closeDb()
        super.onDestroy()
    }

    companion object {
        const val WINNER_NAME = "winnerName"
        const val ROOM_TITLE = "roomTitle"
        fun getIntent(context: Context, roomId: Int, roomTitle: String, winnerName: String) =
            Intent(context, ResultActivity::class.java).apply {
                putExtra(ROOM_ID, roomId)
                putExtra(ROOM_TITLE, roomTitle)
                putExtra(WINNER_NAME, winnerName)
            }
    }
}
