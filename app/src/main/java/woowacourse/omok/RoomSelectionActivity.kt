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
import woowacourse.omok.database.OmokDao
import woowacourse.omok.database.OmokDbHelper

class RoomSelectionActivity : AppCompatActivity() {
    private lateinit var omokDao: OmokDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_selection)
        omokDao = OmokDao(OmokDbHelper(this))
        title = getString(R.string.title_room_selection)
        setRoomListView()
    }

    override fun onResume() {
        super.onResume()
        setRoomListView()
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    private fun setRoomListView() {
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

    private fun showCreateRoomDialog(
        adapter: ArrayAdapter<String>,
        roomList: ArrayList<String>,
    ) {
        val input = EditText(this)
        input.hint = getString(R.string.input_room_name)
        input.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_create_room))
            .setView(input)
            .setPositiveButton(getString(R.string.button_positive)) { _, _ ->
                val roomName = input.text.toString().trim()
                when {
                    roomName.isEmpty() -> Toast.makeText(this, getString(R.string.message_invalid_room_name), Toast.LENGTH_SHORT).show()
                    roomList.contains(
                        roomName,
                    ) -> Toast.makeText(this, getString(R.string.message_room_already_exists), Toast.LENGTH_SHORT).show()
                    else -> {
                        adapter.notifyDataSetChanged()
                        roomList.add(roomName)
                        openGameActivity(roomName)
                    }
                }
            }
            .setNegativeButton(getString(R.string.button_negative), null)
            .show()
    }

    private fun openGameActivity(roomName: String) {
        val intent = Intent(this, RoomActivity::class.java)
        intent.putExtra("ROOM_NAME", roomName)
        startActivity(intent)
    }
}
