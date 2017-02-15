package jp.honkot.exercize.basic.wwword.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import jp.honkot.exercize.basic.wwword.PreferenceActivity;
import jp.honkot.exercize.basic.wwword.service.NotificationService;
import jp.honkot.exercize.basic.wwword.WordEditActivity;
import jp.honkot.exercize.basic.wwword.WordListActivity;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(Application application);

    void inject(WordEditActivity activity);

    void inject(WordListActivity activity);

    void inject(PreferenceActivity activity);

    void inject(NotificationService service);
}
