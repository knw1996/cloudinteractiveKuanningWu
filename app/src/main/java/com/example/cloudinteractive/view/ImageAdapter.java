package com.example.cloudinteractive.view;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cloudinteractive.R;
import com.example.cloudinteractive.databinding.ImageItemBinding;
import com.example.cloudinteractive.model.HttpMethods;
import com.example.cloudinteractive.model.ImageContent;
import com.example.cloudinteractive.viewmodel.ImageDownloadViewModel;

import java.util.List;
import java.util.Objects;


//圖片recyclerview的adapter
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    public static final String TAG = "ImageAdapter";
    List<? extends ImageContent> projectList;

    @Nullable
    private final ImageClickCallback imageClickCallback;
    private LifecycleOwner mOwner;
    private Application mApplication;

    public ImageAdapter(LifecycleOwner owner, Application application, @Nullable ImageClickCallback imageClickCallback) {
        this.mOwner = owner;
        this.mApplication = application;
        this.imageClickCallback = imageClickCallback;
    }

    public void setProjectList(final List<? extends ImageContent> projectList) {
        if (this.projectList == null) {
            this.projectList = projectList;
            notifyItemRangeInserted(0, projectList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ImageAdapter.this.projectList.size();
                }

                @Override
                public int getNewListSize() {
                    return projectList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ImageAdapter.this.projectList.get(oldItemPosition).getId() ==
                            projectList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ImageContent project = projectList.get(newItemPosition);
                    ImageContent old = projectList.get(oldItemPosition);
                    return project.getId() == old.getId()
                            && Objects.equals(project.getThumbnailUrl(), old.getThumbnailUrl())
                            && Objects.equals(project.getImageBytes(), old.getImageBytes());
                }
            });
            this.projectList = projectList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_image,
                        parent, false);

        binding.setCallback(imageClickCallback);

        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageContent content = projectList.get(position);
        if(content.getImageBytes()==null){
            final ImageDownloadViewModel viewModel = new ImageDownloadViewModel(HttpMethods.getInstance(),mApplication,content.getThumbnailUrl());
            observeImage(viewModel,content,holder);
        }
        holder.binding.setImage(content);
        holder.binding.executePendingBindings();
    }

    public  void observeImage(ImageDownloadViewModel viewModel, ImageContent content, ImageViewHolder holder){
        Log.d(TAG,"observe image "+content.getThumbnailUrl());
        viewModel.getDownloadObservable().observe(mOwner, new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] bytes) {
                content.setImageBytes(bytes);
                Log.d(TAG,"observe image success "+content.getThumbnailUrl());
                holder.binding.setImage(content);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList == null ? 0 : projectList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        final ImageItemBinding binding;

        public ImageViewHolder(ImageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
