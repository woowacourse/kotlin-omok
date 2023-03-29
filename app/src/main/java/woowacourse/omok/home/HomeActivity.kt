package woowacourse.omok.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import woowacourse.omok.R
import woowacourse.omok.omokgame.OmokGameActivity
import woowacourse.omok.roommaking.RoomMakingActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var roomsAdapter: RoomsAdapter
    private val homeService = HomeService(this)

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

    override fun onDestroy() {
        homeService.closeDb()
        super.onDestroy()
    }

    private fun initRooms() {
        val rooms: List<Room> = homeService.getRooms()
        roomsAdapter.initRooms(rooms)
    }

    private fun clickNewRoom() {
        val button = findViewById<FloatingActionButton>(R.id.newRoomButton)
        button.setOnClickListener {
            val intent = Intent(this, RoomMakingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initAdapter() {
        val roomRecyclerView = findViewById<RecyclerView>(R.id.roomRecyclerView)
        roomsAdapter = RoomsAdapter { clickRoom(it) }
        roomRecyclerView.adapter = roomsAdapter
    }

    private fun clickRoom(roomId: Int) {
        startActivity(OmokGameActivity.getIntent(this, roomId))
    }

    companion object {
        const val ROOM_ID = "roomId"
    }
}
