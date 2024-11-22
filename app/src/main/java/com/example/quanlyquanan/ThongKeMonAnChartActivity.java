package com.example.quanlyquanan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlyquanan.DAO.ChiTietDonDatDAO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.BarData;
import androidx.core.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class ThongKeMonAnChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private ChiTietDonDatDAO chiTietDonDatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_mon_an_chart);

        barChart = findViewById(R.id.barChart);
        chiTietDonDatDAO = new ChiTietDonDatDAO(this);

        setupBarChart();
        loadChartData();
    }

    private void setupBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);
    }

    private void loadChartData() {
        List<Pair<String, Integer>> thongKeList = chiTietDonDatDAO.ThongKeSoLuongMonAn();

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < thongKeList.size(); i++) {
            Pair<String, Integer> thongKe = thongKeList.get(i);
            barEntries.add(new BarEntry(i, thongKe.second));
            labels.add(thongKe.first); // Thêm tên món vào labels
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Số lượng bán");
        barDataSet.setColor(getResources().getColor(R.color.teal_700));

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        barChart.setData(data);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        barChart.invalidate();
    }
}
