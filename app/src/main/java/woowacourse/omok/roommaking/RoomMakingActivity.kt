package woowacourse.omok.roommaking

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R
import woowacourse.omok.omokgame.OmokGameActivity
import woowacourse.omok.util.ContextUtil.shortToast

class RoomMakingActivity : AppCompatActivity() {
    private val roomMakingService = RoomMakingService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_making)

        val gameStartButton = findViewById<Button>(R.id.gameStartButton)
        gameStartButton.setOnClickListener { clickGameStart() }
    }

    private fun clickGameStart() {
        val firstUserName = findViewById<EditText>(R.id.firstUserNameEdit).text.toString()
        val secondUserName = findViewById<EditText>(R.id.secondUserNameEdit).text.toString()
        val roomTitle = findViewById<EditText>(R.id.roomTitleEdit).text.toString()

        if (firstUserName.isBlank() or secondUserName.isBlank() or roomTitle.isBlank()) {
            shortToast("모두 입력해주세요")
            return
        }
        val roomId = makeRoom(roomTitle, firstUserName, secondUserName)
        goToOmokGameActivity(roomId)
    }

    private fun makeRoom(roomTitle: String, firstUserName: String, secondUserName: String): Int {
        with(roomMakingService) {
            return insertRoom(roomTitle, firstUserName, secondUserName)
                .also { closeDb() }
        }
    }

    private fun goToOmokGameActivity(roomId: Int) {
        startActivity(OmokGameActivity.getIntent(this, roomId))
        finish()
    }
}
