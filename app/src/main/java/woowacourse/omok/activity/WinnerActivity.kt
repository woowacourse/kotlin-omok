package woowacourse.omok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import domain.stone.StoneType
import woowacourse.omok.R

class WinnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val restartButton = findViewById<Button>(R.id.restartButton)
        val endButton = findViewById<Button>(R.id.endButton)

        showWinnerStone()
        restartButton.setOnClickListener { restartGame() }
        endButton.setOnClickListener { endGame() }
    }

    private fun showWinnerStone() {
        val winner = findViewById<TextView>(R.id.winner)
        val winnerStone = intent.getIntExtra("winner", 0)
        when (StoneType.getStoneType(winnerStone)) {
            StoneType.BLACK -> winner.text = BLACK_STONE
            StoneType.WHITE -> winner.text = WHITE_STONE
            else -> null
        }
    }

    private fun restartGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun endGame() {
        finish()
    }

    companion object {
        const val BLACK_STONE = "흑"
        const val WHITE_STONE = "백"
    }
}
