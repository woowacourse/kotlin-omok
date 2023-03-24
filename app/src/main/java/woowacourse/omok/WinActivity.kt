package woowacourse.omok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class WinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        val winner = intent.getStringExtra("winner")
        val stoneImageView = findViewById<ImageView>(R.id.imageView_stone)
        val stoneTextView = findViewById<TextView>(R.id.textView_stoneColor)
        val restartButton = findViewById<Button>(R.id.button_restart)

        when (winner) {
            StoneColor.STONE_COLOR_BLACK.color -> {
                stoneImageView.setImageResource(R.drawable.black_stone)
                stoneTextView.text = "흑"
            }
            StoneColor.STONE_COLOR_WHITE.color -> {
                stoneImageView.setImageResource(R.drawable.white_stone)
                stoneTextView.text = "백"
            }
        }

        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}