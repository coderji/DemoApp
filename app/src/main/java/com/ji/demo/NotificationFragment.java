package com.ji.demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NotificationFragment extends BaseFragment {
    private static final String TAG = "NotificationFragment";
    private NotificationManager mNotificationManager;
    private static final int ID = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNotificationManager = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        view.findViewById(R.id.nt_send).setOnClickListener(v -> sendNotify());
        view.findViewById(R.id.nt_cancel).setOnClickListener(v -> cancelNotify());
    }

    private void sendNotify() {
        Notification.Builder builder = new Notification.Builder(getContext(), TAG);
        builder.setContentText(TAG);
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);

        NotificationChannel channel =
                new NotificationChannel("NotificationChannelID", "NotificationChannel", NotificationManager.IMPORTANCE_HIGH);
        mNotificationManager.createNotificationChannel(channel);
        builder.setChannelId(channel.getId());

        PendingIntent intent = PendingIntent.getActivity(getContext(),
                0, new Intent(Intent.ACTION_DIAL), PendingIntent.FLAG_IMMUTABLE);
        Notification.Action.Builder actionBuilder = new Notification.Action.Builder(
                Icon.createWithResource(getContext(), android.R.mipmap.sym_def_app_icon),
                "Dial",
                intent);
        builder.addAction(actionBuilder.build());

        mNotificationManager.notify(ID, builder.build());
    }

    private void cancelNotify() {
        mNotificationManager.cancel(ID);
    }
}
