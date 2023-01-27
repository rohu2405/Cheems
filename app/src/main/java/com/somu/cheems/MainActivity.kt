package com.somu.cheems

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    var currImgUrl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load()

//   val nameInput = findViewById(R.id.name) as EditText

    }

    private fun load() {
        val progress = findViewById(R.id.progressBar) as ProgressBar
        progress.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"
        val ImageView  = findViewById(R.id.memeImage) as ImageView
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currImgUrl = response.getString("url")
                Glide.with(this).load(currImgUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                       progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }
                }).into(ImageView)
            },
            {

            })

        queue.add(jsonObjectRequest)
    }

    fun share(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT , "Hey checkout this meme from reddit page $currImgUrl")
        val choose = Intent.createChooser(intent , "share this url text using...")
        intent.type = "text/plain"
        startActivity(choose)
    }

    fun next(view: View) {
        load()
    }
}
/**


protected void onActivityResult(int requestCode, int resultCode, Intent data) {

super.onActivityResult(requestCode, resultCode, data);

if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

//file name
Uri selectedImage = data.getData();

Intent i = new Intent(this,
AddImage.class);
i.putExtra("imagePath", selectedImage);
startActivity(i);


String imagePath = getIntent().getStringExtra("imagePath");
imageview.setImageURI(Uri.parse(imagePath ));

        **/