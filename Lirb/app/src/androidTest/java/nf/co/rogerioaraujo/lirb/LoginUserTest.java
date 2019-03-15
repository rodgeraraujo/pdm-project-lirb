package nf.co.rogerioaraujo.lirb;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import nf.co.rogerioaraujo.lirb.activity.LoginActivity;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoginUserTest {

    @Test
    public void testMD5Hashing() {
        LoginActivity loginActivity = new LoginActivity();

        String actual = loginActivity.md5hashing("testePassword");

        assertEquals("c2a7713f6b5e4e7982db2b8910899faf", actual);

    }


}
