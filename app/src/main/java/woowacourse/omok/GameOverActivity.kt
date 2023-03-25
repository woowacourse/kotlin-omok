package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val winnerTextView = findViewById<TextView>(R.id.winner_textview)
        val winner = intent.getStringExtra(MainActivity.OMOK_WINNER)
        winnerTextView.text = WINNER_MESSAGE.format(winner)

        val restartButton = findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener {
            val db = OmokDbHelper(this).writableDatabase
            db.delete(OmokContract.TABLE_NAME, null, null)

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    companion object {
        private const val WINNER_MESSAGE = "%s 승!"
    }
}
