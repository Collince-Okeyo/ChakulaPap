package com.ramgdev.chakulapap.ui.order

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.databinding.FragmentConfirmationCustomDialogBinding
import timber.log.Timber

class ConfirmationCustomDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentConfirmationCustomDialogBinding
    private lateinit var textView: TextView
    private val args: ConfirmationCustomDialogArgs by navArgs()

    private val daraja: Daraja = Daraja.with(
        "5aIbhQsfobZG4VMv8zuIRuwKyocTyhEU", "9Gd9YP4VlQHXaJeO", Env.SANDBOX,
        object : DarajaListener<AccessToken> {
            override fun onResult(result: AccessToken) {
                Timber.d("Access Token: ${result.access_token}")
            }
            override fun onError(error: String?) {
                Timber.d("Error: $error")
            }
        })

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmationCustomDialogBinding.inflate(layoutInflater, container, false)

        val order = args.confirmDetails
        textView = binding.cardTxt

        val amount = order.numberOfOrders?.times(order.orderAmount!!)

        binding.cardTxt.text =
            "You are about to order ${order.numberOfOrders} plates of ${order.foodName} for Ksh.${amount} " +
                    "on table number ${order.tableNumber} \n Payment Mode is: ${order.paymentMode} \n \n Are you sure you want to proceed with the order? "

        binding.proceedAction.setOnClickListener {
            Toast.makeText(requireContext(), "Proceed", Toast.LENGTH_SHORT).show()
            pay(
                "0708289107",
                amount.toString(),
                "INPUT TILL OR PAYBILL NO HERE"
            )
            ConfirmationCustomDialog().dialog?.dismiss()
        }
        binding.cancelAction.setOnClickListener {
            ConfirmationCustomDialog().dialog?.dismiss()
            //findNavController().navigate(R.id.action_confirmationCustomDialogFragment2_to_orderFragment)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

                Toast.makeText(
                    requireContext(), "You will receive an M-Pesa confirmation message shortly",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {

                Toast.makeText(requireContext(), data?.getStringExtra("error"), Toast.LENGTH_SHORT)
                    .show()
                Timber.d("onActivityResult: ${data?.getStringExtra("error")}")
            }
        } catch (e: Exception) {
            Timber.d("onActivityResult: ${e.localizedMessage}")
        }
    }

    private fun pay(phone: String, amount: String, tillNoOrPaybillNo: String) {

        val lnmExpress = LNMExpress(
            "174379",
            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
            TransactionType.CustomerPayBillOnline,
            amount,
            phone,
            "174379",
            phone,
            "https://mycallback.com",
            "001ABC",
            "Food Payment"
        )

        daraja.requestMPESAExpress(lnmExpress, object :
            DarajaListener<LNMResult> {
            override fun onResult(result: LNMResult) {
                Toast.makeText(
                    requireContext(),
                    "Response here ${result.ResponseDescription}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onError(error: String?) {
                Toast.makeText(
                    requireContext(),
                    "Error here $error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
