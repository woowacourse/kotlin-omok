package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.db.OmokDBHelper
import woowacourse.omok.db.PlayerContract

class StartActivity : AppCompatActivity() {
    private val omokDB: SQLiteDatabase by lazy { OmokDBHelper(this).writableDatabase }
    private val nicknameView: EditText by lazy { findViewById(R.id.nickname_input) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val startButton = findViewById<Button>(R.id.start_game)
        startButton.setOnClickListener {
            if (validateNickname()) startGame()
        }

        val continueButton = findViewById<Button>(R.id.continue_game)
        continueButton.setOnClickListener {
            if (validateNickname()) continueGame()
        }
    }

    private fun validateNickname(): Boolean {
        if (nicknameView.length() in 1..4) return true
        Toast.makeText(this, "닉네임을 4자 이하로 입력해 주세요", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    private fun startGame() {
        val nickname = nicknameView.text.toString()
        if (hasNickname(nickname)) return showStartDialog(nickname)
        startNewGame(nickname)
    }

    private fun hasNickname(nickname: String): Boolean {
        val cursor = omokDB.query(
            PlayerContract.TABLE_NAME,
            arrayOf(PlayerContract.COLUMN_NICKNAME),
            "${PlayerContract.COLUMN_NICKNAME} = ?",
            arrayOf(nickname),
            null,
            null,
            null
        )

        val count = cursor.count
        cursor.close()
        return count != 0
    }

    private fun showStartDialog(nickname: String) {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.glo_icon)
        builder.setTitle("이미 게임이 존재합니다")
        builder.setPositiveButton("이어하기") { _, _ -> goToGame(nickname) }
        builder.setNegativeButton(
            "새로하기"
        ) { _, _ ->
            deletePlayer(nickname)
            startNewGame(nickname)
        }
        builder.show()
    }

    private fun deletePlayer(nickname: String) {
        val condition = "${PlayerContract.COLUMN_NICKNAME} = ?"
        omokDB.delete(PlayerContract.TABLE_NAME, condition, arrayOf(nickname))
    }

    private fun startNewGame(nickname: String) {
        createPlayer(nickname)
        goToGame(nickname)
    }

    private fun createPlayer(nickname: String) {
        val values = ContentValues()
        values.put(PlayerContract.COLUMN_NICKNAME, nickname)

        omokDB.insert(PlayerContract.TABLE_NAME, null, values)
    }

    private fun goToGame(nickname: String) {
        val intent = Intent(this, MainActivity::class.java)
        getSharedPreferences("Omok", MODE_PRIVATE)
            .edit()
            .putString("nickname", nickname)
            .apply()
        startActivity(intent)
    }

    private fun continueGame() {
        val nickname = nicknameView.text.toString()
        if (!hasNickname(nickname)) return showContinueDialog(nickname)
        goToGame(nickname)
    }

    private fun showContinueDialog(nickname: String) {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.glo_icon)
        builder.setTitle("진행중인 게임이 존재하지 않습니다")
        builder.setPositiveButton("새로하기") { _, _ -> startNewGame(nickname) }
        builder.setNegativeButton("취소", null)
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        omokDB.close()
    }
}
