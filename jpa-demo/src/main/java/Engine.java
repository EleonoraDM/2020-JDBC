import models.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class Engine implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    private final EntityManager manager;

    public Engine(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {

        changeCasingEx2();

    }

    private void changeCasingEx2() {
        List<Town> towns = manager
                .createQuery("SELECT t from Town t WHERE length(t.name) <= 5", Town.class)
                .getResultList();

        manager.getTransaction().begin();
        towns.forEach(manager::detach);

        for (Town town : towns) {
            town.getName().toLowerCase();
        }

        towns.forEach(manager::merge);
        manager.flush();
        manager.getTransaction().commit();
    }
}
