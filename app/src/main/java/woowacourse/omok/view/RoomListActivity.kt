package woowacourse.omok.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.model.database.GameRoomDao
import woowacourse.omok.model.database.Room

class RoomListActivity : AppCompatActivity() {
    private val gameRoomDao: GameRoomDao by lazy { GameRoomDao(this) }
    private val rooms: MutableList<Room> = mutableListOf()
    private lateinit var adapter: RoomInfoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        initializeNewRoomButton()
        initializeRefreshButton()
        initializeRoomList()
    }

    private fun initializeRefreshButton() {
        findViewById<Button>(R.id.btn_refresh)
            .setOnClickListener {
                val existingRoomCount = rooms.size
                rooms.clear()
                rooms.addAll(gameRoomDao.findAll())

                val renewedRoomCount = rooms.size
                val addedRoomCount = renewedRoomCount - existingRoomCount
                adapter.notifyItemRangeInserted(existingRoomCount, addedRoomCount)
            }
    }

    private fun initializeNewRoomButton() {
        findViewById<Button>(R.id.btn_new_room)
            .setOnClickListener {
                Intent(this, NewRoomActivity::class.java).also(::startActivity)
            }
    }

    private fun initializeRoomList() {
        rooms.addAll(gameRoomDao.findAll())
        adapter =
            RoomInfoRecyclerViewAdapter(
                rooms = rooms,
                onEnterClick = { id, title ->
                    Intent(this, OmokGameActivity::class.java).also {
                        it.putExtra(GAME_ID, id)
                        it.putExtra(GAME_TITLE, title)
                        startActivity(it)
                    }
                },
            )

        val items = findViewById<RecyclerView>(R.id.rv_rooms)
        items.adapter = adapter
    }

    companion object {
        private const val GAME_ID = "game_id"
        private const val GAME_TITLE = "game_title"
    }
}
