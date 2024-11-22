// DoanhThuChartActivity.java
package com.example.quanlyquanan;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import com.example.quanlyquanan.DAO.DonDatDAO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class DoanhThuChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private DonDatDAO donDatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_chart);

        barChart = findViewById(R.id.barChart);
        donDatDAO = new DonDatDAO(this);

        List<Pair<String, Float>> doanhThuTheoNgay = donDatDAO.LayTongDoanhThuTheoNgay();

        if (doanhThuTheoNgay.isEmpty()) {
            Log.d("DoanhThuChartActivity", "Không có dữ liệu doanh thu theo ngày");
            return;
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> xAxisLabels = new ArrayList<>();
        for (int i = 0; i < doanhThuTheoNgay.size(); i++) {
            Pair<String, Float> data = doanhThuTheoNgay.get(i);
            entries.add(new BarEntry(i, data.second));
            xAxisLabels.add(data.first);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Doanh Thu Theo Ngày (VNĐ)");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new DecimalValueFormatter());

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        configureChartAppearance(xAxisLabels);
        barChart.invalidate();
    }

    private void configureChartAppearance(ArrayList<String> xAxisLabels) {
        barChart.getAxisLeft().setValueFormatter(new DecimalValueFormatter());
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        barChart.getAxisRight().setEnabled(false);
        barChart.animateY(1500);
    }

    private static class DecimalValueFormatter extends ValueFormatter {
        private final DecimalFormat formatter;

        public DecimalValueFormatter() {
            this.formatter = new DecimalFormat("#,###,##0 VNĐ");
        }

        @Override
        public String getFormattedValue(float value) {
            return formatter.format(value);
        }
    }
}
