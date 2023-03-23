package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val img = findViewById<ImageView>(R.id.stoneImg)
        val resultText = findViewById<TextView>(R.id.resultText)

        val winner = intent.getStringExtra("winner")
        if (winner == "흑")
            img.setImageResource(R.drawable.black_stone)
        else
            img.setImageResource(R.drawable.white_stone)
        resultText.text = winner

        findViewById<Button>(R.id.retryBtn).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
