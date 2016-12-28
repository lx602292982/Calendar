package update.spreawdwin.com.calendarview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import sun.bob.mcalendarview.CellConfig;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.utils.CurrentCalendar;
import sun.bob.mcalendarview.utils.ExpCalendarUtil;
import sun.bob.mcalendarview.views.DefaultCellView;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;


public class ExpMainActivity extends AppCompatActivity {

    private TextView YearMonthTv;
    private TextView left,right;
    private ExpCalendarView expCalendarView;
    private View lastClickedView;
    private DateData lastClickedDate = CurrentCalendar.getCurrentDateData();
    private int mMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exp_main_activity);

        expCalendarView = ((ExpCalendarView) findViewById(R.id.calendar_exp));
        left = (TextView)findViewById(R.id.left);
        right = (TextView)findViewById(R.id.right);
        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);
        mMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);
        YearMonthTv.setText(Calendar.getInstance().get(Calendar.YEAR) + "年" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "月");


        expCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                if (view instanceof DefaultCellView) {
                    // 判断上次的点击
                    if (lastClickedView != null) {
                        DateData dateData = CurrentCalendar.getCurrentDateData();
                        // 节约！
                        if (lastClickedView == view)
                            return;
                        if (lastClickedDate.equals(dateData)) {
                            ((DefaultCellView) lastClickedView).setDateToday();
                        } else {
                            if (lastClickedDate.getMonth() == mMonth) {
                                ((DefaultCellView) lastClickedView).setDateNormal();
                            } else {
                                ((DefaultCellView) lastClickedView).setDateOther();
                            }
                        }
                    }
                    // 判断这次的点击
                    ((DefaultCellView) view).setDateChoose();
                    lastClickedView = view;
                    lastClickedDate = date;
                    YearMonthTv.setText(String.format("%d年%d月%d日", date.getYear(), date.getMonth(), date.getDay()));
                }
            }
        }).setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(int year, int month) {
                mMonth = month;
                YearMonthTv.setText(String.format("%d年%d月", year, month));
            }

            @Override
            public void onMonthScroll(float positionOffset) {
//                Log.i("listener", "onMonthScroll:" + positionOffset);
            }
        }) .setMarkedStyle(MarkStyle.RIGHTSIDEBAR)
                .markDate(2016, 12, 23).setMarkedStyle(MarkStyle.DOT, Color.CYAN)
                .markDate(2016, 12, 24).setMarkedStyle(MarkStyle.DOT, Color.YELLOW)
                .markDate(2016, 12, 25).setMarkedStyle(MarkStyle.DOT, Color.BLUE)
                .markDate(2016, 12, 26).setMarkedStyle(MarkStyle.DOT, Color.GREEN)
                .hasTitle(false);
        imageInit();
    }

    private boolean ifExpand = true;

    private void imageInit() {
        final ImageView expandIV = (ImageView) findViewById(R.id.main_expandIV);
        expandIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifExpand) {
                    CellConfig.Month2WeekPos = CellConfig.middlePosition;
                    CellConfig.ifMonth = false;
                    expandIV.setImageResource(R.mipmap.icon_arrow_down);
                    expCalendarView.shrink();
                } else {
                    CellConfig.Week2MonthPos = CellConfig.middlePosition;
                    CellConfig.ifMonth = true;
                    expandIV.setImageResource(R.mipmap.icon_arrow_up);
                    expCalendarView.expand();
                }
                ifExpand = !ifExpand;
            }
        });
//        TextView left = (TextView) findViewById(R.id.left);
//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                expCalendarView.setCurrentItem(CellConfig.middlePosition--);
//            }
//        });
//        TextView right = (TextView) findViewById(R.id.right);
//        right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                expCalendarView.setCurrentItem(CellConfig.middlePosition++);
//            }
//        });
    }

}
