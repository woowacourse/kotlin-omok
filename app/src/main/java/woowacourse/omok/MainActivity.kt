package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import woowacourse.omok.model.gameRoom.GameRoom
import woowacourse.omok.model.gameRoom.GameRoomAdapter
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var createGameButton: ExtendedFloatingActionButton
    private lateinit var gameRoomAdapter: GameRoomAdapter

    private val gameRooms = mutableListOf<GameRoom>()
    private var nextRoomId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupListeners()
        loadInitialGameRooms()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerview_game_rooms)
        createGameButton = findViewById(R.id.new_game_btn)
    }

    private fun setupRecyclerView() {
        gameRoomAdapter =
            GameRoomAdapter(
                gameRooms,
                onItemClick = { gameRoom -> openGameRoom(gameRoom) },
            )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameRoomAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        createGameButton.setOnClickListener {
            showCreateGameDialog()
        }
    }

    private fun loadInitialGameRooms() {
        val currentTime = LocalDateTime.now()

        val sampleRooms =
            listOf(
                GameRoom(
                    id = nextRoomId++,
                    blackStonePlayerName = "우아한",
                    whiteStonePlayerName = "테크코스",
                    lastPlayTime = currentTime,
                ),
            )

        gameRooms.addAll(sampleRooms)
        gameRoomAdapter.notifyDataSetChanged()
    }

    private fun openGameRoom(gameRoom: GameRoom) {
        val intent =
            Intent(this, GameActivity::class.java).apply {
                putExtra(GAME_ROOM_ID, gameRoom.id)
                putExtra(BLACK_PLAYER, gameRoom.blackStonePlayerName)
                putExtra(WHITE_PLAYER, gameRoom.whiteStonePlayerName)
            }
        startActivity(intent)
    }

    private fun showCreateGameDialog() {
        val newGameRoom =
            GameRoom(
                id = nextRoomId++,
                blackStonePlayerName = "플레이어$nextRoomId",
                whiteStonePlayerName = "상대$nextRoomId",
                lastPlayTime = LocalDateTime.now(),
            )

        gameRooms.add(0, newGameRoom)
        gameRoomAdapter.notifyItemInserted(0)
        recyclerView.scrollToPosition(0)
    }

    companion object {
        const val GAME_ROOM_ID = "game_room_id"
        const val BLACK_PLAYER = "black_player"
        const val WHITE_PLAYER = "hite_player"
    }
}
