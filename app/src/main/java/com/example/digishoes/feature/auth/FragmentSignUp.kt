package com.example.digishoes.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.digishoes.R
import com.example.digishoes.common.DigiCompletableObserver
import com.example.digishoes.common.NikeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class FragmentSignUp : NikeFragment() {
    val compositeDisposable = CompositeDisposable()
    val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordEt: EditText = view.findViewById(R.id.password_et_signUp)
        val emailEt: EditText = view.findViewById(R.id.email_et_signUp)
        val signupBtn: Button = view.findViewById(R.id.signupBtn)
        val loginLink: Button = view.findViewById(R.id.signupLinkToLogin)

        signupBtn.setOnClickListener {
            viewModel.signUp(emailEt.text.toString(), passwordEt.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        requireActivity().finish()
                    }
                })
        }

        loginLink.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, FragmentLogin())
            }.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}