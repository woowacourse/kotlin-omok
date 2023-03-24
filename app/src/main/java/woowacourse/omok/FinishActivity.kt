package woowacourse.omok

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.domain.CoordinateState

class FinishActivity : AppCompatActivity() {

    private lateinit var winner: CoordinateState
    private lateinit var tvWinnerMessage: TextView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        initExtraData()
        initViewId()
        initWinnerMessage()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initExtraData() {
        winner = intent.getParcelableExtra("winner", CoordinateState::class.java)
            ?: throw IllegalStateException()
    }

    private fun initViewId() {
        tvWinnerMessage = findViewById(R.id.tv_winner_message)
    }

    private fun initWinnerMessage() {
        tvWinnerMessage.text = "${winner.name} 가 승리하였습니다"
    }
}

