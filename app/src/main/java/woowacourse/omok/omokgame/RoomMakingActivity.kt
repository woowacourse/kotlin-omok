package woowacourse.omok.omokgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.dao.UserDao
import woowacourse.omok.home.HomeActivity.Companion.ROOM_ID
import woowacourse.omok.util.ContextUtil.shortToast

class RoomMakingActivity : AppCompatActivity() {
    private val userDao = UserDao(this)
    private val roomDao = RoomDao(this)
    private val firstUserEdit: EditText by lazy { findViewById<EditText>(R.id.firstUserNameEdit) }
    private val secondUserEdit: EditText by lazy { findViewById<EditText>(R.id.secondUserNameEdit) }
    private val roomNameEdit: EditText by lazy { findViewById<EditText>(R.id.roomTitleEdit) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_making)

        val button = findViewById<Button>(R.id.gameStartButton)
        button.setOnClickListener { clickGameStart() }
    }

    private fun clickGameStart() {
        if (firstUserEdit.text.isNullOrBlank() or secondUserEdit.text.isNullOrBlank() or roomNameEdit.text.isNullOrBlank()) {
            shortToast("모두 입력해주세요")
            return
        }
        val roomTitle = roomNameEdit.text.toString()
        val firstUserName = firstUserEdit.text.toString()
        val secondUserName = secondUserEdit.text.toString()
        val firstUserId = makeUser(firstUserName)
        val secondUserId = makeUser(secondUserName)
        val roomId = makeRoom(roomTitle, firstUserId, secondUserId)
        goToOmokGameActivity(roomId)
    }

    private fun makeUser(userName: String): Int {
        return userDao.insertUser(userName)
    }

    private fun makeRoom(roomTitle: String, firstUserId: Int, secondUserId: Int): Int {
        return roomDao.insertRoom(roomTitle, firstUserId, secondUserId)
    }

    private fun goToOmokGameActivity(roomId: Int) {
        val intent = Intent(this, OmokGameActivity::class.java)
        intent.putExtra(ROOM_ID, roomId)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        userDao.closeDb()
        super.onDestroy()
    }
}
