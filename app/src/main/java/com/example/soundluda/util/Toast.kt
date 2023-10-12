package com.example.soundluda.util

import android.content.Context
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.soundluda.App


object Toast  {
  fun show(txt: String, context: Context = App.app) = makeText(context, txt, Toast.LENGTH_SHORT).show()
  fun showLong(txt: String, context: Context = App.app) = makeText(context, txt, Toast.LENGTH_LONG).show()
}




