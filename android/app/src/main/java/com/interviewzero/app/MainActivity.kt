package com.interviewzero.app

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.app.WallpaperManager
import android.widget.*
import java.util.Calendar

class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
  val layout=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(32,48,32,32) }
  layout.addView(TextView(this).apply { text="INTERVIEW//ZERO"; textSize=28f })
  layout.addView(TextView(this).apply { text="Job interview countdown · 31 December 2026"; textSize=16f })
  layout.addView(Button(this).apply { text="Set live wallpaper"; setOnClickListener { startActivity(Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, android.content.ComponentName(this@MainActivity, CountdownWallpaperService::class.java))) } })
  layout.addView(Button(this).apply { text="Schedule 3 daily reminders"; setOnClickListener { scheduleDailyReminders(); Toast.makeText(this@MainActivity,"Daily reminders scheduled",Toast.LENGTH_LONG).show() } })
  layout.addView(Button(this).apply { text="Share static wallpaper"; setOnClickListener { val i=Intent(Intent.ACTION_SEND).setType("image/png").putExtra(Intent.EXTRA_STREAM, android.net.Uri.parse("android.resource://$packageName/${R.drawable.wallpaper_preview}")); startActivity(Intent.createChooser(i,"Share lock-screen wallpaper")) } })
  setContentView(layout); createChannel()
 }
 private fun createChannel(){ val nm=getSystemService(NotificationManager::class.java); nm.createNotificationChannel(NotificationChannel("interviewzero","Interview Zero reminders",NotificationManager.IMPORTANCE_DEFAULT)) }
 private fun scheduleDailyReminders(){ val am=getSystemService(Context.ALARM_SERVICE) as AlarmManager; listOf(8,13,20).forEachIndexed { idx,hour ->
  val cal=Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY,hour);set(Calendar.MINUTE,0);set(Calendar.SECOND,0);if(timeInMillis<=System.currentTimeMillis())add(Calendar.DAY_OF_YEAR,1) }
  val intent=Intent(this,ReminderReceiver::class.java).putExtra("slot",hour)
  val pi=PendingIntent.getBroadcast(this,100+idx,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
  am.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.timeInMillis,AlarmManager.INTERVAL_DAY,pi)
 } }
}