package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.database.OmokConstract
import woowacourse.omok.database.OmokDBHelper

class WinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        val resetBtn = findViewById<Button>(R.id.retry)
        val winImage = findViewById<ImageView>(R.id.winImage)
        val winWho = findViewById<TextView>(R.id.winWho)

        val db = OmokDBHelper(this)
        val wDb = db.writableDatabase

        val color = intent.getStringExtra("color")
        if (color == "black") {
            winImage.setImageResource(R.drawable.black_stone)
            winWho.text = "흑"
        }
        if (color == "white") {
            winImage.setImageResource(R.drawable.white_stone)
            winWho.text = "백"
        }

        resetBtn.setOnClickListener {
            wDb.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        val db = OmokDBHelper(this)
        val wDb = db.writableDatabase
        wDb.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
        super.onDestroy()
    }
}
