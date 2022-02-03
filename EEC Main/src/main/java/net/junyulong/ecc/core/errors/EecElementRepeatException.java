package net.junyulong.ecc.core.errors;

// 指定元素的包含内容或自身存在重复
public class EecElementRepeatException extends EecException {
    public EecElementRepeatException() {

    }

    public EecElementRepeatException(String message) {
        super(message);
    }
}
