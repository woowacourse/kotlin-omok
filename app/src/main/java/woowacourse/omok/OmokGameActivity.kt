package woowacourse.omok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import domain.domain.Board

class OmokGameActivity : AppCompatActivity() {
    private val board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.omok_game_activity)

        OmokGameController(board, this).run()
    }
}
