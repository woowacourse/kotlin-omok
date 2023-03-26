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
        setUpRestartActivity(intent.getSerializableExtra("winner") as Player)
    }

    private fun setUpRestartActivity(player: Player) = player.run {
        findViewById<ImageView>(R.id.restart_image).setImageResource(profile)
        findViewById<TextView>(R.id.restart_text).text = "${player.name}님의 승리입니다."
        findViewById<Button>(R.id.restart_button).setOnClickListener { finish() }
    }
}
