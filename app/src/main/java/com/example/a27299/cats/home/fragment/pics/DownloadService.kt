package com.example.a27299.cats.home.fragment.pics

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.IBinder
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
                    val length = contentLength()
                    val input = InputStreamReader(this.byteStream())
                    val parent = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

                    val file = File(parent, fileName)
                    Log.d("FilePath", file.path)
                    if (!file.exists()) {
                        val b = file.createNewFile()
                        Log.d("FileCreate", b.toString())
                    }

                    val fs = FileWriter(file)
                    var position = 0
                    var rc = CharArray(1024 * 4)
                    var r = input.read(rc)
                    Log.d("download length", length.toString())
                    while (r != -1) {
                        fs.write(rc)
                        position += r
                        r = input.read(rc)

                        var percent = (position.toFloat() / length) * 100
                        Log.d("download position", position.toString())
                        Log.d("download percent", percent.toString())
                        notificationBuilder.setProgress(100, percent.toInt(), false)
                        notificationManager.notify(nId, notificationBuilder.build())
                    }
                    fs.flush()
                    fs.close()
                    notificationBuilder.setProgress(100, 100.toInt(), false)
                            .setContentText("下载完成")
                    notificationManager.notify(nId, notificationBuilder.build())
                    input.close()
                    Log.d("FileFinish", "y")
//                    MediaStore.Images.Media.insertImage(contentResolver,file.absolutePath, fileName, null);
//                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.absolutePath)))
                }

            }
        }
    }

}