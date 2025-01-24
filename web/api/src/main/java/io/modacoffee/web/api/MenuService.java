package io.modacoffee.web.api;

import java.util.List;

import io.modacoffee.web.v5.MenuItem;

public interface MenuService {
    List<MenuItem> listMenuItems();
    void registerMenuItem(MenuItem menuItem);
    void unregisterMenuItem(MenuItem menuItem);
}
