package woowacourse.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import rule.BlackRenjuRule
import woowacourse.omok.R
import woowacourse.omok.adapter.RenjuRuleAdapter
import woowacourse.omok.domain.Game
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

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
    }

    fun init() {
        val boardView = findViewById<TableLayout>(R.id.board)
        val renjuRule = RenjuRuleAdapter(BlackRenjuRule())
        val omokGame = Game(OmokRule(renjuRule), Stones(listOf()), Turn(StoneType.BLACK))
        boardView
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    view.tag
                    view.setImageResource(R.drawable.black_stone)
                }
            }
    }
}
