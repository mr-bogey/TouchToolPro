package top.bogey.touch_tool.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import top.bogey.touch_tool.data.Task;
import top.bogey.touch_tool.data.action.convert.ValueConvertToString;
import top.bogey.touch_tool.data.action.start.NormalStartAction;
import top.bogey.touch_tool.data.action.state.TextStateAction;
import top.bogey.touch_tool.databinding.ViewTaskBlueprintBinding;

public class TaskView extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewTaskBlueprintBinding binding = ViewTaskBlueprintBinding.inflate(inflater, container, false);

        Task task = new Task();
        NormalStartAction startAction = new NormalStartAction();
        task.addAction(startAction);
        startAction.x = 1;
        startAction.y = 7;

        TextStateAction delayAction = new TextStateAction();
        task.addAction(delayAction);
        delayAction.x = 2;
        delayAction.y = 5;

        ValueConvertToString delayAction2 = new ValueConvertToString();
        task.addAction(delayAction2);
        delayAction2.x = 3;
        delayAction2.y = 5;

        binding.cardLayout.setTask(task);

        return binding.getRoot();
    }
}
