package vo;
/**
 * 将用户信息打包
 * @author asd99
 *
 */
public class UserInfo {
	public String toString() {
		return "UserInfo [username=" + username + ", password=" + password + ", nickname=" + nickname + ", phone="
				+ phone + "]";
	}

	private String username,password,nickname,phone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
