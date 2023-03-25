package woowacourse.omok

import android.content.ContentValues
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

        val winnerImageView = findViewById<ImageView>(R.id.winner)
        val winnerTextView = findViewById<TextView>(R.id.winner_nickname)
        when (intent.getStringExtra("winner")) {
            Black.toString() -> {
                val nickname = Omok.sharedPref.getString("nickname", "닉네임") ?: ""
                setWinner(winnerImageView, R.drawable.black_stone, winnerTextView, nickname)
            }
            White.toString() -> {
                setWinner(winnerImageView, R.drawable.white_stone, winnerTextView, "양파 쿵야")
            }
        }

        val restart = findViewById<Button>(R.id.restart_game)
        restart.setOnClickListener {
            startNewGame(Omok.sharedPref.getString("nickname", "닉네임") ?: "")
        }
    }

    private fun setWinner(imageView: ImageView, imageResource: Int, textView: TextView, nickname: String) {
        imageView.setImageResource(imageResource)
        textView.text = "$nickname 승리"
    }

    private fun startNewGame(nickname: String) {
        createPlayer(nickname)
        goToGame()
    }

    private fun createPlayer(nickname: String) {
        val values = ContentValues()
        values.put(PlayerContract.COLUMN_NAME_NICKNAME, nickname)

        omokDB.insert(PlayerContract.TABLE_NAME, null, values)
    }

    private fun goToGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
