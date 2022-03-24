package com.example.mediatek86formations.vue;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.example.mediatek86formations.*;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


@RunWith(JUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    /**
     * Teste le filtre sur les formations.
     */
    @Test
    public void scnearioFiltreSurFormations() {
        String titre = "Python n°18 : Décorateur singleton";
        onView(withId(R.id.btnFormations)).perform(click());
        onView(withId(R.id.txtFiltre)).perform(typeText("python"), closeSoftKeyboard());
        onView(withId(R.id.btnFiltrer)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.txtTitle))
                .onChildView(withText(titre)).check(matches(isDisplayed()));
    }

    /**
     * Teste l'ajout et le retrait d'un favori.
     */
    @Test
    public void scenarioAjoutFavoris() {
        onView(withId(R.id.btnFormations)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.btnListFavori))
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.btnFavoris))
                .onChildView(withHint(R.drawable.coeur_rouge));

        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.btnListFavori))
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.btnFavoris))
                .onChildView(withHint(R.drawable.coeur_gris));
    }

    /**
     * Teste la présence d'un favori ajouté au préalable.
     */
    @Test
    public void presenceDansListeFavoris() {
        onView(withId(R.id.btnFormations)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.btnListFavori))
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.btnFavoris))
                .onChildView(withHint(R.drawable.coeur_rouge));

        onView(isRoot()).perform(ViewActions.pressBack());

        onView(withId(R.id.btnFavoris)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.txtTitle))
                .onChildView(withText("Eclipse n°8 : Déploiement")).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.lstFormations))
                .atPosition(0)
                .onChildView(withId(R.id.btnListFavori))
                .perform(click());
    }


}