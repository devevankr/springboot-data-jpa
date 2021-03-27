package study.datajpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

	@Id // 식별자 맵핑
	@GeneratedValue // PK값을 알아서 시퀀스를 따줌.
	private Long id;
	
	private String username;

	// 기본 생성자는 protected
	// proxy 기술 등을 쓸 때 막힐 수 있으니, private는 안됨. protected까지만 열자.
	protected Member() {
		
	}
	
	public Member(String username) {
		this.username = username;
	}

	// 데이터 변경 시, 아래처럼 메서드 만들어서 호출.
	public void changeUsername(String username) {
		this.username = username;
	}
}
