import business.ReportFactory;
import controller.ReportsController;
import org.junit.Test;
import view.ReportsView;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportsControllerTests {

    @Test
    public void givenPdfOption_generateReport(){
        ReportsView reportsView = mock(ReportsView.class);

        when(reportsView.getOption()).thenReturn("PDF");

        ReportsController reportsController = new ReportsController(reportsView);

        reportsController.generateReport();

    }
}
