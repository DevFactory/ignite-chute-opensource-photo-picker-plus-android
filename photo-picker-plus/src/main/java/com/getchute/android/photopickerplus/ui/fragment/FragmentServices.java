/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2013 Chute
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.getchute.android.photopickerplus.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.api.model.enums.AccountType;
import com.getchute.android.photopickerplus.config.PhotoPicker;
import com.getchute.android.photopickerplus.models.enums.LocalServiceType;
import com.getchute.android.photopickerplus.ui.adapter.ServicesRecyclerAdapter;
import java.util.List;

public class FragmentServices extends ActionBarFragment {

    private ServicesRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ServiceClickedListener serviceClickedListener;

    public interface ServiceClickedListener {

        void accountLogin(AccountType accountType);

        void photoStream();

        void cameraRoll();

        void lastPhoto();

        void takePhoto();

        void recordVideo();

        void lastVideo();
    }

    public static FragmentServices newInstance(String[] services) {
        FragmentServices frag = new FragmentServices();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        serviceClickedListener = (ServiceClickedListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.gc_fragment_services, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view
                .findViewById(R.id.gcRecyclerViewServices);
        int columns = getResources().getInteger(R.integer.grid_columns_services);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), columns);
        recyclerView.setLayoutManager(gridLayoutManager);

        PhotoPicker singleton = PhotoPicker.getInstance();
        configureServices(singleton.getRemoteServices(),
                singleton.getLocalServices());
    }

    private void configureServices(List<AccountType> remoteServices,
            List<LocalServiceType> localServices) {
        adapter = new ServicesRecyclerAdapter(getActivity(), remoteServices,
                localServices, serviceClickedListener);
        recyclerView.setAdapter(adapter);
    }
}
