package com.interviewzero.app
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
class ReminderReceiver: BroadcastReceiver(){
 override fun onReceive(context:Context,intent:Intent){
  val hour=intent.getIntExtra("slot",8)
  val body=when(hour){8->"Start strong: review today's interview-preparation milestone.";13->"Midday check-in: practice one technical or behavioral answer.";else->"Evening review: record progress and plan tomorrow."}
  val n=NotificationCompat.Builder(context,"interviewzero").setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("INTERVIEW//ZERO · Check-in").setContentText(body).setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true).build()
  try { NotificationManagerCompat.from(context).notify(hour,n) } catch (_: SecurityException) { }
 }
}