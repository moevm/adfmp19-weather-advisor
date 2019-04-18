package info.moevm.se.weatheradvisor.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import info.moevm.se.ext.bitmap
import info.moevm.se.ext.color
import info.moevm.se.ext.dpToPx
import info.moevm.se.weatheradvisor.R

class ClotheItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : View(context, attrs, defStyleAttrs) {

    private var textPosition: TextPosition
    private var itemImage: Drawable?
    private var itemText: String
    private var borderRadius = 10.dpToPx()
    private var borderWidth = 1.dpToPx()
    private var borderSide = 40.dpToPx()
    private var itemTextSize = 10.dpToPx()
    private var itemTextMargin = 8.dpToPx()

    private val borderPen = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#BDBDBD")
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        strokeWidth = borderWidth
    }
    private val textPen = Paint(ANTI_ALIAS_FLAG).apply {
        textSize = itemTextSize
        color = Color.parseColor("#BDBDBD")
    }
    private val bitmapPen = Paint(ANTI_ALIAS_FLAG)

    private var fittedTextX = 0f
    private var fittedTextY = 0f
    private var bitmapX = 0f
    private var bitmapY = 0f
    private var borderRect: RectF

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ClotheItem,
            0, 0
        ).apply {
            try {
                borderRadius = getInt(R.styleable.ClotheItem_border_radius, 4).dpToPx()
                borderWidth = getInt(R.styleable.ClotheItem_border_width, 1).dpToPx()
                textPosition = when (getInt(R.styleable.ClotheItem_text_placement, 0)) {
                    0 -> TextPosition.START
                    1 -> TextPosition.TOP
                    2 -> TextPosition.END
                    3 -> TextPosition.BOTTOM
                    else -> TextPosition.START
                }
                itemImage = getDrawable(R.styleable.ClotheItem_image_item)

                itemText = getString(R.styleable.ClotheItem_item_text).let { it ?: "" }

            } finally {
                recycle()
            }
        }

        borderRect = RectF(borderWidth, borderWidth, borderSide, borderSide)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when (textPosition) {
            TextPosition.START, TextPosition.END -> {
                run {
                    setMeasuredDimension(
                        (borderSide + textPen.measureText(itemText) + borderWidth * 2 + itemTextMargin).toInt(),
                        (borderSide + borderWidth * 2).toInt()
                    )
                    fitViews()
                }
            }
            TextPosition.TOP, TextPosition.BOTTOM -> {
                run {
                    setMeasuredDimension(
                        Math.max((borderSide + borderWidth * 2), textPen.measureText(itemText)).toInt(),
                        (borderSide + borderWidth * 2 + itemTextSize + itemTextMargin).toInt()
                    )
                    fitViews()
                }
            }

        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.apply {
            drawRoundRect(borderRect, borderRadius, borderRadius, borderPen)
            drawText(itemText, fittedTextX, fittedTextY, textPen)
            itemImage?.let { drawBitmap(it.bitmap(), bitmapX, bitmapY, bitmapPen) }
        }
    }

    fun setItemImage(image: Drawable?) {
        itemImage = image
        invalidate()
    }

    fun setItemColor(color: Int) {
        if (itemImage == null) return
        bitmapPen.colorFilter = PorterDuffColorFilter(context.color(color), PorterDuff.Mode.SRC_IN)
        invalidate()
    }

    fun setItemTitle(title: String) {
        itemText = title
        invalidate()
    }

    internal fun getHalfDrawableAndTextSize() =
        (textPen.measureText(itemText) + itemTextMargin).toInt()

    private fun fitViews() {
        val textWidth = textPen.measureText(itemText)
        val bitmap = itemImage?.bitmap()
        var cellMiddleX = 0f
        var cellMiddleY = 0f
        when (textPosition) {
            TextPosition.START -> {
                fittedTextX = borderWidth
                fittedTextY = 24.dpToPx()
                borderRect = RectF(
                    textWidth + itemTextMargin,
                    borderWidth,
                    textWidth + itemTextMargin + borderSide,
                    borderSide
                )
                cellMiddleX = textWidth + itemTextMargin + borderWidth + borderSide / 2
                cellMiddleY = height / 2f
            }
            TextPosition.TOP -> {
                fittedTextX = (width / 2 - textWidth / 2)
                fittedTextY = borderWidth + itemTextSize
                borderRect = RectF(
                    (width / 2 - borderSide / 2),
                    itemTextSize + itemTextMargin,
                    (width / 2 + borderSide / 2),
                    itemTextSize + itemTextMargin + borderSide
                )
                cellMiddleX = width / 2f
                cellMiddleY = itemTextSize + itemTextMargin + borderWidth + borderSide / 2
            }
            TextPosition.END -> {
                fittedTextX = borderSide + itemTextMargin
                fittedTextY = 24.dpToPx()
                borderRect = RectF(
                    borderWidth,
                    borderWidth,
                    borderSide,
                    borderSide
                )
                cellMiddleX = borderWidth + borderSide / 2
                cellMiddleY = height / 2f
            }
            TextPosition.BOTTOM -> {
                fittedTextX = (width / 2 - textWidth / 2)
                fittedTextY = borderSide + borderWidth * 2 + itemTextMargin
                borderRect = RectF(
                    (width / 2 - borderSide / 2),
                    borderWidth,
                    (width / 2 + borderSide / 2),
                    borderSide
                )
                cellMiddleX = width / 2f
                cellMiddleY = borderWidth + borderSide / 2
            }
        }
        bitmapX = cellMiddleX - (bitmap?.let { it.width / 2 } ?: 0)
        bitmapY = cellMiddleY - (bitmap?.let { it.height / 2 } ?: 0)
    }

    fun hideTitle() {
        textPen.color = Color.TRANSPARENT
        invalidate()
    }

    enum class TextPosition {
        START, TOP, END, BOTTOM
    }
}
