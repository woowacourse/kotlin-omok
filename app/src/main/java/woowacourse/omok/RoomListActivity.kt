package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import model.Position
import model.Stone
import model.StoneColor
import model.Row
import model.Col
import woowacourse.omok.databinding.ActivityMainBinding
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

        val query = """
            SELECT r._id, n.name AS nickname, r.stone_count
            FROM rooms r
            JOIN nicknames n ON r.nickname_id = n._id
            WHERE n.name = ?
        """

        val cursor: Cursor = db.rawQuery(query, arrayOf(nickname))

        with(cursor) {
            while (moveToNext()) {
                val roomId = getInt(getColumnIndexOrThrow("_id"))
                val roomUserName = getString(getColumnIndexOrThrow("nickname"))
                val roomStoneCount = getInt(getColumnIndexOrThrow("stone_count"))

                val stonesCursor = db.rawQuery(
                    "SELECT x, y, color, turn FROM stones WHERE room_id = ? ORDER BY turn ASC",
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
        val cursor = db.rawQuery("SELECT _id FROM nicknames WHERE name = ?", arrayOf(nickname))
        if (cursor.moveToFirst()) {
            nicknameId = cursor.getInt(0)
        } else {
            val values = ContentValues().apply {
                put("name", nickname)
            }
            nicknameId = db.insert("nicknames", null, values).toInt()
        }
        cursor.close()

        val values = ContentValues().apply {
            put("nickname_id", nicknameId)
            put("stone_count", 0)
        }
        val newRowId = db.insert("rooms", null, values)

        if (newRowId == -1L) {
            Log.e("RoomListActivity", "Room insert failed")
        } else {
            Log.d("RoomListActivity", "Room insert success: $newRowId")
        }
        db.close()

        // 🔥 room_id 같이 넘기기!
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra("nickname", nickname)
            putExtra("room_id", newRowId.toInt()) // <- 이거 추가!
        })
    }
}