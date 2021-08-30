package com.example.digishoes.feature.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.digishoes.R
import com.example.digishoes.common.DigiActivity
import com.example.digishoes.common.convertDpToPixel
import com.example.digishoes.common.setupWithNavController
import com.example.digishoes.data.CartItemCount
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : DigiActivity() {

    private var currentNavController: LiveData<NavController>? = null
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.BottomNavigationView)

        val navGraphIds = listOf(R.navigation.home, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun badgeMain(cartItemCount: CartItemCount) {
        val badge = BottomNavigationView.getOrCreateBadge(R.id.cart)
        badge.backgroundColor = MaterialColors.getColor(BottomNavigationView, R.attr.colorPrimary)
        badge.badgeGravity = BadgeDrawable.BOTTOM_END
        badge.number = cartItemCount.count
        badge.isVisible = cartItemCount.count > 0
        badge.verticalOffset = convertDpToPixel(10f, this).toInt()

    }

    override fun onResume() {
        super.onResume()
        viewModel.changeItemCountBadge()
    }


}