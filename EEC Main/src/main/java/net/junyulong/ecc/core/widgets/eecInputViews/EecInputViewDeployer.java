package net.junyulong.ecc.core.widgets.eecInputViews;

import net.junyulong.ecc.core.EEC;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.model.layout.layer.view.EecInputViewModel;

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

    // 更新全部的控件位置
    public void updateAll() {
        for (EecInputViewChildInterface inputViewInterface : parentInterface.getEecInputViews())
            innerUpdate(inputViewInterface);
    }

    private void innerUpdate(EecInputViewChildInterface inputViewInterface) {
        innerUpdate(inputViewInterface.getModel().getId());
    }

    private void innerUpdate(String id) {
        EecInputViewModel inputViewModel = parentInterface.getEecInputViewById(id).getModel();
        PreViewPos viewPos;
        if (mViewPosMap.containsKey(id))
            viewPos = Objects.requireNonNull(mViewPosMap.get(id));
        else {
            viewPos = new PreViewPos();
            mViewPosMap.put(id, viewPos);
        }
        String verticalBindingId = inputViewModel.getBind().vertical.referenceId;
        String horizontalBindingId = inputViewModel.getBind().horizontal.referenceId;

        // 判断绑定对象是否初始化完成
        if (!mViewPosMap.containsKey(horizontalBindingId) ||
                Objects.requireNonNull(mViewPosMap.get(horizontalBindingId)).left == null)
            // 绑定对象初始化未完成，则进入递归
            innerUpdate(horizontalBindingId);
        else {
            // 若绑定对象初始化完成，则进行计算
            PreViewPos vtPos = Objects.requireNonNull(mViewPosMap.get(horizontalBindingId));
            switch (Objects.requireNonNull(EecInputViewAlignBindType.getType(inputViewModel.getBind().horizontal.referenceMode))) {
                case Left_to_Left_of:
                    viewPos.left = vtPos.left + inputViewModel.getBind().horizontal.offset;
                    viewPos.right = viewPos.left + inputViewModel.getParam().width;
                    break;
                case Left_to_Right_of:
                    viewPos.left = vtPos.right + inputViewModel.getBind().horizontal.offset;
                    viewPos.right = viewPos.left + inputViewModel.getParam().width;
                    break;
                case Right_to_Left_of:
                    viewPos.right = vtPos.left + inputViewModel.getBind().horizontal.offset;
                    viewPos.left = viewPos.right - inputViewModel.getParam().width;
                    break;
                case Right_to_Right_of:
                    viewPos.right = vtPos.right + inputViewModel.getBind().horizontal.offset;
                    viewPos.left = viewPos.right - inputViewModel.getParam().width;
                    break;
                default:
                    throw new EecException("Undefined Align Type: " + inputViewModel.getBind().horizontal.referenceMode + " .");
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
            switch (Objects.requireNonNull(EecInputViewAlignBindType.getType(inputViewModel.getBind().vertical.referenceMode))) {
                case Top_to_Top_of:
                    viewPos.top = htPos.top + inputViewModel.getBind().vertical.offset;
                    viewPos.bottom = viewPos.top + inputViewModel.getParam().height;
                    break;
                case Top_to_Bottom_of:
                    viewPos.top = htPos.bottom + inputViewModel.getBind().vertical.offset;
                    viewPos.bottom = viewPos.top + inputViewModel.getParam().height;
                    break;
                case Bottom_to_Top_of:
                    viewPos.bottom = htPos.top + inputViewModel.getBind().vertical.offset;
                    viewPos.top = viewPos.bottom - inputViewModel.getParam().height;
                    break;
                case Bottom_to_Bottom_of:
                    viewPos.bottom = htPos.bottom + inputViewModel.getBind().vertical.offset;
                    viewPos.top = viewPos.bottom - inputViewModel.getParam().height;
                    break;
                default:
                    throw new EecException("Undefined Align Type: " + inputViewModel.getBind().vertical.referenceMode + " .");
            }
        }

        // 如果绑定父对象不是Parent，则向父对象注册子对象的Id
        if (!horizontalBindingId.equals(ParentId))
            Objects.requireNonNull(mViewPosMap.get(horizontalBindingId)).childrenId.add(id);
        if (!verticalBindingId.equals(ParentId))
            Objects.requireNonNull(mViewPosMap.get(verticalBindingId)).childrenId.add(id);

        // 如果存在子对象，则更新子对象的部署信息
        if (getBoundViewsCounts(id, BindType.StrongBindChild) != 0) {
            for (String childId : getStrongBoundChildViewsId(id)) {
                updateView(childId);
            }
        }
    }

    // 获取绑定的控件数量
    public int getBoundViewsCounts(String id, BindType bindType) {
        return getBoundViewsInterface(id, bindType).size();
    }

    public int getBoundViewsCounts(EecInputViewChildInterface childInterface, BindType bindType) {
        return getBoundViewsCounts(childInterface.getModel().getId(), bindType);
    }

    // 获取绑定的控件集合
    public HashSet<EecInputViewChildInterface> getBoundViewsInterface(
            String sourceId, BindType bindType) {
        switch (bindType) {
            case StrongBindChild:
                return getStrongBoundChildViews(sourceId);
            case StrongBindParent:
                // TODO:
                return null;
            case WeakBindChild:
                return getWeakBoundChildViews(sourceId);
            case WeakBindParent:
                // TODO:
                return null;
            case All:
                // TODO:
                return null;
            default:
                return null;
        }
    }

    public HashSet<EecInputViewChildInterface> getBoundViewsInterface(
            EecInputViewChildInterface childInterface, BindType bindType) {
        return getBoundViewsInterface(childInterface.getModel().getId(), bindType);
    }

    private HashSet<String> getStrongBoundChildViewsId(String sourceId) {
        return new HashSet<>(Objects.requireNonNull(mViewPosMap.get(sourceId)).childrenId);
    }

    private HashSet<EecInputViewChildInterface> getStrongBoundChildViews(String sourceId) {
        HashSet<String> idHashSet = getStrongBoundChildViewsId(sourceId);
        HashSet<EecInputViewChildInterface> result = new HashSet<>();
        for (String str : idHashSet) {
            result.add(parentInterface.getEecInputViewById(str));
        }
        return result;
    }

    private HashSet<String> getWeakBoundChildViewsId(String sourceId) {
        HashSet<String> result = new HashSet<>();
        HashSet<String> idHashSet = getStrongBoundChildViewsId(sourceId);
        for (String id : idHashSet) {
            result.addAll(getWeakBoundChildViewsId(id));
        }
        return result;
    }

    private HashSet<EecInputViewChildInterface> getWeakBoundChildViews(String sourceId) {
        HashSet<String> idHashSet = getWeakBoundChildViewsId(sourceId);
        HashSet<EecInputViewChildInterface> result = new HashSet<>();
        for (String str : idHashSet) {
            result.add(parentInterface.getEecInputViewById(str));
        }
        return result;
    }

    // 获取控件真实宽度
    public int getRealWidth(EecInputViewModel model) {
        return EEC.getInstance().getEecWindowManager().getRealBaseCl() * model.getParam().width;
    }

    public int getRealWidth(EecInputViewChildInterface inputViewInterface) {
        return getRealWidth(inputViewInterface.getModel());
    }

    // 获取控件真实高度
    public int getRealHeight(EecInputViewModel model) {
        return EEC.getInstance().getEecWindowManager().getRealBaseCl() * model.getParam().height;
    }

    public int getRealHeight(EecInputViewChildInterface inputViewInterface) {
        return getRealHeight(inputViewInterface.getModel());
    }

    // 获取控件真实位置
    public RealViewPos getRealPos(EecInputViewChildInterface inputViewInterface) {
        return getRealPos(inputViewInterface.getModel().getId());
    }

    public RealViewPos getRealPos(String id) {
        RealViewPos pos = new RealViewPos();
        PreViewPos pPos = Objects.requireNonNull(mViewPosMap.get(id));
        int base = EEC.getInstance().getEecWindowManager().getRealBaseCl();
        pos.x = pPos.left * base;
        pos.y = pPos.top * base;
        return pos;
    }

    // 更新某一控件位置
    public void updateView(EecInputViewChildInterface inputViewInterface) {
        updateView(inputViewInterface.getModel().getId());
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

    public enum BindType {
        // 强绑定
        StrongBindChild,
        StrongBindParent,
        // 弱绑定
        WeakBindChild,
        WeakBindParent,
        // 全部
        All
    }

    public enum RefType {
        Parent,
        Children,
        All
    }

}
