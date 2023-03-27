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


        val stoneImageView = findViewById<ImageView>(R.id.imageView_stone)
        val stoneTextView = findViewById<TextView>(R.id.textView_stoneColor)
        when (intent.getStringExtra("winner")) {
            StoneColor.BLACK.english -> {
                stoneImageView.setImageResource(StoneColor.BLACK.imageResource)
                stoneTextView.text = StoneColor.BLACK.korean
            }
            StoneColor.WHITE.english -> {
                stoneImageView.setImageResource(StoneColor.WHITE.imageResource)
                stoneTextView.text = StoneColor.WHITE.korean
            }
        }

        val restartButton = findViewById<Button>(R.id.button_restart)
        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}