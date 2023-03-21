package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    private lateinit var nickname: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        nickname = findViewById(R.id.nickname_input)

        val startButton = findViewById<Button>(R.id.start_game)
        startButton.setOnClickListener(startGame())
    }

    private fun startGame() = OnClickListener {
        if (validateNickname()) {
            val intent = Intent(this, MainActivity::class.java)
            getSharedPreferences("Omok", MODE_PRIVATE)
                .edit()
                .putString("nickname", nickname.text.toString())
                .apply()
            startActivity(intent)
        }
    }

    private fun validateNickname(): Boolean {
        if (nickname.length() in 1..4) return true
        Toast.makeText(this, "닉네임을 4자 이하로 입력해 주세요", Toast.LENGTH_SHORT)
            .show()
        return false
    }
}
