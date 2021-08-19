package com.example.digishoes.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.digishoes.R
import org.greenrobot.eventbus.util.ErrorDialogManager

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, FragmentLogin())
        }.commit()
    }
}