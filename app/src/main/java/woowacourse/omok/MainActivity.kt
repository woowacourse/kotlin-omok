package woowacourse.omok

import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import omok.domain.board.OmokBoard
import omok.domain.game.OmokGame
import omok.domain.place.OmokStones
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.event.OmokEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val board = findViewById<TableLayout>(R.id.board)
        startGame(board)
    }

    private fun startGame(board: TableLayout) {
        val rules =
            object : OmokRules {
                override val rules = listOf(RenjuRule(DfsRenjuFinder))
            }
        val omokBoard = OmokBoard(OmokStones(), rules)
        Thread {
            OmokGame(omokBoard, rules).startGame(OmokEventListener(board, this))
        }.start()
    }
}
