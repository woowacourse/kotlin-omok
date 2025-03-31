package woowacourse.omok.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)
        val submitButton = findViewById<Button>(R.id.nicknameSubmitButton)
        val editText = findViewById<EditText>(R.id.nicknameEditText)

        submitButton.setOnClickListener {
            val nickname: String = editText.text.toString()
            val intent = Intent(this, OmokGameActivity::class.java)
            intent.putExtra(NICKNAME_KEY, nickname)
            startActivity(intent)
        }
    }

    companion object {
        const val NICKNAME_KEY = "nickname"
    }
}
