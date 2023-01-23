package com.yprodan.player.arch.ext

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yprodan.player.Constant
import com.yprodan.player.R


fun Fragment.makeToast(textRes: Int) {
    Toast.makeText(requireContext(), getString(textRes), Toast.LENGTH_SHORT)
        .show()
}

fun Fragment.showNotifications(url: String, title: String, playing: Boolean) {
    val contentView = RemoteViews(requireActivity().packageName, R.layout.player_notification)

    Glide.with(this)
        .asBitmap()
        .load(url)
        .error(R.drawable.ic_melody_notification)
        .placeholder(R.drawable.ic_melody_notification)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                contentView.setImageViewBitmap(R.id.albumImage, resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    contentView.setImageViewResource(
        R.id.notifButtonPausePlay,
        if (playing) R.drawable.ic_pause else R.drawable.ic_play
    )

    val mBuilder = makeNotificationBuilder(contentView, requireContext(), title)

    val notification: Notification = mBuilder.build()
    notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(Constant.NOTIFICATION_CHANNEL_ID, getString(R.string.notification_player_name), NotificationManager.IMPORTANCE_HIGH)

        with(NotificationManagerCompat.from(requireContext())) {
            createNotificationChannel(channel)
            notify(1, notification)
        }

    } else {
        with(NotificationManagerCompat.from(requireContext())) {
            notify(1, notification)
        }
    }
}

fun Fragment.showNotifications(uri: Uri, title: String, playing: Boolean) {
    val contentView = RemoteViews(requireActivity().packageName, R.layout.player_notification)

    val m = MediaMetadataRetriever().apply { setDataSource(uri.path) }

    if (m.embeddedPicture == null) {
        contentView.setImageViewBitmap(
            R.id.albumImage,
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_melody_notification)
                ?.toBitmap()
        )
    } else {
        contentView.setImageViewBitmap(
            R.id.albumImage,
            BitmapFactory.decodeByteArray(m.embeddedPicture, 0, m.embeddedPicture!!.size)
        )
    }
    contentView.setImageViewResource(
        R.id.notifButtonPausePlay,
        if (playing) R.drawable.ic_pause else R.drawable.ic_play
    )

    val mBuilder = makeNotificationBuilder(contentView, requireContext(), title)

    val notification: Notification = mBuilder.build()
    notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(Constant.NOTIFICATION_CHANNEL_ID, getString(R.string.notification_player_name), NotificationManager.IMPORTANCE_HIGH)

        with(NotificationManagerCompat.from(requireContext())) {
            createNotificationChannel(channel)
            notify(1, notification)
        }

    } else {
        with(NotificationManagerCompat.from(requireContext())) {
            notify(1, notification)
        }
    }
}
fun makeNotificationBuilder(contentView: RemoteViews, context: Context, title: String): NotificationCompat.Builder {
    val intentPrev = Intent(Context.NOTIFICATION_SERVICE)

    intentPrev.action = Constant.ACTION_NOTIFICATION_PREVIOUS_TAG
    val pIntentPrev = PendingIntent
        .getBroadcast(context, 1, intentPrev, PendingIntent.FLAG_MUTABLE)
    contentView.setOnClickPendingIntent(R.id.nptifButtonPrev, pIntentPrev)

    val intentPlayPause = Intent(Context.NOTIFICATION_SERVICE)
    intentPlayPause.action = Constant.ACTION_NOTIFICATION_PLAY_PAUSE_TAG
    val pIntentPlayPause = PendingIntent
        .getBroadcast(context, 1, intentPlayPause, PendingIntent.FLAG_MUTABLE)
    contentView.setOnClickPendingIntent(R.id.notifButtonPausePlay, pIntentPlayPause)

    val intentNext = Intent(Context.NOTIFICATION_SERVICE)
    intentNext.action = Constant.ACTION_NOTIFICATION_NEXT_TAG
    val pIntentNext = PendingIntent
        .getBroadcast(context, 1, intentNext, PendingIntent.FLAG_MUTABLE)
    contentView.setOnClickPendingIntent(R.id.notifButtonNext, pIntentNext)

    contentView.setTextViewText(R.id.compositionNameTextView, title)

    val intentStop = Intent(Context.NOTIFICATION_SERVICE)
    intentStop.action = Constant.ACTION_NOTIFICATION_STOP_TAG
    val pIntentStop = PendingIntent
        .getBroadcast(context, 1, intentStop, PendingIntent.FLAG_MUTABLE)

    return NotificationCompat.Builder(context, Constant.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setDeleteIntent(pIntentStop)
            .setContent(contentView)
            .setSilent(true)
}