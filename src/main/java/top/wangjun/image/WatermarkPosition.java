package top.wangjun.image;

/**
 * 水印位置
 */
public enum WatermarkPosition {

	RIGHT_BOTTOM(1, "右下"),
	RIGHT_TOP(2, "右上"),
	LEFT_BOTTOM(3, "左下"),
	LEFT_TOP(4, "左上"),
	CENTER(5, "居中");

	WatermarkPosition(int position, String description) {
		this.position = position;
		this.description = description;
	}

	public static WatermarkPosition getPosition(int value) {
		for(WatermarkPosition position : WatermarkPosition.values()) {
			if(position.getPosition() == value) return position;
		}
		return null;
	}

	private int position;
	private String description;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
