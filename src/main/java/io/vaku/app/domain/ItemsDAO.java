package io.vaku.app.domain;

import java.util.Collections;
import java.util.List;

public class ItemsDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/edu-otus-coursework";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mysecretpassword";

    public List<Item> getAll() {
        return Collections.emptyList();
    }

    public Item getById(long id) {
        return null;
    }

    public Item createOrUpdate(Item item) {
        return null;
    }

    public void delete(long id) {

    }
}
