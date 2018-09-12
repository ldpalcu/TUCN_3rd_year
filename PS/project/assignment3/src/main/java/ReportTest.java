import business.ReportFactory;
import business.ReportGenerator;

public class ReportTest {

    public static void main(String[] args) {


        ReportGenerator reportGenerator = ReportFactory.getReport("pdf");

        reportGenerator.generateReport();

    }
}
