package com.chiheb.footballclubs.core.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ImageView.loadImage(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun EditText.textChanges(): Flow<String> {
    return callbackFlow {
        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(query: Editable?) {
                this@callbackFlow.trySend(query.toString()).isSuccess
            }

            override fun beforeTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        }

        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }
}

fun EditText.setOnEditorSearchListener(callback: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callback()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}

fun View.hideKeyboard() {
    clearFocus()
    context.inputMethodManager()?.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.inputMethodManager(): InputMethodManager? =
    getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager