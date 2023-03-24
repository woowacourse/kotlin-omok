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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        val resetBtn = findViewById<Button>(R.id.retry)
        val winImage = findViewById<ImageView>(R.id.winImage)
        val winWho = findViewById<TextView>(R.id.winWho)

        val color = intent.getStringExtra(OmokConstract.TABLE_COLUMN_COLOR)
        if (color == Turn.Black.color) {
            winImage.setImageResource(R.drawable.black_stone)
            winWho.text = "흑"
        }
        if (color == Turn.White.color) {
            winImage.setImageResource(R.drawable.white_stone)
            winWho.text = "백"
        }

        resetBtn.setOnClickListener {
            DBController(OmokDBHelper(this).writableDatabase).deleteDB()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        DBController(OmokDBHelper(this).writableDatabase).deleteDB()
        super.onDestroy()
    }
}
