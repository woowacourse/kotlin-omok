package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import omok.domain.player.Black
import omok.domain.player.White

class WinnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val winnerView = findViewById<ImageView>(R.id.winner)
        when (intent.getStringExtra("winner")) {
            Black.toString() -> winnerView.setImageResource(R.drawable.black_stone)
            White.toString() -> winnerView.setImageResource(R.drawable.white_stone)
        }
    }
}
