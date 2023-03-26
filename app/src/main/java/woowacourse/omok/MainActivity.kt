package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.SampleData.generateSampleData
import woowacourse.omok.data.Player
import woowacourse.omok.dbHelper.OmokPlayerDbHelper
import woowacourse.omok.roomList.RoomListActivity

class MainActivity : AppCompatActivity() {
    val playerDb = OmokPlayerDbHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateSampleData(this)
        setContentView(R.layout.activity_main)
        addListenerOnButton()
    }

    private fun addListenerOnButton() {
        val button = findViewById<Button>(R.id.main_button)
        button.setOnClickListener {
            val nameText = findViewById<EditText>(R.id.main_edit_text)
            val player = playerDb.getPlayer(nameText.text.toString())

            when (player) {
                null -> nameText.error = "존재하지 않는 플레이어입니다."
                else -> startRoomListActivity(player)
            }
        }
    }

    private fun startRoomListActivity(player: Player) =
        startActivity(
            Intent(this, RoomListActivity::class.java).apply {
                putExtra("player", player)
            },
        )

    override fun onDestroy() {
        super.onDestroy()
        playerDb.close()
    }
}
