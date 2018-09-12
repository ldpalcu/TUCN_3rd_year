package client;

import java.util.Observable;

public class ObservableClient extends Observable {

    public void clearChanged(){
        super.clearChanged();
    }

    public void setChanged(){
        super.setChanged();
    }

}
