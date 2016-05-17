package com.doctor_andonuts.mylife.Chain;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;

import com.doctor_andonuts.mylife.R;
import com.doctor_andonuts.mylife.Task.TaskManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by jgowing on 5/12/2016.
 */
public class ChainWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final Calendar today = Calendar.getInstance();
        final String todayString = myDateFormat.format(today.getTime());

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            ChainManager chainManager = new ChainManager(context);
            List<Chain> chains = chainManager.getChains();

            Integer dayStatusCount_done = 0;
            Integer dayStatusCount_shouldDo = 0;
            Integer dayStatusCount_noNeed = 0;
            Integer dayStatusCount_doIt = 0;

            for(Chain chain : chains) {
                String dayStatus = chain.getDayStatus(todayString);
                switch (dayStatus) {
                    case "Done":
                        dayStatusCount_done++;
                        break;
                    case "Should do":
                        dayStatusCount_shouldDo++;
                        break;
                    case "No need":
                        dayStatusCount_noNeed++;
                        break;
                    case "DO IT!":
                        dayStatusCount_doIt++;
                        break;
                }
            }

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.chain_widget);
            remoteViews.setTextViewText(R.id.chainWidget_done, dayStatusCount_done.toString());
            remoteViews.setTextViewText(R.id.chainWidget_shouldDo, dayStatusCount_shouldDo.toString());
            remoteViews.setTextViewText(R.id.chainWidget_noNeed, dayStatusCount_noNeed.toString());
            remoteViews.setTextViewText(R.id.chainWidget_doIt, dayStatusCount_doIt.toString());

            Intent intent = new Intent(context, ChainWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
