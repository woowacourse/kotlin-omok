package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import woowacourse.omok.model.database.OmokDBContract
import woowacourse.omok.model.database.OmokDBHelper
import woowacourse.omok.model.gameRoom.GameRoom
import woowacourse.omok.model.gameRoom.GameRoomAdapter
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var createGameButton: ExtendedFloatingActionButton
    private lateinit var gameRoomAdapter: GameRoomAdapter
    private lateinit var dbHelper: OmokDBHelper

    private val gameRooms = mutableListOf<GameRoom>()

    private val gameLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                reloadPreviousGameRooms()
                gameRoomAdapter.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = OmokDBHelper(this)
        initViews()
        setupListeners()
        reloadPreviousGameRooms()
        setupRecyclerView()
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

    private fun reloadPreviousGameRooms() {
        val db = dbHelper.readableDatabase
        gameRooms.clear()

        val projection =
            arrayOf(
                OmokDBContract.GameRoomsTable.COLUMN_ROOM_ID,
                OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME,
                OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME,
                OmokDBContract.GameRoomsTable.COLUMN_LAST_PLAY_TIME,
            )
        db
            .query(
                OmokDBContract.GameRoomsTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                "${OmokDBContract.GameRoomsTable.COLUMN_LAST_PLAY_TIME} DESC",
            ).use { cursor ->
                val roomIdIndex = cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_ROOM_ID)
                val blackNameIndex = cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME)
                val whiteNameIndex = cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME)
                val timeIndex = cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_LAST_PLAY_TIME)

                while (cursor.moveToNext()) {
                    val roomId = cursor.getInt(roomIdIndex)
                    val blackPlayerName = cursor.getString(blackNameIndex)
                    val whitePlayerName = cursor.getString(whiteNameIndex)
                    val lastPlayTime = LocalDateTime.parse(cursor.getString(timeIndex), OmokDBContract.dbTimeFormatter)

                    gameRooms.add(
                        GameRoom(
                            id = roomId,
                            blackStonePlayerName = blackPlayerName,
                            whiteStonePlayerName = whitePlayerName,
                            lastPlayTime = lastPlayTime,
                        ),
                    )
                }
            }
    }

    private fun openGameRoom(gameRoom: GameRoom) {
        val intent =
            Intent(this, GameActivity::class.java).apply {
                putExtra(GAME_ROOM_ID, gameRoom.id)
                putExtra(BLACK_PLAYER, gameRoom.blackStonePlayerName)
                putExtra(WHITE_PLAYER, gameRoom.whiteStonePlayerName)
            }
        gameLauncher.launch(intent)
    }

    private fun showCreateGameDialog() {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME, "흑돌기본이름")
                put(OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME, "백돌기본이름")
            }

        val newRoomId = db.insert(OmokDBContract.GameRoomsTable.TABLE_NAME, null, values)

        val projection =
            arrayOf(
                OmokDBContract.GameRoomsTable.COLUMN_ROOM_ID,
                OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME,
                OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME,
                OmokDBContract.GameRoomsTable.COLUMN_LAST_PLAY_TIME,
            )

        val selection = "${OmokDBContract.GameRoomsTable.COLUMN_ROOM_ID} = ?"
        val selectionArgs = arrayOf(newRoomId.toString())

        db
            .query(
                OmokDBContract.GameRoomsTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    val blackPlayerName =
                        cursor.getString(cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME))
                    val whitePlayerName =
                        cursor.getString(cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME))
                    val lastPlayTime =
                        cursor.getString(cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_LAST_PLAY_TIME))

                    val newGameRoom =
                        GameRoom(
                            id = newRoomId.toInt(),
                            blackStonePlayerName = blackPlayerName,
                            whiteStonePlayerName = whitePlayerName,
                            lastPlayTime = LocalDateTime.parse(lastPlayTime, OmokDBContract.dbTimeFormatter),
                        )

                    gameRooms.add(0, newGameRoom)
                    gameRoomAdapter.notifyItemInserted(0)
                    recyclerView.scrollToPosition(0)
                }
            }
    }

    override fun onDestroy() {
        dbHelper.close()

        super.onDestroy()
    }

    companion object {
        const val GAME_ROOM_ID = "game_room_id"
        const val BLACK_PLAYER = "black_player"
        const val WHITE_PLAYER = "hite_player"
    }
}
