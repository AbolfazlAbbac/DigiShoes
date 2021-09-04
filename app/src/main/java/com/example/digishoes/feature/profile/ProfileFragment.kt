package com.example.digishoes.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digishoes.R
import com.example.digishoes.common.NikeFragment
import com.example.digishoes.data.TokenContainer
import com.example.digishoes.feature.auth.AuthActivity
import com.example.digishoes.feature.favorite.FavoriteActivity
import com.example.digishoes.feature.history.OrderHistory
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import java.util.*

class ProfileFragment : NikeFragment() {

    val viewModel: ProfileViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteBtnProfile.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteActivity::class.java))
        }
        orderHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistory::class.java))
        }

    }


    override fun onResume() {
        super.onResume()
        checkout()
    }

    private fun checkout() {
        if (viewModel.isSignIn) {
            authBtn.text = getString(R.string.signOut)
            usernameTv.text = viewModel.getUserName
            authBtn.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_exit_to_app_24,
                0
            )
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkout()
            }
        } else {
            authBtn.text = getString(R.string.loginScreenTitle)
            authBtn.setOnClickListener {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
            if(Locale.getDefault().displayLanguage == "en"){
                authBtn.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_baseline_vpn_key_24,
                    0,
                    0,
                    0
                )
            }else{
                authBtn.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_baseline_vpn_key_24,
                    0
                )
            }

            usernameTv.text = getText(R.string.guestUser)

        }
    }
}
