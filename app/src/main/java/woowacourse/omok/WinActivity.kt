package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import omok.domain.state.Turn
import woowacourse.omok.database.DBController
import woowacourse.omok.database.OmokConstract
import woowacourse.omok.database.OmokDBHelper

class WinActivity : AppCompatActivity() {
    lateinit var dBController: DBController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        val retryBtn = findViewById<Button>(R.id.retry)
        val winImage = findViewById<ImageView>(R.id.winImage)
        val winWho = findViewById<TextView>(R.id.winWho)
        dBController = DBController(OmokDBHelper(this).writableDatabase)

        displayWinner(winImage, winWho)
        retryGame(retryBtn)
    }

    private fun displayWinner(winImage: ImageView, winWho: TextView) {
        val color = intent.getStringExtra(OmokConstract.TABLE_COLUMN_COLOR)
        if (color == Turn.Black.color) {
            winImage.setImageResource(R.drawable.black_stone)
            winWho.text = "흑"
        }
        if (color == Turn.White.color) {
            winImage.setImageResource(R.drawable.white_stone)
            winWho.text = "백"
        }
    }

    private fun retryGame(retryBtn: Button) {
        retryBtn.setOnClickListener {
            dBController.deleteDB()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        dBController.deleteDB()
        dBController.closeDB()
        super.onDestroy()
    }
}
