package fr.umontpellier.tp3_android_persistence.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import fr.umontpellier.tp3_android_persistence.dao.UserRepository;
import androidx.lifecycle.MutableLiveData;

import fr.umontpellier.tp3_android_persistence.models.User;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void loginExists(String login, UserRepository.ExistsCallback callback) {
        userRepository.loginExists(login, callback);
    }

    public void insertUser(User user, UserRepository.Callback callback) {
        userRepository.insertUser(user, callback);
    }

    public void emailExists(String email, UserRepository.ExistsCallback callback) {
        userRepository.emailExists(email, callback);
    }

    public void phoneExists(String phone, UserRepository.ExistsCallback callback) {
        userRepository.phoneExists(phone, callback);
    }

    public void getUserByLoginAndPassword(String login, String password, UserRepository.UserCallback callback) {
        userRepository.getUserByLoginAndPassword(login, password, callback);
    }

    public void getUserByEmailAndPassword(String email, String password, UserRepository.UserCallback callback) {
        userRepository.getUserByEmailAndPassword(email, password, callback);
    }
}
