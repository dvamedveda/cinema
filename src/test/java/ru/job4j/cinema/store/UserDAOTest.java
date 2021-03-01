package ru.job4j.cinema.store;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.model.UserDTO;

import static org.hamcrest.CoreMatchers.is;

public class UserDAOTest {
    @BeforeClass
    public static void setUp() {
        DatabaseUpdater updater = new DatabaseUpdater("test_db.properties");
        updater.updateDatabase();
    }

    @Test
    public void whenGetExistUserThenSuccess() {
        UserDAO userDAO = new UserDAO(PgStore.getInst("test_db.properties"));
        UserDTO user = userDAO.getUser("default_tel");
        Assert.assertThat(user.getId(), is(0));
        Assert.assertThat(user.getName(), is("default_user"));
        Assert.assertThat(user.getTelNumber(), is("default_tel"));
    }

    @Test
    public void whenGetUnexistUserThenNull() {
        UserDAO userDAO = new UserDAO(PgStore.getInst("test_db.properties"));
        UserDTO user = userDAO.getUser("default_tel111");
        Assert.assertNull(user);
    }

    @Test
    public void whenCreateUserThenSuccess() {
        UserDAO userDAO = new UserDAO(PgStore.getInst("test_db.properties"));
        UserDTO expected = userDAO.saveUser(new UserDTO("some_name", "some_tel"));
        UserDTO result = userDAO.getUser("some_tel");
        Assert.assertThat(result.getId(), is(expected.getId()));
        Assert.assertThat(result.getName(), is(expected.getName()));
        Assert.assertThat(result.getTelNumber(), is(expected.getTelNumber()));
        userDAO.deleteUser(expected.getId());
    }

    @Test
    public void whenUpdateUserThenSuccess() {
        UserDAO userDAO = new UserDAO(PgStore.getInst("test_db.properties"));
        UserDTO user = userDAO.saveUser(new UserDTO("some_name", "some_tel"));
        user.setName("other_name");
        userDAO.saveUser(user);
        UserDTO result = userDAO.getUser("some_tel");
        Assert.assertThat(result.getId(), is(user.getId()));
        Assert.assertThat(result.getName(), is("other_name"));
        Assert.assertThat(result.getTelNumber(), is("some_tel"));
        userDAO.deleteUser(user.getId());
    }
}