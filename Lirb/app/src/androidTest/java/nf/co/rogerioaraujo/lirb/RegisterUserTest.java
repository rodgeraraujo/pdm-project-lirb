package nf.co.rogerioaraujo.lirb;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import nf.co.rogerioaraujo.lirb.activity.RegisterActivity;
import nf.co.rogerioaraujo.lirb.model.Status;
import nf.co.rogerioaraujo.lirb.model.User;
import nf.co.rogerioaraujo.lirb.services.RegisterUserService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static java.util.regex.Pattern.matches;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RegisterUserTest {
    private RegisterUserService registerUserService;
    private RegisterActivity registerActivity;
    private User newUser;


    @Before
    public void setup() {
        java.util.Date newDate = new Date();
        java.sql.Date dateRegister = new java.sql.Date (newDate.getTime());

        newUser = new User(
                "testeNewUser",
                "teste@teste.com",
                "teste123",
                "teste",
                dateRegister,
                Status.ACTIVE
        );
    }

    @Test
    public void testNavigateToRegisterInterface() {
        Activity instance = getActivityInstance();
        onView(withText("Clique aqui!")).perform(click());
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof  RegisterActivity);
        assertTrue(b);
    }


    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("nf.co.rogerioaraujo.lirb", appContext.getPackageName());
    }


    // if new user
    @Test
    public void testRegisterNewUser() throws ExecutionException, InterruptedException {
        registerUserService = new RegisterUserService(this.registerActivity, newUser);
        String expected = "User registered successfully";
        String actual = String.valueOf(registerUserService.execute("").get());
        assertEquals(expected , actual);
    }

    // if the some user are registered
    @Test
    public void testRegisterUserRegisted() throws ExecutionException, InterruptedException {
        registerUserService = new RegisterUserService(this.registerActivity, newUser);
        String expected = "Connection goes wrong";
        String actual = String.valueOf(registerUserService.execute("").get());
        assertEquals(expected , actual);
    }


    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable( ) {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });

        return activity[0];
    }


}
