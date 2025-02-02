package com.edotassi.amazmod.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.edotassi.amazmod.R;
import com.edotassi.amazmod.adapters.SilencedApplicationsAdapter;
import com.edotassi.amazmod.databinding.FragmentSilencedAppsBinding;
import com.edotassi.amazmod.db.model.NotificationPreferencesEntity;
import com.edotassi.amazmod.support.SilenceApplicationHelper;
import com.edotassi.amazmod.ui.card.Card;

import org.tinylog.Logger;

import java.util.ArrayList;

public class SilencedApplicationsFragment extends Card {

    private FragmentSilencedAppsBinding binding;

    private SilencedApplicationsAdapter silencedApplicationsAdapter;
    private Context mContext;
    private View card;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity.getBaseContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSilencedAppsBinding.inflate(getLayoutInflater());
        card = binding.getRoot(); //inflater.inflate(R.layout.fragment_silenced_apps, container, false);

        silencedApplicationsAdapter = new SilencedApplicationsAdapter(this, R.layout.item_silenced_app, new ArrayList<NotificationPreferencesEntity>());
        binding.fragmentSilencedAppsGrid.setAdapter(silencedApplicationsAdapter);
        updateSilencedApps();
        return card;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.debug("SilencedApplicationsFragment onResume");

        updateSilencedApps();
    }

    @Override
    public String getName() {
        return "silenced-apps";
    }

    public void updateSilencedApps(){
        if (SilenceApplicationHelper.getSilencedApplicationsCount() > 0) {
            card.setVisibility(View.VISIBLE);
        }else{
            card.setVisibility(View.GONE);
        }
        silencedApplicationsAdapter.clear();
        silencedApplicationsAdapter.addAll(SilenceApplicationHelper.listSilencedApplications());
        silencedApplicationsAdapter.notifyDataSetChanged();
    }
}
