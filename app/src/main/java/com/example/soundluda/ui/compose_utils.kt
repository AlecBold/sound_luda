package com.example.soundluda.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle


class VisualTransformation(
  private val transformer: AnnotateTransformer
) : VisualTransformation, AnnotateTransformer by transformer {
  override fun filter(text: AnnotatedString): TransformedText {
    return TransformedText(transform(text.toString()), OffsetMapping.Identity)
  }
}

interface AnnotateTransformer {

  class RangeColor(private val from: Int, private val to: Int, private val color: Color) :
    AnnotateTransformer {
    override fun transform(text: String): AnnotatedString {
      val builder = AnnotatedString.Builder()
      for ((i, c) in text.withIndex()) {
        if (i in from until to) {
          builder.withStyle(SpanStyle(color = color)) {
            append(c)
          }
        } else builder.append(c)
      }
      return builder.toAnnotatedString()
    }
  }


  fun transform(text: String): AnnotatedString
}


fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
  return if (condition) {
    then(modifier(Modifier))
  } else {
    this
  }
}
