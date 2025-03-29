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
import woowacourse.omok.domain.room.Rooms
import woowacourse.omok.ui.dialog.RoomNameDialog
import woowacourse.omok.ui.main.MainActivity
import woowacourse.omok.ui.room.rv.RoomRvAdapter

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    private lateinit var room: Rooms
    private lateinit var roomRepository: RoomRepository
    private lateinit var adapter: RoomRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
        initializeListener()
        initializeDataSource()
        setRecyclerView()
    }

    private fun initializeView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeListener() {
        binding.makeRoomBtn.setOnClickListener {
            RoomNameDialog(
                onClickComplete = {
                    val roomId = room + it
                    updateRecyclerView()
                    navigateToMain(roomId)
                },
            ).show(supportFragmentManager, "Room")
        }
    }

    private fun initializeDataSource() {
        val dbHelper = OmokDbHelper(this)
        val dao = RoomDao(dbHelper)
        roomRepository = RoomRepositoryImpl(dao)
        room = Rooms(roomRepository)
    }

    private fun setRecyclerView() {
        adapter =
            RoomRvAdapter(
                onClickDelete = {
                    room - it
                    updateRecyclerView()
                },
                onClickJoin = {
                    val selectedRoom = room.roomList[it]
                    navigateToMain(selectedRoom.id)
                },
            )

        val layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = layoutManager

        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        adapter.submitList(room.roomList)
    }

    private fun navigateToMain(roomId: Long) {
        val intent =
            Intent(this, MainActivity::class.java).apply {
                putExtra("roomId", roomId)
            }
        startActivity(intent)
    }
}
