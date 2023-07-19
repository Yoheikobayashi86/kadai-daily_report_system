package actions;

import java.io.IOException;
import java.util.List; //追記

import javax.servlet.ServletException;

import actions.views.EmployeeView; //追記
import actions.views.ReportView; //追記
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;  //追記
import services.ReportService;  //追記

public class TopAction extends ActionBase {

    private ReportService service;

    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();

        invoke();

        service.close();

    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {


        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        int page = getPage();
        List<ReportView> reports = service.getMinePerPage(loginEmployee, page);

        long myReportsCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.REPORTS, reports);
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount);
        putRequestScope(AttributeConst.PAGE, page);
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);


        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        forward(ForwardConst.FW_TOP_INDEX);
    }

}