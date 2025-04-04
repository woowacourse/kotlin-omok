package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class LobbyActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        listView = findViewById(R.id.room_list)

        val rooms =
            listOf(
                mapOf("roomName" to "1번 방"),
                mapOf("roomName" to "2번 방"),
                mapOf("roomName" to "3번 방"),
                mapOf("roomName" to "4번 방"),
                mapOf("roomName" to "5번 방"),
            )

        val adapter =
            SimpleAdapter(
                this,
                rooms,
                android.R.layout.simple_list_item_1,
                arrayOf("roomName"),
                intArrayOf(android.R.id.text1),
            )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedRoom = rooms[position]["roomName"]

            val intent =
                Intent(this, MainActivity::class.java).apply {
                    putExtra("ROOM_NAME", selectedRoom)
                }
            startActivity(intent)
        }
    }
}
