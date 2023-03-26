package woowacourse.omok.roomList

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.Room
import woowacourse.omok.dbHelper.OmokRoomDbHelper
import woowacourse.omok.room.RoomActivity

class RoomListActivity : AppCompatActivity() {
    private val db = OmokRoomDbHelper(this)
    private val adapter = RoomListAdapter(
        { onRefreshButtonClicked() },
        { roomInfo -> onRoomClicked(room = roomInfo) },
    )

    private lateinit var rooms: List<Room>

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        findViewById<RecyclerView>(R.id.room_list).adapter = adapter

        rooms = db.getRooms(this)
        adapter.submitList(rooms)
    }

    override fun onResume() {
        super.onResume()
        rooms = db.getRooms(this)
        adapter.submitList(rooms)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    private fun onRefreshButtonClicked() {
        adapter.submitList(rooms)
        Toast.makeText(this, "새로고침", Toast.LENGTH_SHORT).show()
    }

    private fun onRoomClicked(room: Room) {
        startActivity(
            Intent(this, RoomActivity::class.java).apply {
                putExtra("gameId", room.gameId)
                putExtra("player", room.player)
            },
        )
    }
}
