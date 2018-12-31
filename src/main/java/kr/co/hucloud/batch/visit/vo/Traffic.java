package kr.co.hucloud.batch.visit.vo;

public class Traffic {

	private String yyyymmdd;
	private String url;
	private String method;
	private int count;
	
	public Traffic(String[] arr) {
		this.yyyymmdd = arr[0];
		this.url = arr[1];
		this.method = arr[2];
		this.count = Integer.parseInt(arr[3]);
	}

	public Traffic(String yyyymmdd, String url, String method, int count) {
		this.yyyymmdd = yyyymmdd;
		this.url = url;
		this.method = method;
		this.count = count;
	}

	public String getYyyymmdd() {
		return yyyymmdd;
	}

	public void setYyyymmdd(String yyyymmdd) {
		this.yyyymmdd = yyyymmdd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Traffic [yyyymmdd=" + yyyymmdd + ", url=" + url + ", method=" + method + ", count=" + count + "]";
	}
	
	

}
