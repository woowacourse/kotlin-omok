package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.model.Position
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.Row
import woowacourse.omok.model.Col
import woowacourse.omok.view.RoomData

class RoomListActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: CustomAdapter
    private val rooms = mutableListOf<RoomData>()
    private lateinit var currentNickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.room_list)

        dbHelper = DbHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.room_list)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentNickname = intent.getStringExtra("nickname") ?: return

        recyclerView = findViewById(R.id.Rooms)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataset = getDataList(currentNickname)
        customAdapter = CustomAdapter(dataset)
        recyclerView.adapter = customAdapter

        findViewById<Button>(R.id.makeNewRoom).setOnClickListener {
            makeRoom()
            finish()
        }
    }

    private fun getDataList(nickname: String): List<RoomData> {
        val db = dbHelper.readableDatabase
        val result = mutableListOf<RoomData>()

        val query = RoomContract.SQL_FIND_ROOMS

        val cursor: Cursor = db.rawQuery(query, arrayOf(nickname))

        with(cursor) {
            while (moveToNext()) {
                val roomId = getInt(getColumnIndexOrThrow(RoomContract.COLUMN_ROOM_ID))
                val roomUserName = getString(getColumnIndexOrThrow(RoomContract.SQL_FIND_ROOMS_NICKNAME))
                val roomStoneCount = getInt(getColumnIndexOrThrow(RoomContract.COLUMN_ROOM_STONE_COUNT))
                val stonesCursor = db.rawQuery(
                    RoomContract.SQL_FIND_ROOM_STONES,
                    arrayOf(roomId.toString())
                )

                val roomStones = mutableListOf<Stone>()
                with(stonesCursor) {
                    while (moveToNext()) {
                        val x = getInt(getColumnIndexOrThrow("x"))
                        val y = getInt(getColumnIndexOrThrow("y"))
                        val colorStr = getString(getColumnIndexOrThrow("color"))

                        val position = Position(Row.from(x), Col.from(y))
                        val color = StoneColor.from(colorStr)
                        roomStones.add(Stone(position, color))
                    }
                }
                stonesCursor.close()

                result.add(RoomData(roomId, roomUserName, roomStoneCount, roomStones))
            }
        }
        cursor.close()
        return result
    }

    private fun makeRoom() {
        val nickname = currentNickname
        val db = dbHelper.writableDatabase

        var nicknameId = -1
        val cursor = db.rawQuery(RoomContract.SQL_FIND_ROOM_USER_ID, arrayOf(nickname))
        if (cursor.moveToFirst()) {
            nicknameId = cursor.getInt(0)
        } else {
            val values = ContentValues().apply {
                put(RoomContract.COLUMN_NICKNAME_NAME, nickname)
            }
            nicknameId = db.insert(RoomContract.NICKNAME_TABLE_NAME, null, values).toInt()
        }
        cursor.close()

        val values = ContentValues().apply {
            put(RoomContract.COLUMN_ROOM_NICKNAME_ID, nicknameId)
            put(RoomContract.COLUMN_ROOM_STONE_COUNT, 0)
        }
        val newRoomId = db.insert(RoomContract.ROOM_TABLE_NAME , null, values)

        db.close()


        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra("nickname", nickname)
            putExtra("room_id", newRoomId.toInt())
        })
    }
}
