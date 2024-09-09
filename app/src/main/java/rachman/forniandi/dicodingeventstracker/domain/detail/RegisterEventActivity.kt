package rachman.forniandi.dicodingeventstracker.domain.detail

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.databinding.ActivityDetailEventsBinding
import rachman.forniandi.dicodingeventstracker.databinding.ActivityRegisterEventBinding
import rachman.forniandi.dicodingeventstracker.domain.detail.DetailEventsActivity.Companion.REGISTER_EVENT

class RegisterEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEventBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        showLinkRegister()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showLinkRegister() {
        val linkEvent = intent.getStringExtra(REGISTER_EVENT)

        binding.wbDetailEvent.webViewClient= object : WebViewClient() {}
        if (linkEvent != null) {
            binding.wbDetailEvent.loadUrl(linkEvent)
        }
    }
}