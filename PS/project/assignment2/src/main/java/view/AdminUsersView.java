package view;

import model.Role;

public interface AdminUsersView {

    Long getIdUser();

    String getUsername();

    String getPassword();

    String getName();

    String getOption();

    Role getOptionMenuRole();

    void setTextAreaUser(String text);

}
