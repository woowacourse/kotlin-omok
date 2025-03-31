package woowacourse.omok.view.activity

import android.content.Context
import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import omok.domain.board.OmokBoard
import omok.domain.place.Empty
import omok.domain.place.OmokStones
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.event.OmokEventListener
import woowacourse.omok.R
import woowacourse.omok.dao.Dao
import woowacourse.omok.dao.OmokDaoImpl
import woowacourse.omok.dao.OmokDbHelper
import woowacourse.omok.domain.game.OmokGame
import woowacourse.omok.dto.OmokGameDto
import woowacourse.omok.view.OmokView

class OmokGameActivity : AppCompatActivity() {
    private lateinit var layout: TableLayout
    private lateinit var nickname: String
    private lateinit var dao: Dao
    private val omokRules =
        object : OmokRules {
            override val rules = listOf(RenjuRule(DfsRenjuFinder))
        }
    private val omokBoard =
        OmokBoard(
            OmokStones(),
            omokRules,
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
        nickname = intent.getStringExtra(MainActivity.NICKNAME_KEY) ?: throw IllegalArgumentException(ERR_NICKNAME_NOT_PROVIDED)
        dao = OmokDaoImpl(getHelper(this))

        val latestPlace = dao.findLatestStoneByNickName(nickname)?.latestStone ?: Empty.dummy()
        val loadedBoard =
            dao.findBoardByNickName(nickname)?.let {
                OmokBoard(OmokStones(it.places), omokRules, latestPlace)
            } ?: omokBoard
        val omokGameDto = OmokGameDto(nickname, loadedBoard)
        startGame(omokGameDto)
    }

    private fun startGame(omokGameDto: OmokGameDto) {
        OmokGame(
            omokGameDto,
            dao,
            OmokEventListener(OmokView(layout)),
        ).startGame(omokGameDto.board.latestPlace.opponent())
    }

    companion object {
        fun getHelper(context: Context): OmokDbHelper {
            if (dbHelper == null) {
                dbHelper = OmokDbHelper(context.applicationContext)
            }
            return dbHelper!!
        }

        const val ERR_NICKNAME_NOT_PROVIDED = "닉네임이 제공되지 않았습니다"
        private var dbHelper: OmokDbHelper? = null
    }
}
