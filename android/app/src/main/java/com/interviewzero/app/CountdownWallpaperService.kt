package com.interviewzero.app

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import android.graphics.*
import android.os.Handler
import android.os.Looper
import java.util.Calendar

class CountdownWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine = CountdownEngine()
    inner class CountdownEngine : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private var visible = false
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val tick = object : Runnable { override fun run() { drawFrame(); if (visible) handler.postDelayed(this, 1000L) } }
        override fun onVisibilityChanged(isVisible: Boolean) { visible = isVisible; if (visible) handler.post(tick) else handler.removeCallbacks(tick) }
        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { super.onSurfaceChanged(holder,format,width,height); drawFrame() }
        override fun onSurfaceDestroyed(holder: SurfaceHolder) { visible=false; handler.removeCallbacks(tick); super.onSurfaceDestroyed(holder) }
        private fun drawFrame() {
            val holder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                if (canvas != null) {
                    val w=canvas.width.toFloat(); val h=canvas.height.toFloat()
                    canvas.drawColor(Color.rgb(7,13,34))
                    val bg=Paint().apply { shader=LinearGradient(0f,0f,w,h,Color.rgb(24,38,83),Color.rgb(5,10,28),Shader.TileMode.CLAMP) }
                    canvas.drawRect(0f,0f,w,h,bg)
                    val target=Calendar.getInstance().apply { set(2026,Calendar.DECEMBER,31,9,0,0); set(Calendar.MILLISECOND,0) }.timeInMillis
                    var seconds=((target-System.currentTimeMillis()).coerceAtLeast(0L))/1000L
                    val days=seconds/86400; seconds%=86400
                    val hours=seconds/3600; seconds%=3600
                    val mins=seconds/60; val secs=seconds%60
                    paint.color=Color.WHITE; paint.textAlign=Paint.Align.CENTER; paint.typeface=Typeface.create("sans-serif",Typeface.NORMAL)
                    paint.textSize=h*0.055f; canvas.drawText("INTERVIEW//ZERO",w/2,h*0.22f,paint)
                    paint.textSize=h*0.095f; canvas.drawText(days.toString(),w/2,h*0.39f,paint)
                    paint.textSize=h*0.022f; paint.color=Color.CYAN; canvas.drawText("DAYS LEFT",w/2,h*0.43f,paint)
                    paint.textSize=h*0.038f; paint.color=Color.WHITE; canvas.drawText(String.format("%02d:%02d:%02d",hours,mins,secs),w/2,h*0.50f,paint)
                    paint.textSize=h*0.025f; paint.color=Color.rgb(205,216,244); canvas.drawText("JOB INTERVIEW · 31 DEC 2026",w/2,h*0.57f,paint)
                    paint.color=Color.rgb(186,139,255); paint.textSize=h*0.022f; canvas.drawText("PREPARE  ·  PRACTICE  ·  PERFORM",w/2,h*0.68f,paint)
                }
            } catch (_: Exception) { } finally { if(canvas!=null) holder.unlockCanvasAndPost(canvas) }
        }
    }
}