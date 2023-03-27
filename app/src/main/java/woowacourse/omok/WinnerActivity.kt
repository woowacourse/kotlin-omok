package woowacourse.omok

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import omok.domain.player.Black
import omok.domain.player.White
import woowacourse.omok.db.OmokDBHelper
import woowacourse.omok.db.PlayerContract

class WinnerActivity : AppCompatActivity() {
    private val omokDB: SQLiteDatabase by lazy { OmokDBHelper(this).writableDatabase }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val nickname = Omok.getNickname()
        val winnerImageView = findViewById<ImageView>(R.id.winner)
        val winnerTextView = findViewById<TextView>(R.id.winner_nickname)
        when (intent.getStringExtra("winner")) {
            Black.toString() -> {
                setWinner(winnerImageView, R.drawable.black_stone, winnerTextView, nickname)
            }
            White.toString() -> {
                setWinner(winnerImageView, R.drawable.white_stone, winnerTextView, "양파 쿵야")
            }
        }

        val restart = findViewById<Button>(R.id.restart_game)
        restart.setOnClickListener {
            startNewGame(nickname)
        }
    }

    private fun setWinner(imageView: ImageView, imageResource: Int, textView: TextView, nickname: String) {
        imageView.setImageResource(imageResource)
        textView.text = "$nickname 승리"
    }

    private fun startNewGame(nickname: String) {
        PlayerContract.createRecord(omokDB, nickname)
        goToGame()
    }

    private fun goToGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
