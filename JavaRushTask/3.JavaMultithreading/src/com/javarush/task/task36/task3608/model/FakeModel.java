package com.javarush.task.task36.task3608.model;

import com.javarush.task.task36.task3608.bean.User;

import java.util.ArrayList;
import java.util.List;

public class FakeModel implements Model {
    @Override
    public void deleteUserById(long id) {
throw new UnsupportedOperationException();
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        throw new UnsupportedOperationException();
    }

    private ModelData modelData = new ModelData();

    @Override
    public void loadUserById(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    public void loadDeletedUsers() {
        throw new UnsupportedOperationException();
    }
    @Override
    public void loadUsers() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("a", 1, 1));
        list.add(new User("b", 2, 1));
        modelData.setUsers(list);
    }
}