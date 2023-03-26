package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import omok.domain.player.Stone
import omok.domain.player.changeToStone

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val img = findViewById<ImageView>(R.id.stoneImg)
        val resultText = findViewById<TextView>(R.id.resultText)

        val winner = intent.getStringExtra("winner")?.changeToStone()
        if (winner == Stone.BLACK)
            img.setImageResource(R.drawable.black_stone)
        else
            img.setImageResource(R.drawable.white_stone)
        resultText.text = changeToWord(winner)

        findViewById<Button>(R.id.retryBtn).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun changeToWord(winner: Stone?) = if (winner == Stone.BLACK) BLACK else WHITE

    companion object {
        private const val BLACK = "흑"
        private const val WHITE = "백"
    }
}
