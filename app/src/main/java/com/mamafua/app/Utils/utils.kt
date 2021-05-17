package com.mamafua.app.Utils

import android.content.Context
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mamafua.app.Repo.Resource

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()

}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar("please check your internet", retry)
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }

    }
}
fun Context.myalert(
    permission: String,
    message: String,
    retry: (() -> Unit)? = null
) {
    AlertDialog.Builder(this).apply {
        setTitle("$permission Required")
        setMessage(message)
        setPositiveButton("Ok") { _, _ -> retry?.invoke() }
    }.show()
}


fun Context.showmessages(
    Title: String,
    message: String,
    retry: (() -> Unit)? = null
) {
    AlertDialog.Builder(this).apply {
        setTitle(Title)
        setMessage(message)
        setCancelable(false)
        setPositiveButton("Ok") { _, _ -> retry?.invoke() }
    }.show()
}

fun View.snackbarz(message: String,actioname: String ,action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let {
        snackbar.setAction(actioname) {
            it()
        }
    }
    snackbar.show()

}

fun isValidMobile(field: String): Boolean {
    return Patterns.PHONE.matcher(field).matches()
}

fun isValidEmail(field: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(field).matches()
}

fun isValidPassword(field: String): Boolean {
    return field.length < 6
}

fun Context.capitalizeString(str: String): String {
    var retStr = str
    try { // We can face index out of bound exception if the string is null
        retStr = str.substring(0, 1).toUpperCase() + str.substring(1)
    } catch (e: Exception) {
        e.printStackTrace().toString()
    }
    return retStr
}