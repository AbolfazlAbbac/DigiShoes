package com.example.digishoes.feature.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.digishoes.R
import com.example.digishoes.common.EXTRA_KEY_ID
import com.example.digishoes.common.priceFormat
import kotlinx.android.synthetic.main.activity_checkout.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.net.URI

class CheckoutActivity : AppCompatActivity() {
    val viewModel: CheckoutViewModel by viewModel() {
        val uri: Uri? = intent.data
        if (uri != null) {
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        } else {
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        viewModel.checkoutLiveData.observe(this) {
            orderStatusHeaderTv.text =
                if (it.purchase_success) getString(R.string.success) else getString(R.string.failed)
            orderStatusTv.text = it.payment_status
            totalPriceTvCheckout.text = priceFormat(it.payable_price, this)
        }
    }
}