package business;

public class ReportFactory {

    public static ReportGenerator getReport(String reportType){

        if (reportType == null){
            return null;
        }
        if (reportType.equalsIgnoreCase("pdf")){
            return new PdfFileGenerator();
        }
        if (reportType.equalsIgnoreCase("txt")){
            return new TxtFileGenerator();
        }

        return null;
    }
}
