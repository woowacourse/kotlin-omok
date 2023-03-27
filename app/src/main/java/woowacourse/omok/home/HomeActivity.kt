package woowacourse.omok.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import woowacourse.omok.R
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.dao.UserDao
import woowacourse.omok.omokgame.OmokGameActivity
import woowacourse.omok.roommaking.RoomMakingActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var roomsAdapter: RoomsAdapter
    private val roomDao = RoomDao(this)
    private val userDao = UserDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        clickNewRoom()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        initRooms()
    }

    private fun initRooms() {
        val roomEntities = roomDao.getRooms()
        val rooms: List<Room> = roomEntities.map {
            Room(
                roomId = it.roomId,
                roomTitle = it.roomTitle,
                firstUserEntity = userDao.getUser(it.firstUserId),
                secondUserEntity = userDao.getUser(it.secondUserId),
            )
        }
        roomsAdapter.initRooms(rooms)
    }

    private fun clickNewRoom() {
        val button = findViewById<FloatingActionButton>(R.id.newRoomButton)
        button.setOnClickListener {
            val intent = Intent(this, RoomMakingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickRoom(roomId: Int) {
        startActivity(OmokGameActivity.getIntent(this, roomId))
    }

    private fun initAdapter() {
        val roomRecyclerView = findViewById<RecyclerView>(R.id.roomRecyclerView)
        roomsAdapter = RoomsAdapter { clickRoom(it) }
        roomRecyclerView.adapter = roomsAdapter
    }

    override fun onDestroy() {
        roomDao.closeDb()
        userDao.closeDb()
        super.onDestroy()
    }

    companion object {
        const val ROOM_ID = "roomId"
    }
}
