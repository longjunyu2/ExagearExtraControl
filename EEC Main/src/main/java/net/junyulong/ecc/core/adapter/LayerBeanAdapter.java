package net.junyulong.ecc.core.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecElementNotFoundException;
import net.junyulong.ecc.core.eventbus.EventBus;
import net.junyulong.ecc.core.eventbus.events.LayerRemovedEvent;
import net.junyulong.ecc.core.eventbus.events.LayerVisibilityChangedEvent;
import net.junyulong.ecc.core.local.LocalStrings;
import net.junyulong.ecc.core.model.layout.EecLayoutModelWrapper;
import net.junyulong.ecc.core.model.layout.layer.EecLayerModel;
import net.junyulong.ecc.core.resource.EecColorResources;
import net.junyulong.ecc.core.resource.EecImageResources;
import net.junyulong.ecc.core.widgets.buttonView.UnityThemeButton;
import net.junyulong.ecc.core.widgets.buttonView.UnityThemeImageButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;

public class LayerBeanAdapter extends BaseAdapter {

    // bean列表
    private List<LayerBean> list;

    // 当前布局封装实例
    private final EecLayoutModelWrapper layoutWrapper;

    // 当前上下文
    private final Context context;

    // 按钮大小
    private final static int ButtonSizeDp = 30;

    // 边距大小
    private final static int MarginSizeDp = 5;

    // 文字大小
    private final static int TextSizeSp = 13;

    public LayerBeanAdapter(Context context, EecLayoutModelWrapper wrapper, List<LayerBean> list) {
        this.layoutWrapper = wrapper;
        this.context = context;
        this.list = list == null ? new ArrayList<>() : list;
    }

    // 获取数据数量
    @Override
    public int getCount() {
        return list.size();
    }

    // 根据位置获取数据对象
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    // 根据位置获取数据的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 创建每项View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int buttonSizePx = EEC.getInstance().getEecWindowManager().getPxFromDp(ButtonSizeDp);
        int marginPx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginSizeDp);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        // 选择按钮
        AppCompatRadioButton selectButton = new AppCompatRadioButton(context);
        LinearLayout.LayoutParams selectParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        selectParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        selectParams.gravity = Gravity.CENTER;
        selectButton.setLayoutParams(selectParams);
        selectButton.setOnClickListener(v -> {
            // 更改布局中的默认层
            layoutWrapper.getLayer(((LayerBean) getItem(position)).getLayerName()).setDefault(true);
            // 更新列表
            notifyDataSetChanged();
        });

        // 可见性控制按钮
        AppCompatButton controlButton = new AppCompatButton(context);
        LinearLayout.LayoutParams controlParams = new LinearLayout.LayoutParams(buttonSizePx, buttonSizePx);
        controlParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        controlParams.gravity = Gravity.CENTER;
        controlButton.setLayoutParams(controlParams);
        controlButton.setOnClickListener(v -> {
            // 获取bean实例
            LayerBean theBean = ((LayerBean) getItem(position));
            // 改变布局中层的可见性信息
            layoutWrapper.getLayer(theBean.getLayerName()).setShowing(!theBean.isShowing());
            // 通知层的可见性发生变化
            EventBus.getDefaultEventBus().post(new LayerVisibilityChangedEvent(theBean.getLayerName(), theBean.isShowing()));
            // 更新列表
            notifyDataSetChanged();
        });

        // 图层名称
        UnityThemeButton textView = new UnityThemeButton(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER;
        textParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        textParams.weight = 1;
        textView.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
        textView.setTextSize(TextSizeSp);
        textView.setLayoutParams(textParams);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.setOnClickListener(v -> {
            // 显示层信息对话框
            createLayerInfoDialog(
                    layoutWrapper.getLayer(((LayerBean) getItem(position)).getLayerName())
            ).show();
        });

        // 修改层按钮
        UnityThemeImageButton editButton = new UnityThemeImageButton(context,
                EecImageResources.baseline_edit_white_24dp);
        LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(buttonSizePx, buttonSizePx);
        editParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        editParams.gravity = Gravity.CENTER;
        editButton.setLayoutParams(editParams);
        editButton.setOnClickListener(v -> {
            // TODO: 修改层信息功能
        });

        // 移除层按钮
        UnityThemeImageButton removeButton = new UnityThemeImageButton(context,
                EecImageResources.baseline_delete_white_24dp);
        LinearLayout.LayoutParams removeParams = new LinearLayout.LayoutParams(buttonSizePx, buttonSizePx);
        removeParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        removeParams.gravity = Gravity.CENTER;
        removeButton.setLayoutParams(removeParams);
        removeButton.setOnClickListener(v -> {
            try {
                // 尝试获取层的model实例
                EecLayerModel model = layoutWrapper.getLayer(((LayerBean) getItem(position))
                        .getLayerName()).getLayerModel();
                // 从布局中移除层
                layoutWrapper.removeLayer(((LayerBean) getItem(position)).getLayerName());
                // 通知层被移除
                EventBus.getDefaultEventBus().post(new LayerRemovedEvent(model));
                // 更新列表
                notifyDataSetChanged();
            } catch (EecElementNotFoundException e) {
                e.printStackTrace();
            }
        });

        layout.addView(selectButton);
        layout.addView(controlButton);
        layout.addView(textView);
        layout.addView(editButton);
        layout.addView(removeButton);

        // 设置层名称
        textView.setText(list.get(position).getLayerName());
        // 设置选中状态
        selectButton.setChecked(list.get(position).isSelected());
        // 设置可见状态
        if (list.get(position).isShowing())
            controlButton.setBackground(EEC.getInstance().getEecResourcesManager().
                    getDrawable(EecImageResources.outline_eye_on_white_24dp));
        else
            controlButton.setBackground(EEC.getInstance().getEecResourcesManager().
                    getDrawable(EecImageResources.outline_eye_off_white_24dp));

        return layout;
    }

    @Override
    public void notifyDataSetChanged() {
        updateBeanList();
        super.notifyDataSetChanged();
    }

    private void updateBeanList() {
        if (list == null)
            list = new ArrayList<>();
        else
            list.clear();
        for (EecLayoutModelWrapper.EecLayerModelWrapper wrapper : layoutWrapper.getLayers())
            list.add(new LayerBean(wrapper.getLayerName(), wrapper.isShowing(), wrapper.isDefault()));
    }

    // 创建图层信息对话框
    private Dialog createLayerInfoDialog(EecLayoutModelWrapper.EecLayerModelWrapper layerWrapper) {
        // 文字大小
        int textSizeSp = 16;

        // 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context, THEME_DEVICE_DEFAULT_DARK)
                // 设置对话框标题
                .setTitle(LocalStrings.Layer_Information)
                // 设置确认按钮
                .setPositiveButton(LocalStrings.Accept, (dialog, which) -> dialog.dismiss());

        // 视图
        LinearLayout layout = new LinearLayout(builder.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // 滚动视图
        ScrollView scrollView = new ScrollView(builder.getContext());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(scrollView);
        // 内部视图
        LinearLayout innerLayout = new LinearLayout(builder.getContext());
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        scrollView.addView(innerLayout);
        // 标题: 图层名称
        innerLayout.addView(createLayerInfoTextView(true,
                LocalStrings.Layer_Name, textSizeSp, builder.getContext()));
        // 文本: 图层名称
        innerLayout.addView(createLayerInfoTextView(false,
                layerWrapper.getLayerName(), textSizeSp, builder.getContext()));
        // 标题: 图层描述
        innerLayout.addView(createLayerInfoTextView(true,
                LocalStrings.Layer_Description, textSizeSp, builder.getContext()));
        // 文本: 图层描述
        innerLayout.addView(createLayerInfoTextView(false,
                layerWrapper.getLayerDes(), textSizeSp, builder.getContext()));

        // 设置视图
        builder.setView(layout);

        return builder.create();
    }

    // 创建图层信息文本框
    private TextView createLayerInfoTextView(boolean header, String text, int textSizeSp, Context context) {
        // 初始化数值
        int marginPx = EEC.getInstance().getEecWindowManager().getPxFromDp(MarginSizeDp);

        TextView textView = new TextView(context);
        textView.setTextSize(textSizeSp);
        LinearLayout.LayoutParams textLayerNameParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayerNameParams.setMargins(0, marginPx, 0, marginPx);
        textLayerNameParams.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        textView.setLayoutParams(textLayerNameParams);
        textView.setText(text);
        if (header) {
            textView.setTextColor(Color.parseColor(EecColorResources.ColorViceHeader));
        } else {
            textView.setTextColor(Color.parseColor(EecColorResources.ColorMainText));
        }
        return textView;
    }
}
