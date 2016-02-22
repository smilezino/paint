package top.wangjun.enums;

/**
 *
 */
public enum Status {
    CLOSE(0),
    OPEN(1);

    Status(int value) {
        this.value = value;
    }

    public int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
