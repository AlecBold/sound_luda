package com.example.core

import android.util.Log


interface Loggable {
  val tag: String
  fun log(txt: String)
}

class AndroidLoggable(
  override val tag: String,
  private val enabled: Boolean = true
): Loggable {

  override fun log(txt: String) {
    if (enabled) Log.d(tag, txt)
  }

}

object GlobalLog {

  private const val TAG = "Global"
  private const val ENABLED = true

  fun log(tag: String = TAG, txt: String) {
    if(ENABLED) Log.d(tag, txt)
  }

}
