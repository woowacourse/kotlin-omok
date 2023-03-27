package woowacourse.omok.roommaking

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.dao.UserDao
import woowacourse.omok.omokgame.OmokGameActivity
import woowacourse.omok.util.ContextUtil.shortToast

class RoomMakingActivity : AppCompatActivity() {

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
        val userDao = UserDao(this)
        val roomDao = RoomDao(this)
        return roomDao.insertRoom(
            roomTitle,
            userDao.insertUser(firstUserName),
            userDao.insertUser(secondUserName),
        ).also {
            roomDao.closeDb()
            userDao.closeDb()
        }
    }

    private fun goToOmokGameActivity(roomId: Int) {
        startActivity(OmokGameActivity.getIntent(this, roomId))
        finish()
    }
}
