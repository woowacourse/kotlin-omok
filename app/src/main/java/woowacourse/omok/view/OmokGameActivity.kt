package woowacourse.omok.view

import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Empty
import omok.domain.place.OmokStones
import omok.domain.place.Protected
import omok.domain.place.White
import omok.domain.rule.OmokRules
import woowacourse.omok.R
import woowacourse.omok.db.OmokDao
import woowacourse.omok.domain.game.OmokGame
import woowacourse.omok.ioc.Container

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
        omokBoard = omokDao.findBoardByNickName(nickname)?.board?.toOmokBoard() ?: container.omokBoard
        startGame()
    }

    private fun startGame() {
        OmokGame(omokBoard, layout, nickname).startGame(omokBoard.latestPlace.opponent())
    }

    private fun String.toOmokBoard(): OmokBoard {
        val deserialized =
            this.split("/").map {
                val info = it.split("|")
                when (info[0]) {
                    "B" -> Black(info[1].toInt(), info[2].toInt())
                    "W" -> White(info[1].toInt(), info[2].toInt())
                    "P" -> Protected(info[1].toInt(), info[2].toInt())
                    "E" -> Empty(info[1].toInt(), info[2].toInt())
                    else -> throw IllegalStateException("not supported")
                }
            }
        val omokStones = OmokStones(deserialized)
        return OmokBoard(omokStones, omokRules, deserialized[deserialized.size - 1])
    }
}
