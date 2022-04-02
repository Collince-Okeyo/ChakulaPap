package com.ramgdev.chakulapap.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ramgdev.chakulapap.R
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.showSnackbar(text: String) {
    Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_LONG
    ).show()
}

private const val TAG = "UtilFunctions"

fun Fragment.hideKeyboard(): Boolean {
    return (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((activity?.currentFocus ?: View(context)).windowToken, 0)
}

fun isConnected(context: Context): Boolean {
    val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val wifiNetworkInfo: NetworkInfo? =
        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    val mobileNetworkInfo: NetworkInfo? =
        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    return wifiNetworkInfo != null && wifiNetworkInfo.isConnected || mobileNetworkInfo != null && mobileNetworkInfo.isConnected
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Long): String {
    val result = Date(timestamp)
    val startCalendar = Calendar.getInstance()
    startCalendar.time = result
    val format = SimpleDateFormat("EEEE, MMMM d, yyyy 'at' hh:mm a")

    return format.format(startCalendar.time)
}

@SuppressLint("SimpleDateFormat")
fun formatToSimpleDate(timestamp: Long): String {
    val result = Date(timestamp)
    val startCalendar = Calendar.getInstance()
    startCalendar.time = result
    val format = SimpleDateFormat("EEEE, MMMM d, yyyy")

    return format.format(startCalendar.time)
}

@SuppressLint("SimpleDateFormat")
fun formatToDate(timestamp: Long): Date {
    return Date(timestamp)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.changeStatusBar(shouldBeLight: Boolean) {
    WindowInsetsControllerCompat(window, window.decorView.rootView).isAppearanceLightStatusBars =
        shouldBeLight
    window.statusBarColor = if (shouldBeLight) getColor(R.color.white) else getColor(R.color.black)
}