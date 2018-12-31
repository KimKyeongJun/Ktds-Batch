package kr.co.hucloud.batch.visit.vo;

public class RealTimeLog {

	private String url;
	private int count;

	public RealTimeLog(String[] arr) {
		this.url = arr[0];
		this.count = Integer.parseInt(arr[1]);
	}

	public RealTimeLog(String url, int count) {
		this.url = url;
		this.count = count;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "RealTimeLog [url=" + url + ", count=" + count + "]";
	}

}
