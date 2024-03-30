package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.model.database.GameRoomDao

class RoomListActivity : AppCompatActivity() {
    private val gameRoomDao: GameRoomDao by lazy { GameRoomDao(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)
        val buttonNewRoom = findViewById<Button>(R.id.btn_new_room)
        buttonNewRoom.setOnClickListener {
            Intent(this, NewRoomActivity::class.java).also(::startActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        val adapter =
            RoomInfoRVAdapter(
                rooms = gameRoomDao.findAll(),
                onEnterClick = { id, title ->
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra(GAME_ID, id)
                        it.putExtra(GAME_TITLE, title)
                        startActivity(it)
                    }
                },
            )

        val items = findViewById<RecyclerView>(R.id.rv_rooms)
        items.adapter = adapter
        items.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        private const val GAME_ID = "game_id"
        private const val GAME_TITLE = "game_title"
    }
}
