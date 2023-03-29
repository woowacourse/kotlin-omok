package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val resetButton = findViewById<Button>(R.id.resetButton)

        val db = OmokDbHelper(this)

        resetButton.setOnClickListener { db.deleteData() }
    }
}
