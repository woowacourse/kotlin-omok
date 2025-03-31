package woowacourse.omok.view.activity

import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import omok.domain.board.OmokBoard
import omok.domain.place.OmokStones
import omok.domain.rule.OmokRule
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.event.OmokEventListener
import woowacourse.omok.R
import woowacourse.omok.dao.OmokDaoImpl
import woowacourse.omok.dao.OmokDbHelper
import woowacourse.omok.domain.game.OmokGame
import woowacourse.omok.view.OmokView
import woowacourse.omok.view.ext.deserialize

class OmokGameActivity : AppCompatActivity() {
    private lateinit var nickname: String
    private lateinit var layout: TableLayout
    private val dao = OmokDaoImpl(OmokDbHelper(this))
    private val omokBoard =
        OmokBoard(
            OmokStones(),
            object : OmokRules {
                override val rules: List<OmokRule>
                    get() = listOf(RenjuRule(DfsRenjuFinder))
            },
        )

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

        nickname = intent.getStringExtra("nickname")!!
        val loadedBoard =
            dao.findBoardByNickName(nickname)?.let {
                runCatching {
                    omokBoard.deserialize(it.board)
                }.getOrNull()
            } ?: omokBoard
        startGame(loadedBoard)
    }

    private fun startGame(loadedBoard: OmokBoard) {
        OmokGame(
            loadedBoard,
            nickname,
            dao,
            OmokEventListener(OmokView(layout)),
        ).startGame(loadedBoard.latestPlace.opponent())
    }
}
