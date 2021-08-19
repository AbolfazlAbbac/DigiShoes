package com.example.digishoes.common

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.digishoes.R
import com.example.digishoes.feature.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.IllegalStateException

abstract class NikeFragment : DigiView, Fragment() {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}

abstract class DigiActivity : DigiView, AppCompatActivity() {
    override val rootView: CoordinatorLayout?
        get() {

            val viewGroup =
                window.decorView.rootView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout) {
                        return it
                    }
                }
                throw IllegalStateException("Exeption in base.kt")
            } else
                return viewGroup
        }
    override val viewContext: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}

interface DigiView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?
    fun setProgressbarIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.loading_view, it, false)
                    it.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ShowError(digiException: DigiException) {
        viewContext?.let {
            when (digiException.type) {
                DigiException.Type.SIMPLE -> snackBar(
                    digiException.serverMessage ?: it.getString(digiException.userFriendlyMessage),
                    Snackbar.LENGTH_SHORT
                )

                DigiException.Type.AUTH -> {
                    val intent = Intent(it, AuthActivity::class.java)
                    intent.flags = FLAG_ACTIVITY_CLEAR_TOP

                    it.startActivity(intent)
                    Toast.makeText(it, digiException.serverMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun snackBar(message: String, duration: Int) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }

}

abstract class DigiViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val progressBar = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
