package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.SampleData.generateSampleData
import woowacourse.omok.data.Player
import woowacourse.omok.dbHelper.OmokBoardDbHelper
import woowacourse.omok.dbHelper.OmokPlayerDbHelper
import woowacourse.omok.dbHelper.OmokRoomDbHelper
import woowacourse.omok.roomList.RoomListActivity

class MainActivity : AppCompatActivity() {
    private val playerDb = OmokPlayerDbHelper(this)
    private val BoardDb = OmokBoardDbHelper(this)
    private val roomDb = OmokRoomDbHelper(this)
    private val button by lazy { findViewById<Button>(R.id.main_button) }
    private val nameText by lazy { findViewById<EditText>(R.id.main_edit_text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDb()
        setContentView(R.layout.activity_main)
        addListenerOnButton()
    }

    private fun createDb() {
        playerDb.onCreate(playerDb.writableDatabase)
        BoardDb.onCreate(BoardDb.writableDatabase)
        roomDb.onCreate(roomDb.writableDatabase)
    }
    private fun addListenerOnButton() {
        button.setOnClickListener {
            val player = playerDb.getPlayer(nameText.text.toString()) ?: createPlayer(nameText)

            if (player == null) {
                Toast.makeText(this, getString(R.string.main_message_input_name), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startRoomListActivity(player)
        }
    }

    private fun createPlayer(nameText: EditText): Player? =
        Player(name = nameText.text.toString(), profile = R.drawable.player_ark)
            .run { playerDb.insert(this) }
            .let { playerDb.getPlayer(it) }

    private fun startRoomListActivity(player: Player) =
        startActivity(
            Intent(this, RoomListActivity::class.java).apply {
                putExtra("player", player)
            },
        )

    override fun onPause() {
        super.onPause()
        generateSampleData(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        playerDb.close()
        roomDb.close()
        BoardDb.close()
    }
}
