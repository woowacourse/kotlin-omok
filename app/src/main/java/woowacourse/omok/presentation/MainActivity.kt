package woowacourse.omok.presentation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.GameDao

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper
    private lateinit var gameDao: GameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(this)
        gameDao = GameDao(dbHelper)

        findViewById<Button>(R.id.btn_create_game).setOnClickListener {
            showDialog()
        }

        findViewById<Button>(R.id.btn_enter_game).setOnClickListener {
            val intent = Intent(this, GameListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.text_dialog_hint)

        val input =
            EditText(this).apply {
                inputType = InputType.TYPE_CLASS_TEXT
            }
        builder.setView(input)

        builder.setPositiveButton(R.string.text_dialog_ok) { _, _ ->
            val roomName = input.text.toString()
            val gameId = gameDao.createGame(roomName)
            val intent = Intent(this, GameActivity::class.java).putExtra("game_id", gameId)
            startActivity(intent)
        }

        builder.setNegativeButton(R.string.text_dialog_cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
