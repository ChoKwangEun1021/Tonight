package com.weare2024.tonight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.objects.DetectedObject

class MyOverlayView(context: Context, attrs: AttributeSet?): View(context ,attrs) {
    //뷰가 화면에 그려낼 때 의 작업을 수행하는 콜백메소드
    override fun onDraw(canvas: Canvas) {
        //파라미터 canvas : 이 MyOverlayView 에 붙어있는 도화지
        //동작 테스트
        val paint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 8f
            textSize = 40f

        }
        var n=0
        val colorSpace = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA)
        objectList?.forEach{
            val bound = it.boundingBox
            bound.left = (bound.left * widthRatio).toInt()
            bound.top = (bound.top * hightRatio).toInt()
            bound.right = (bound.right * widthRatio).toInt()
            bound.bottom = (bound.bottom * hightRatio).toInt()
            paint.color=colorSpace[n++]
            canvas.drawRect(bound,paint)
            canvas.drawText("${it.trackingId}",bound.left.toFloat(),bound.top.toFloat(),paint)
            return
        }
//        canvas.drawText("Hello",100f,100f,paint)

    }
    private var objectList:List<DetectedObject>? =null
    private var widthRatio:Double = 1.0
    private var hightRatio:Double = 1.0
companion object fun drawObjectBoundingBox(objectList: List<DetectedObject>, widthRatio:Double, hightRatio:Double ){
        this.objectList =objectList
        this.widthRatio =widthRatio
        this.hightRatio =hightRatio
        postInvalidate()
    }
}

