package controller;

import business.ReportFactory;
import business.ReportGenerator;
import view.ReportsView;

public class ReportsController {

    private final ReportsView reportsView;

    public ReportsController(ReportsView reportsView) {
        this.reportsView = reportsView;
    }

    public void generateReport(){
        String reportType = reportsView.getOption();

        ReportGenerator reportGenerator = ReportFactory.getReport(reportType);
        reportGenerator.generateReport();
    }


}
