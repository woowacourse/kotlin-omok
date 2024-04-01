package woowacourse.omok.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import woowacourse.omok.R
import woowacourse.omok.model.database.GameRoomDao
import woowacourse.omok.model.database.Room

class NewRoomActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var gameNameInput: EditText
    private lateinit var gameRoomDao: GameRoomDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_room)

        gameRoomDao = GameRoomDao(this)
        initializeGameNameInput()
        initializeStartButton()
    }

    private fun initializeGameNameInput() {
        gameNameInput =
            findViewById<EditText>(R.id.et_new_game).apply {
                addTextChangedListener(
                    onTextChanged = { currentText, _, _, _ ->
                        startButton.isClickable = currentText?.length in RANGE_ROOM_NAME_LENGTH
                    },
                )
            }
    }

    private fun initializeStartButton() {
        startButton = findViewById(R.id.btn_start)
        startButton.setOnClickListener {
            val roomInfo = Room(title = gameNameInput.text.toString())
            val generationResult = gameRoomDao.save(roomInfo)
            val intent = generateGameStartIntent(generationResult)
            startActivity(intent)
            finish()
        }
    }

    private fun generateGameStartIntent(generationResult: Room): Intent {
        return Intent(this, OmokGameActivity::class.java).apply {
            putExtra(GAME_TITLE, generationResult.title)
            putExtra(GAME_ID, generationResult.id)
        }
    }

    companion object {
        private const val GAME_ID = "game_id"
        private const val GAME_TITLE = "game_title"
        private val RANGE_ROOM_NAME_LENGTH = 1..30
    }
}
