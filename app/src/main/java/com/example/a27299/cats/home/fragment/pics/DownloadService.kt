package com.example.a27299.cats.home.fragment.pics

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader
import java.util.jar.Manifest

class DownloadService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra("pic_url")
        val id = intent?.getStringExtra("pic_id")
        downloadPic(url, id)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }else{
//            EasyPermissions.requestPermissions(this, "写上你需要用权限的理由, 是给用户看的", RC_CAMERA_AND_RECORD_AUDIO, perms);
        }

    }

    private fun downloadPic(url: String?, id: String?) {
        GlobalScope.launch(IO) {

            url?.let {
                val client = OkHttpClient.Builder().build()
                val request = Request.Builder()
                        .url(url).get().build()
                val response = client.newCall(request).execute()
                response.body()?.apply {
                    val input = InputStreamReader(this.byteStream())
                    val parent = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val fileName = "$id.${url.split(".").last()}"
                    val file = File(parent, fileName)
                    Log.d("FilePath", file.path)
                    if (!file.exists()) {
                        val b = file.createNewFile()
                        Log.d("FileCreate", b.toString())
                    }
                    val fs = FileWriter(file)
                    var r = input.read()
                    while (r != -1) {
                        fs.write(r)
                        r = input.read()
                    }
                    fs.flush()
                    fs.close()
                    input.close()
                    Log.d("FileFinish", "y")
//                    MediaStore.Images.Media.insertImage(contentResolver,file.absolutePath, fileName, null);
//                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.absolutePath)))
                }

            }
        }
    }

//    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
//        Toast.makeText(this, "未开启", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        Toast.makeText(this, "开启成功", Toast.LENGTH_SHORT).show()
//
//    }
//
//    override fun onRequestPermissionsResult(p0: Int, p1: Array<out String>, p2: IntArray) {
//        EasyPermissions.onRequestPermissionsResult(p0, p1, p2, this)
//    }
}