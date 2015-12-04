package kr.ds.data;

import android.os.Parcel;
import android.os.Parcelable;

public class DataHandler implements Parcelable{
	
	private String md_uid;
	private String md_id;
	private String md_pw;
	private String md_name;
	private String md_tell;
	private String md_state;
	private String md_regdate;
	private String cupon_total;
	private String cupon_use;
	
	public DataHandler(){
		
	}
	public String getMd_uid() {
		return md_uid;
	}
	public void setMd_uid(String md_uid) {
		this.md_uid = md_uid;
	}
	public String getMd_id() {
		return md_id;
	}
	public void setMd_id(String md_id) {
		this.md_id = md_id;
	}
	public String getMd_pw() {
		return md_pw;
	}
	public void setMd_pw(String md_pw) {
		this.md_pw = md_pw;
	}
	public String getMd_name() {
		return md_name;
	}
	public void setMd_name(String md_name) {
		this.md_name = md_name;
	}
	public String getMd_tell() {
		return md_tell;
	}
	public void setMd_tell(String md_tell) {
		this.md_tell = md_tell;
	}
	public String getMd_state() {
		return md_state;
	}
	public void setMd_state(String md_state) {
		this.md_state = md_state;
	}
	public String getMd_regdate() {
		return md_regdate;
	}
	public void setMd_regdate(String md_regdate) {
		this.md_regdate = md_regdate;
	}
	public String getCupon_total() {
		return cupon_total;
	}
	public void setCupon_total(String cupon_total) {
		this.cupon_total = cupon_total;
	}
	public String getCupon_use() {
		return cupon_use;
	}
	public void setCupon_use(String cupon_use) {
		this.cupon_use = cupon_use;
	}
	
	public DataHandler (Parcel src){
		this.md_uid = src.readString();
		this.md_id = src.readString();
		this.md_pw = src.readString();
		this.md_name = src.readString();
		this.md_tell = src.readString();
		this.md_state = src.readString();
		this.md_regdate = src.readString();
		this.cupon_total = src.readString();
		this.cupon_use = src.readString();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.md_uid);
		dest.writeString(this.md_id);
		dest.writeString(this.md_pw);
		dest.writeString(this.md_name);
		dest.writeString(this.md_tell);
		dest.writeString(this.md_state);
		dest.writeString(this.md_regdate);
		dest.writeString(this.cupon_total);
		dest.writeString(this.cupon_use);
	}
	public static final Parcelable.Creator CREATOR = new Creator() { //데이터 가져오기

		@Override
		public Object createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new DataHandler(in);
		}
		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DataHandler[size];
		}
	};

	

}
