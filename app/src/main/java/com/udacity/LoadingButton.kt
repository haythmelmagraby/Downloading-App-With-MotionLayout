package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var loadingButtonbackgroundColor = 0
    private var loadingButtontextColor = 0

    private var progress = 0

    private var loadingRectangle = Rect()

    private val rectPaint = Paint().apply {
        style = Paint.Style.FILL
        color = resources.getColor(R.color.colorPrimary, null)
    }
    private val textPaint = Paint().apply {
        style = Paint.Style.FILL
        color = resources.getColor(R.color.white, null)
        textSize = resources.getDimension(R.dimen.default_text_size)
        textAlign = Paint.Align.CENTER
    }
    private val circlePaint = Paint().apply {
        style = Paint.Style.FILL
        color = resources.getColor(R.color.colorAccent, null)
    }

    private var valueAnimator = ValueAnimator()

    private fun startLoadingAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, 360).setDuration(1000)
        valueAnimator.addUpdateListener {
            progress = it.animatedValue as Int
            invalidate()
        }
        valueAnimator.start()
        valueAnimator.doOnEnd {
            buttonState = ButtonState.Completed
            invalidate()
        }


    }


    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed)
    { p, old, new ->
        if (new == ButtonState.Loading) {
            startLoadingAnimation()
        }

    }


    init {
        // set the view to be clickable
        isClickable = true
        // set my local vaiables with attributes
        context.withStyledAttributes(attrs, R.styleable.LoadingButton, 0) {
            loadingButtonbackgroundColor =
                getColor(R.styleable.LoadingButton_button_backgroundColor, 0)
            loadingButtontextColor = getColor(R.styleable.LoadingButton_button_textColor, 0)
        }
        buttonState = ButtonState.Clicked

    }

    override fun performClick(): Boolean {
        super.performClick()
        buttonState = ButtonState.Loading
        invalidate()

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        when (buttonState) {
            ButtonState.Loading -> {
                loadingRectangle.set(0, 0, widthSize * progress / 360, heightSize)
                canvas?.drawRect(loadingRectangle, rectPaint)
                canvas?.drawArc(
                    (widthSize -150).toFloat(),
                    (heightSize / 2 - 30).toFloat(),
                    (widthSize - 80).toFloat(),
                    (heightSize / 2 + 30).toFloat(),
                    0f,
                    progress.toFloat(),
                    true,
                    circlePaint
                )
                canvas?.drawText("Loading .....", widthSize / 2f, heightSize / 2f, textPaint)
            }
            ButtonState.Completed -> {
                canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), rectPaint)
                canvas?.drawText("Downloaded !", widthSize / 2f, heightSize / 2f, textPaint)
            }
            ButtonState.Clicked -> {
                canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), rectPaint)
                canvas?.drawText("Download", widthSize / 2f, heightSize / 2f, textPaint)
            }
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}