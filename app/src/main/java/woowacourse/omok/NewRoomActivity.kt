package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import woowacourse.omok.model.database.GameRoomContract
import woowacourse.omok.model.database.GameRoomDbHelper
import woowacourse.omok.model.state.GameState

class NewRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_room)

        val gameRoomDbHelper = GameRoomDbHelper(this)
        val gameRoomDb = gameRoomDbHelper.writableDatabase
        val startButton = findViewById<Button>(R.id.btn_start)
        val gameNameInput =
            findViewById<EditText>(R.id.et_new_game).apply {
                addTextChangedListener(
                    onTextChanged = { currentText, _, _, _ ->
                        startButton.isClickable = currentText?.length in 1..30
                    },
                )
            }

        startButton.setOnClickListener {
            val name = gameNameInput.text.toString()
            val values =
                ContentValues().apply {
                    put(GameRoomContract.COLUMN_TITLE, name)
                    put(GameRoomContract.COLUMN_STATUS, GameState.OnProgress::class.java.name)
                }
            val gameId = gameRoomDb.insert(GameRoomContract.TABLE_NAME, null, values)
            Intent(this, MainActivity::class.java).also {
                it.putExtra(GAME_ID, gameId)
                startActivity(it)
            }
        }
    }

    companion object {
        private const val GAME_ID = "game_id"
    }
}
