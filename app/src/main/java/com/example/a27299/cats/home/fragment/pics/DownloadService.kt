package com.example.a27299.cats.home.fragment.pics

import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.a27299.cats.R
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.*

class DownloadService : Service() {

    private var notificationId = 1
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private val NOTIFICATION_CHANNEL = "下载通知"
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra("pic_url")
        val id = intent?.getStringExtra("pic_id")
        downloadPic(url, id)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun getBuilder(): Notification.Builder {
        val notificationBuilder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL, "下载", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder = Notification.Builder(this, NOTIFICATION_CHANNEL)
        } else {
            notificationBuilder = Notification.Builder(this)
            notificationBuilder.setSound(null)
        }
        notificationBuilder.setSmallIcon(R.mipmap.ic_app)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_app))
                .setProgress(100, 0, false)
        return notificationBuilder
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun downloadPic(url: String?, id: String?) {
        GlobalScope.launch(IO) {
            url?.let {
                val nId = notificationId
                notificationId++
                val fileName = "$id.${url.split(".").last()}"
                val notificationBuilder = getBuilder()
                notificationBuilder.setTicker("download")
                        .setContentTitle("正在下载图片 $fileName")
                notificationManager.notify(nId, notificationBuilder.build())
                Log.d("download url", url)
                val client = OkHttpClient.Builder().build()
                val request = Request.Builder()
                        .url(url).get().build()
                val response = client.newCall(request).execute()
                response.body()?.apply {
                    val input = this.byteStream()
                    val parent = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val file = File(parent, fileName)
                    if (!file.exists()) {
                        val b = file.createNewFile()
                        Log.d("FileCreate", b.toString())
                    }

                    val picData = input.readBytes()
                    val output = FileOutputStream(file)
                    output.write(picData)
                    output.flush()
                    output.close()
                    input.close()
                    notificationBuilder.setProgress(100, 100, false)
                            .setContentText("下载完成")
                    notificationManager.notify(nId, notificationBuilder.build())
                    input.close()
                    MediaStore.Images.Media.insertImage(contentResolver, file.absolutePath, fileName, null);
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.absolutePath)))
                }

            }
        }
    }

}