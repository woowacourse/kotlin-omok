package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import omok.domain.player.Black
import omok.domain.player.White

class WinnerActivity : AppCompatActivity() {
    private lateinit var winnerImage: ImageView
    private lateinit var winner: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        winnerImage = findViewById(R.id.winner)
        winner = findViewById(R.id.winner_nickname)

        when (intent.getStringExtra("winner")) {
            Black.toString() -> setPlayerWin()
            White.toString() -> setKungYaWin()
        }

        val restart = findViewById<Button>(R.id.restart_game)
        restart.setOnClickListener {
            // val intent = Intent(this, MainActivity::class.java)
            // startActivity(intent)
            // finish()
        }
    }

    private fun setPlayerWin() {
        winnerImage.setImageResource(R.drawable.black_stone)
        val winnerNickname = getSharedPreferences("Omok", MODE_PRIVATE).getString("nickname", "닉네임")
        winner.text = "$winnerNickname 우승"
    }

    private fun setKungYaWin() {
        winnerImage.setImageResource(R.drawable.white_stone)
        winner.text = "양파 쿵야 우승"
    }
}
