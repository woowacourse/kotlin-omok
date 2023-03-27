package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import omok.domain.player.Stone
import woowacourse.omok.StoneModel.toPresentation
import woowacourse.omok.StoneModel.toStone

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val img = findViewById<ImageView>(R.id.stoneImg)
        val resultText = findViewById<TextView>(R.id.resultText)

        val winner = intent.getStringExtra("winner")?.toStone()
        if (winner == Stone.BLACK)
            img.setImageResource(R.drawable.black_stone)
        else
            img.setImageResource(R.drawable.white_stone)
        resultText.text = winner?.toPresentation()

        findViewById<Button>(R.id.retryBtn).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
