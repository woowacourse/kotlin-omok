package woowacourse.omok

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import woowacourse.omok.data.dao.OmokDao

class LobbyActivity : AppCompatActivity() {
    private lateinit var roomListAdapter: RoomListAdapter
    private val roomItems = mutableListOf<RoomData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lobby)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val omokDao = OmokDao(this)
        val savedRoomIds = omokDao.getAllRooms()

        roomListAdapter = RoomListAdapter(roomItems)
        val roomRecyclerView = findViewById<RecyclerView>(R.id.room_recyclerView)
        roomRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roomRecyclerView.adapter = roomListAdapter

        if (savedRoomIds.isNotEmpty()) {
            val savedRooms = savedRoomIds.sorted().map { RoomData(it) }
            roomItems.addAll(savedRooms)
        }

        val btnAddRoom = findViewById<FloatingActionButton>(R.id.btn_add_room)

        btnAddRoom.setOnClickListener {
            val nextRoomId = if (roomItems.isEmpty()) 0 else roomItems.maxOf { it.roomId } + 1
            val newRoom = RoomData(nextRoomId)

            omokDao.insertRoom(newRoom.roomId)
            roomListAdapter.addRoom(newRoom)
        }
    }
}
