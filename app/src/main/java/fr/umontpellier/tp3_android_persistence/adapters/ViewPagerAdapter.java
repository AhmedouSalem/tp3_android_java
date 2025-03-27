package fr.umontpellier.tp3_android_persistence.adapters;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import fr.umontpellier.tp3_android_persistence.fragments.FragmentLogin;
import fr.umontpellier.tp3_android_persistence.fragments.FragmentInfo;
import fr.umontpellier.tp3_android_persistence.fragments.FragmentSummary;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private Bundle bundle;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
        super(fragmentActivity);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentLogin();
            case 1:
                return new FragmentInfo();
            case 2:
                FragmentSummary fragmentSummary = new FragmentSummary();
                fragmentSummary.setArguments(bundle);
                return fragmentSummary;
            default:
                return new FragmentLogin();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
        notifyDataSetChanged();
    }
}
