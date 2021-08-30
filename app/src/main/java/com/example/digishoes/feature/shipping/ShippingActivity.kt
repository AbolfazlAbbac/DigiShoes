package com.example.digishoes.feature.shipping

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.digishoes.R
import com.example.digishoes.common.*
import com.example.digishoes.data.Checkout
import com.example.digishoes.data.PurchaseDetail
import com.example.digishoes.data.SubmitOrderResult
import com.example.digishoes.feature.checkout.CheckoutActivity
import com.example.digishoes.feature.common.CartItemAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShippingActivity : DigiActivity() {

    val viewModel: ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?: throw IllegalStateException("purchase Detail Can't be null")

        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(shippingView, this)
        viewHolder.bind(
            purchaseDetail.total_price,
            purchaseDetail.payable_price,
            purchaseDetail.shipping_cost
        )

        val clickListener = View.OnClickListener {
            viewModel.submit(
                firstNameEt.text.toString(),
                lastNameEt.text.toString(),
                postalCodeEt.text.toString(),
                phoneNumberEt.text.toString(),
                addressEt.text.toString(),
                if (it.id == R.id.onlinePayment) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DigiSingleObserver<SubmitOrderResult>(compositeDisposable) {
                    override fun onSuccess(t: SubmitOrderResult) {
                        if (t.bank_gateway_url.isNotEmpty()) {
                            openUrlInCustomTab(this@ShippingActivity, t.bank_gateway_url)
                        } else {
                            startActivity(
                                Intent(
                                    this@ShippingActivity,
                                    CheckoutActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_KEY_ID, t.order_id)
                                })
                        }
                        finish()
                    }
                })
        }

        onlinePayment.setOnClickListener(clickListener)
        codPayment.setOnClickListener(clickListener)
    }
}