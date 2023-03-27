package woowacourse.omok

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

        nicknameView.setText(Omok.sharedPref.getString("nickname", ""))

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
        val cursor = PlayerContract.readRecord(omokDB, nickname)
        val count = cursor.count
        cursor.close()
        return count != 0
    }

    private fun showStartDialog(nickname: String) {
        val builder = AlertDialog
            .Builder(this)
            .setIcon(R.drawable.glo_icon)
            .setTitle("이미 게임이 존재합니다")
            .setPositiveButton("이어하기") { _, _ -> goToGame(nickname) }
            .setNegativeButton("새로하기") { _, _ ->
                PlayerContract.deleteRecord(omokDB, nickname)
                startNewGame(nickname)
            }
        builder.show()
    }

    private fun startNewGame(nickname: String) {
        PlayerContract.createRecord(omokDB, nickname)
        goToGame(nickname)
    }

    private fun goToGame(nickname: String) {
        val intent = Intent(this, MainActivity::class.java)
        Omok.sharedPref
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
        val builder = AlertDialog
            .Builder(this)
            .setIcon(R.drawable.glo_icon)
            .setTitle("진행중인 게임이 존재하지 않습니다")
            .setPositiveButton("새로하기") { _, _ -> startNewGame(nickname) }
            .setNegativeButton("취소", null)
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        omokDB.close()
    }
}
