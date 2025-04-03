package woowacourse.omok

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.database.OmokDao2
import woowacourse.omok.database.OmokDbHelper2

class RoomSelectionActivity : AppCompatActivity() {
    private lateinit var omokDao: OmokDao2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_selection)
        omokDao = OmokDao2(OmokDbHelper2(this))
        title = "방 선택"

        val roomListView: ListView = findViewById(R.id.room_list)
        val roomNames: ArrayList<String> = omokDao.queryRoomNames()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, roomNames)
        roomListView.adapter = adapter
        roomListView.setOnItemClickListener { _, _, position, _ ->
            val selectedRoom = roomNames[position]
            openGameActivity(selectedRoom)
        }
        val createRoomButton: Button = findViewById(R.id.create_room_button)
        createRoomButton.setOnClickListener {
            showCreateRoomDialog(adapter, roomNames)
        }
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    private fun showCreateRoomDialog(
        adapter: ArrayAdapter<String>,
        roomList: ArrayList<String>,
    ) {
        val input = EditText(this)
        input.hint = "방 이름을 입력하세요."
        input.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle("새로운 방 생성")
            .setView(input)
            .setPositiveButton("생성") { _, _ ->
                val roomName = input.text.toString().trim()
                when {
                    roomName.isEmpty() -> Toast.makeText(this, "올바르지 않은 방 이름입니다.", Toast.LENGTH_SHORT).show()
                    roomList.contains(roomName) -> Toast.makeText(this, "이미 존재하는 방입니다.", Toast.LENGTH_SHORT).show()
                    else -> {
                        roomList.add(roomName)
                        adapter.notifyDataSetChanged()
                        openGameActivity(roomName)
                    }
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun openGameActivity(roomName: String) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("ROOM_NAME", roomName)
        startActivity(intent)
    }
}
