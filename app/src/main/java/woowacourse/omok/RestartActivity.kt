package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RestartActivity : AppCompatActivity() {
    private val winnerStoneImage by lazy { findViewById<ImageView>(R.id.winner_stone_image) }
    private val winnerNameText by lazy { findViewById<TextView>(R.id.winner_stone_text) }
    private val restartButton by lazy { findViewById<Button>(R.id.restart_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restart)
        initRestart()
    }

    private fun initRestart() {
        val winnerStoneStr = intent.getStringExtra("winner")
        winnerNameText.text = "우승자는 ${winnerStoneStr}입니다"
        when (winnerStoneStr) {
            "흑" -> winnerStoneImage.setImageResource(R.drawable.black_stone)
            "백" -> winnerStoneImage.setImageResource(R.drawable.white_stone)
        }
        restartButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }
}
