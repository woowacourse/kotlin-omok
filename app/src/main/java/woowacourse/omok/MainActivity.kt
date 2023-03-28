package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import omok.OmokGame
import woowacourse.omok.db.OmokDB

class MainActivity : AppCompatActivity() {
    private val omokStatusView: OmokStatusView by lazy {
        OmokStatusView(findViewById(R.id.tv_status))
    }
    private val omokGame: OmokGame by lazy {
        OmokGame(db.getBoard(), omokStatusView::onSuccessProcess, omokStatusView::onFailedProcess)
    }
    private val board: TableLayout by lazy {
        findViewById(R.id.board)
    }
    private val btnReset: Button by lazy {
        findViewById(R.id.btn_reset)
    }
    private val db: OmokDB by lazy {
        OmokDB(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BoardController(board, omokGame, db)

        btnReset.setOnClickListener {
            db.clearStoneInfo()
            changeActivity(WaitingRoomActivity::class.java)
        }
    }

    private fun <T> changeActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        finish()
        startActivity(intent)
    }
}
