package woowacourse.omok.room

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R
import woowacourse.omok.data.Player

class RestartActivity : AppCompatActivity() {
    private val restartButton by lazy { findViewById<Button>(R.id.restart_button) }
    private val restartImage by lazy { findViewById<ImageView>(R.id.restart_image) }
    private val restartText by lazy { findViewById<TextView>(R.id.restart_text) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restart)
        setUpRestartActivity(intent.getSerializableExtra("winner") as Player)
    }

    private fun setUpRestartActivity(player: Player) = player.run {
        restartImage.setImageResource(profile)
        restartText.text = "${player.name}님의 승리입니다."
        restartButton.setOnClickListener { finish() }
    }
}
