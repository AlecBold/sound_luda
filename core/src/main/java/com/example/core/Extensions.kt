package com.example.core



inline fun checkNotNulls(vararg values: Any?, lazyMessage: () -> Unit) {
  values.forEach {
    checkNotNull(it, lazyMessage)
  }
}
