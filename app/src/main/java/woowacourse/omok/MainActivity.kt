package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.SampleData.generateSampleData
import woowacourse.omok.dbHelper.OmokPlayerDbHelper
import woowacourse.omok.roomList.RoomListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateSampleData(this)

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.main_button).setOnClickListener {
            val name = findViewById<EditText>(R.id.main_edit_text).text.toString()
            val player = OmokPlayerDbHelper(this).getPlayer(name)

            if (player == null) {
                findViewById<EditText>(R.id.main_edit_text).error = "존재하지 않는 플레이어입니다."
                return@setOnClickListener
            }
            startActivity(
                Intent(this, RoomListActivity::class.java).apply {
                    putExtra("player", player)
                },
            )
        }
    }
}
