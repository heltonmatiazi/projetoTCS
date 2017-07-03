package br.com.senac.cademeulivro.helpers;

import java.util.concurrent.Callable;

/**
 * Created by helton on 03/05/2017.
 */

public class InstanceController {
    private static InstanceController ourInstance = new InstanceController();

    public static InstanceController getInstance() {
        return ourInstance;
    }

    private Callable<Void> mLogoutCallable;

    private InstanceController() {
    }

    public void setLogoutCallable(Callable<Void> callable) {
        mLogoutCallable = callable;
    }
}
