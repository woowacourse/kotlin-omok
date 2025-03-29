package woowacourse.omok.ui.room

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.omok.R
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.db.omok.OmokDbHelper
import woowacourse.omok.data.repository.RoomRepositoryImpl
import woowacourse.omok.databinding.ActivityRoomBinding
import woowacourse.omok.domain.repository.RoomRepository
import woowacourse.omok.domain.room.Room
import woowacourse.omok.ui.dialog.RoomNameDialog
import woowacourse.omok.ui.main.MainActivity
import woowacourse.omok.ui.room.rv.RoomRvAdapter
import kotlin.concurrent.thread

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    private lateinit var roomRepository: RoomRepository
    private lateinit var adapter: RoomRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeDataSource()
        initializeView(restoreSavedRoom())
        initializeListener()
    }

    private fun initializeView(rooms: List<Room>) {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter =
            RoomRvAdapter(
                onClickDelete = {
                    deleteRoom(it)
                },
                onClickJoin = {
                    val selectedRoom = rooms[it]
                    navigateToMain(selectedRoom.id)
                },
            )

        val layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = layoutManager

        adapter.submitList(rooms)
    }

    private fun initializeListener() {
        binding.makeRoomBtn.setOnClickListener {
            RoomNameDialog(
                onClickComplete = {
                    val roomId = addRoom(it)
                    navigateToMain(roomId)
                },
            ).show(supportFragmentManager, "Room")
        }
    }

    private fun initializeDataSource() {
        val dbHelper = OmokDbHelper(this)
        val dao = RoomDao(dbHelper)
        roomRepository = RoomRepositoryImpl(dao)
    }

    private fun restoreSavedRoom(): List<Room> {
        return roomRepository.readAll()
    }

    private fun addRoom(roomName: String): Long {
        var roomId = 0L
        executeRoomUpdate {
            roomId = roomRepository.save(Room(roomName = roomName))
        }
        return roomId
    }

    private fun deleteRoom(position: Int) =
        executeRoomUpdate {
            val room = adapter.currentList[position]
            roomRepository.delete(room.id)
        }

    private fun executeRoomUpdate(action: () -> Unit) =
        thread {
            action()
            runOnUiThread {
                adapter.submitList(roomRepository.readAll())
            }
        }.join()

    private fun navigateToMain(roomId: Long) {
        val intent =
            Intent(this, MainActivity::class.java).apply {
                intent.putExtra("roomId", roomId)
            }
        startActivity(intent)
    }
}
