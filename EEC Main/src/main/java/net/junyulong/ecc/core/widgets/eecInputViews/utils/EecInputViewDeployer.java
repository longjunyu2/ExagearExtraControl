package net.junyulong.ecc.core.widgets.eecInputViews.utils;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.widgets.eecInputViews.EecBaseInputViewConfig;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewAlignType;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewInterface;
import net.junyulong.ecc.core.widgets.eecInputViews.EecInputViewParentInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import static net.junyulong.ecc.core.widgets.eecTSController.EecTSControllerView.ParentId;

public class EecInputViewDeployer {

    private final EecInputViewParentInterface parentInterface;

    private final HashMap<String, PreViewPos> mViewPosMap;

    public EecInputViewDeployer(EecInputViewParentInterface parentInterface) {
        this.parentInterface = parentInterface;
        this.mViewPosMap = new HashMap<>();
        mViewPosMap.put(ParentId, new PreViewPos(0, EEC.getInstance().getEecWindowManager().getMaxCellCounts(),
                0, EEC.getInstance().getEecWindowManager().getMinimalCellCounts()));
    }

    public void updateAll() {
        for (EecInputViewInterface inputViewInterface : parentInterface.getEecInputViews())
            innerUpdate(inputViewInterface);
    }

    private void innerUpdate(EecInputViewInterface inputViewInterface) {
        innerUpdate(inputViewInterface.getEecConfig().getId());
    }

    private void innerUpdate(String id) {
        EecBaseInputViewConfig viewConfig = parentInterface.getEecInputViewById(id).getEecConfig();
        if (!mViewPosMap.containsKey(id) ||
                Objects.requireNonNull(mViewPosMap.get(id)).left == null ||
                Objects.requireNonNull(mViewPosMap.get(id)).top == null) {
            PreViewPos viewPos = new PreViewPos();
            mViewPosMap.put(id, viewPos);
            String verticalBindingId = viewConfig.getViewVR();
            String horizontalBindingId = viewConfig.getViewHR();

            // 判断绑定对象是否初始化完成
            if (!mViewPosMap.containsKey(horizontalBindingId) ||
                    Objects.requireNonNull(mViewPosMap.get(horizontalBindingId)).left == null)
                // 绑定对象初始化未完成，则进入递归
                innerUpdate(horizontalBindingId);
            else {
                // 若绑定对象初始化完成，则进行计算
                PreViewPos vtPos = Objects.requireNonNull(mViewPosMap.get(horizontalBindingId));
                switch (Objects.requireNonNull(EecInputViewAlignType.getType(viewConfig.getViewHRM()))) {
                    case Left_to_Left_of:
                        viewPos.left = vtPos.left + viewConfig.getViewHRD();
                        viewPos.right = viewPos.left + viewConfig.getViewWidth();
                        break;
                    case Left_to_Right_of:
                        viewPos.left = vtPos.right + viewConfig.getViewHRD();
                        viewPos.right = viewPos.left + viewConfig.getViewWidth();
                        break;
                    case Right_to_Left_of:
                        viewPos.right = vtPos.left + viewConfig.getViewHRD();
                        viewPos.left = viewPos.right - viewConfig.getViewWidth();
                        break;
                    case Right_to_Right_of:
                        viewPos.right = vtPos.right + viewConfig.getViewHRD();
                        viewPos.left = viewPos.right - viewConfig.getViewWidth();
                        break;
                    default:
                        throw new EecException("Undefined Align Type: " + viewConfig.getViewHRM() + " .");
                }
            }

            // 判断绑定对象是否初始化完成
            if (!mViewPosMap.containsKey(verticalBindingId) ||
                    Objects.requireNonNull(mViewPosMap.get(verticalBindingId)).left == null)
                // 绑定对象初始化未完成，则进入递归
                innerUpdate(verticalBindingId);
            else {
                // 若绑定对象初始化完成，则进行计算
                PreViewPos htPos = Objects.requireNonNull(mViewPosMap.get(verticalBindingId));
                switch (Objects.requireNonNull(EecInputViewAlignType.getType(viewConfig.getViewVRM()))) {
                    case Top_to_Top_of:
                        viewPos.top = htPos.top + viewConfig.getViewVRD();
                        viewPos.bottom = viewPos.top + viewConfig.getViewHeight();
                        break;
                    case Top_to_Bottom_of:
                        viewPos.top = htPos.bottom + viewConfig.getViewVRD();
                        viewPos.bottom = viewPos.top + viewConfig.getViewHeight();
                        break;
                    case Bottom_to_Top_of:
                        viewPos.bottom = htPos.top + viewConfig.getViewVRD();
                        viewPos.top = viewPos.bottom - viewConfig.getViewHeight();
                        break;
                    case Bottom_to_Bottom_of:
                        viewPos.bottom = htPos.bottom + viewConfig.getViewVRD();
                        viewPos.top = viewPos.bottom - viewConfig.getViewHeight();
                        break;
                    default:
                        throw new EecException("Undefined Align Type: " + viewConfig.getViewVRM() + " .");
                }
            }

            // 如果绑定父对象不是Parent，则向父对象注册子对象的Id
            if (horizontalBindingId.equals(verticalBindingId) && !horizontalBindingId.equals(ParentId))
                Objects.requireNonNull(mViewPosMap.get(horizontalBindingId)).childrenId.add(id);
            else if (!horizontalBindingId.equals(ParentId))
                Objects.requireNonNull(mViewPosMap.get(horizontalBindingId)).childrenId.add(id);
            else if (!verticalBindingId.equals(ParentId))
                Objects.requireNonNull(mViewPosMap.get(verticalBindingId)).childrenId.add(id);
        }
    }

    // 获取被绑定的控件数量
    public int getBoundViewsCounts(EecInputViewInterface inputViewInterface) {
        return getBoundViewsCounts(inputViewInterface.getEecConfig().getId());
    }

    public int getBoundViewsCounts(String id) {
        return getBoundViewsId(id).size();
    }

    // 获取被绑定的控件的Id集合
    public HashSet<String> getBoundViewsId(String id) {
        HashSet<String> tHashSet = Objects.requireNonNull(mViewPosMap.get(id)).childrenId;
        HashSet<String> hashSet = new HashSet<>(tHashSet);
        for (String boundId : tHashSet) {
            hashSet.addAll(getBoundViewsId(boundId));
        }
        return hashSet;
    }

    // 获取被绑定的控件的集合
    public HashSet<EecInputViewInterface> getBoundViews(String id) {
        HashSet<String> idHashSet = getBoundViewsId(id);
        HashSet<EecInputViewInterface> interfaceHashSet = new HashSet<>();
        for (String str : idHashSet) {
            interfaceHashSet.add(parentInterface.getEecInputViewById(str));
        }
        return interfaceHashSet;
    }

    // 获取控件真实宽度
    public static int getRealWidth(EecBaseInputViewConfig config) {
        return EEC.getInstance().getEecWindowManager().getRealBaseCl() * config.getViewWidth();
    }

    public static int getRealWidth(EecInputViewInterface inputViewInterface) {
        return getRealWidth(inputViewInterface.getEecConfig());
    }

    // 获取控件真实高度
    public static int getRealHeight(EecBaseInputViewConfig config) {
        return EEC.getInstance().getEecWindowManager().getRealBaseCl() * config.getViewHeight();
    }

    public static int getRealHeight(EecInputViewInterface inputViewInterface) {
        return getRealHeight(inputViewInterface.getEecConfig());
    }

    // 获取控件真实位置
    public RealViewPos getRealPos(EecInputViewInterface inputViewInterface) {
        return getRealPos(inputViewInterface.getEecConfig().getId());
    }

    public RealViewPos getRealPos(String id) {
        RealViewPos pos = new RealViewPos();
        PreViewPos pPos = mViewPosMap.get(id);
        int base = EEC.getInstance().getEecWindowManager().getRealBaseCl();
        pos.x = pPos.left * base;
        pos.y = pPos.top * base;
        return pos;
    }

    // 更新某一控件
    public void updateView(EecInputViewInterface inputViewInterface) {
        updateView(inputViewInterface.getEecConfig().getId());
    }

    public void updateView(String id) {
        innerUpdate(id);
    }

    public static class RealViewPos {
        public int x;
        public int y;
    }

    public static class PreViewPos {
        Integer left;
        Integer right;
        Integer top;
        Integer bottom;
        final HashSet<String> childrenId;

        public PreViewPos(int l, int r, int t, int b) {
            this();
            this.left = l;
            this.right = r;
            this.top = t;
            this.bottom = b;
        }

        public PreViewPos() {
            childrenId = new HashSet<>();
        }
    }

}
