package woowacourse.omok.view

import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import omok.domain.board.OmokBoard
import omok.domain.rule.OmokRules
import woowacourse.omok.R
import woowacourse.omok.db.OmokDao
import woowacourse.omok.domain.game.OmokGame
import woowacourse.omok.ioc.Container
import woowacourse.omok.view.ext.deserialize

class OmokGameActivity : AppCompatActivity() {
    private lateinit var omokBoard: OmokBoard
    private lateinit var omokRules: OmokRules
    private lateinit var omokDao: OmokDao
    private lateinit var nickname: String
    private lateinit var layout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        layout = findViewById<TableLayout>(R.id.board)
        val container = Container(layout)
        omokRules = container.omokRules
        omokDao = container.omokDao
        nickname = intent.getStringExtra("nickname")!!
        omokBoard = omokDao.findBoardByNickName(nickname)?.board?.let {
            runCatching {
                container.omokBoard.deserialize(it)
            }.getOrNull()
        } ?: container.omokBoard
        startGame()
    }

    private fun startGame() {
        OmokGame(omokBoard, layout, nickname).startGame(omokBoard.latestPlace.opponent())
    }
}
