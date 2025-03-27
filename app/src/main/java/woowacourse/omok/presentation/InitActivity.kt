package woowacourse.omok.presentation

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.MainActivity
import woowacourse.omok.R
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.GameContract

class InitActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_init)

        dbHelper = DbHelper(this)

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
        builder.setTitle("방 제목을 입력해주세요.")

        val input =
            EditText(this).apply {
                inputType = InputType.TYPE_CLASS_TEXT
            }
        builder.setView(input)

        builder.setPositiveButton("방 만들기") { _, _ ->
            val roomName = input.text.toString()
            val gameId = createGame(roomName)
            val intent = Intent(this, MainActivity::class.java).putExtra("game_id", gameId)
            startActivity(intent)
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun createGame(roomName: String): Long {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(GameContract.COLUMN_NAME_GAME_NAME, roomName)
            }

        val newRowId = db.insert(GameContract.TABLE_NAME, null, values)
        if (newRowId == -1L) {
            Log.e("Init", "create failed")
        } else {
            Log.d("Init", "create success: $roomName $newRowId")
        }
        db.close()

        return newRowId
    }
}
