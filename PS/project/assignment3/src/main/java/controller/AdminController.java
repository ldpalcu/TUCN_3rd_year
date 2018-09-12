package controller;


import view.AdminView;

public class AdminController {

    private final AdminView adminView;

    public AdminController(AdminView adminView) {
        this.adminView = adminView;
    }

    public void handleActivities(){
        adminView.showActivitiesView();
    }

    public void handleUsers(){
        adminView.showUsersView();
    }

    public void handleReports(){
        adminView.showReportsView();
    }
}
