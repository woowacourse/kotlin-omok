package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.model.database.GameRoomContract
import woowacourse.omok.model.database.GameRoomDbHelper

class RoomListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        val gameRoomDbHelper = GameRoomDbHelper(this)
        val gameRoomDb = gameRoomDbHelper.readableDatabase

        val buttonNewRoom = findViewById<Button>(R.id.btn_new_room)
        buttonNewRoom.setOnClickListener {
            Intent(this, NewRoomActivity::class.java).also(::startActivity)
        }

        val cursor =
            gameRoomDb.rawQuery(
                """
               SELECT * FROM ${GameRoomContract.TABLE_NAME} 
            """,
                null,
            )

        val result = mutableListOf<Room>()
        while (cursor.moveToNext()) {
            result.add(
                Room(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(GameRoomContract.COLUMN_TITLE)),
                    status = cursor.getString(cursor.getColumnIndexOrThrow(GameRoomContract.COLUMN_STATUS)),
                ),
            )
        }

        val adapter =
            RoomInfoRVAdapter(result) { id, title ->
                Intent(this, MainActivity::class.java).also {
                    it.putExtra(GAME_ID, id)
                    it.putExtra(GAME_TITLE, title)
                    startActivity(it)
                }
            }

        val items = findViewById<RecyclerView>(R.id.rv_rooms)
        items.adapter = adapter
        items.layoutManager = LinearLayoutManager(this)

        cursor.close()
    }

    companion object {
        private const val GAME_ID = "game_id"
        private const val GAME_TITLE = "game_title"
    }
}
