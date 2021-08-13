package com.example.digishoes.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.digishoes.R
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException

abstract class NikeFragment : DigiView, Fragment() {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context


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

    abstract class DigiViewModel : ViewModel() {
        val compositeDisposable = CompositeDisposable()
        val progressBar = MutableLiveData<Boolean>()

        override fun onCleared() {
            compositeDisposable.clear()
            super.onCleared()
        }
    }
}