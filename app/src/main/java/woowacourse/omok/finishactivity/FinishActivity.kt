package woowacourse.omok.finishactivity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import domain.domain.BoardState
import domain.domain.CoordinateState
import woowacourse.omok.R
import woowacourse.omok.util.customGetSerializable

class FinishActivity : AppCompatActivity() {

    private lateinit var winner: CoordinateState
    private lateinit var board: BoardState
    private lateinit var tvWinnerMessage: TextView
    private lateinit var finishBoardView: FinishBoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        initExtraData()
        initViewId()
        initWinnerMessage()
        initFinishBoardView()
    }

    private fun initExtraData() {
        winner = intent.customGetSerializable("winner")
            ?: throw IllegalStateException()
        board = intent.customGetSerializable("board")
            ?: throw IllegalStateException()
    }

    private fun initViewId() {
        tvWinnerMessage = findViewById(R.id.tv_winner_message)
    }

    private fun initWinnerMessage() {
        tvWinnerMessage.text = "${winner.name}의 승리"
    }

    private fun initFinishBoardView() {
        finishBoardView = FinishBoardView(findViewById(R.id.finish_board), board)
    }
}
