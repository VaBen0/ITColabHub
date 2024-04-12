package ru.dvteam.itcollabhub.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.MainActivity;
import ru.dvteam.itcollabhub.view.authorizeviews.ConfirmReg;
import ru.dvteam.itcollabhub.view.authorizeviews.LogIn;
import ru.dvteam.itcollabhub.view.profileviews.activities.ChooseThemeForApp;
import ru.dvteam.itcollabhub.view.profileviews.activities.EditProfile;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;

@Singleton
@Component(modules = {SharedPreferencesModule.class})
public interface AppComponent {
    void inject(Profile profile);
    void inject(LogIn logIn);
    void inject(ChooseThemeForApp chooseThemeForApp);
    void inject(EditProfile editProfile);
    void inject(MainActivity mainActivity);
    void inject(ConfirmReg confirmReg);
}
