package com.example.digishoes.feature.auth

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.digishoes.R
import com.example.digishoes.common.DigiCompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class FragmentLogin : Fragment() {
    val viewModel: AuthViewModel by inject()
    val compositeDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hidePassword: ImageView = view.findViewById(R.id.hidePasswordBtn)
        val passwordEt: EditText = view.findViewById(R.id.password_et_login)
        val emailEt: EditText = view.findViewById(R.id.email_et_login)
        val loginBtn: Button = view.findViewById(R.id.loginBtn)

        var boolean = false
        hidePassword.setOnClickListener {
            if (!boolean) {
                hidePassword.setImageResource(R.drawable.ic_eye_off)
                passwordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
                boolean = true
            } else if (boolean) {
                hidePassword.setImageResource(R.drawable.ic_eye)
                passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                boolean = false
            }
        }


        loginBtn.setOnClickListener {
            viewModel.login(emailEt.text.toString(), passwordEt.text.toString())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        requireActivity().finish()
                    }
                })
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}