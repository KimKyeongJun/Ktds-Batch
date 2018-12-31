package kr.co.hucloud.batch.visit.vo;

public class IdTopic {

	private String ip;
	private String id;
	private String yyyymmdd;
	private String url;
	private String method;
	private int count;

	public IdTopic(String[] arr) {
		this.ip = arr[0].trim();
		this.id = arr[1].trim();
		this.yyyymmdd = arr[2].trim();
		this.url = arr[3].trim();
		this.method = arr[4].trim();
		this.count = Integer.parseInt(arr[5].trim());
	}

	public IdTopic(String ip, String id, String yyyymmdd, String url, String method, int count) {
		this.ip = ip;
		this.id = id;
		this.yyyymmdd = yyyymmdd;
		this.url = url;
		this.method = method;
		this.count = count;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "IdTopic [ip=" + ip + ", id=" + id + ", yyyymmdd=" + yyyymmdd + ", url=" + url + ", method=" + method
				+ ", count=" + count + "]";
	}

}
