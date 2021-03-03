package ru.job4j.cinema.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class UserDTOTest {
    @Test
    public void whenCreateUserDtoThenCorrect() {
        UserDTO userOne = new UserDTO();
        Assert.assertThat(userOne.getId(), is(0));
        Assert.assertThat(userOne.getName(), is(""));
        Assert.assertThat(userOne.getTelNumber(), is(""));
        userOne.setId(111);
        userOne.setName("some_setted_name");
        userOne.setTelNumber("some_setted_tel");
        Assert.assertThat(userOne.getId(), is(111));
        Assert.assertThat(userOne.getName(), is("some_setted_name"));
        Assert.assertThat(userOne.getTelNumber(), is("some_setted_tel"));
        UserDTO userTwo = new UserDTO("some_name", "some_tel");
        Assert.assertThat(userTwo.getId(), is(0));
        Assert.assertThat(userTwo.getName(), is("some_name"));
        Assert.assertThat(userTwo.getTelNumber(), is("some_tel"));
    }

    @Test
    public void whenTwoSimilarThenEquals() {
        UserDTO userOne = new UserDTO("some_name", "some_tel");
        UserDTO userTwo = new UserDTO("some_name", "some_tel");
        userOne.setId(1);
        userTwo.setId(1);
        Assert.assertEquals(userOne, userTwo);
    }

    @Test
    public void whenTwoDifferentThenEquals() {
        UserDTO userOne = new UserDTO("some_name", "some_tel");
        UserDTO userTwo = new UserDTO("some_name", "some_tel_");
        userOne.setId(1);
        userTwo.setId(1);
        Assert.assertNotEquals(userOne, userTwo);
    }
}