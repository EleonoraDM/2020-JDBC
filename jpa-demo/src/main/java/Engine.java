import models.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class Engine implements Runnable {

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
                .createQuery("SELECT t from Town t " +
                        "WHERE length(t.name) <= 5", Town.class)
                .getResultList();

        manager.getTransaction().begin();

        towns.forEach(manager::detach);

        for (Town town : towns) {
            town.setName(town.getName().toLowerCase());
        }

        towns.forEach(manager::merge);
        manager.flush();//might be skipped, because the commit will do this by itself.

        manager.getTransaction().commit();
    }


}
