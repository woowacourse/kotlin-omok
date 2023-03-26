package woowacourse.omok.room

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R
import woowacourse.omok.data.Player

class RestartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restart)

        val player = intent.getSerializableExtra("winner") as Player

        findViewById<TextView>(R.id.restart_text).text = "${player.name}님의 승리입니다."
        findViewById<ImageView>(R.id.restart_image).setImageResource(player.profile)
        findViewById<Button>(R.id.restart_button).setOnClickListener {
            finish()
        }
    }
}
