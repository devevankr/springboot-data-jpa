package study.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected default constructor
@ToString(of = {"id", "username", "age"}) // team처럼 연관관계 필드는 최대한 ToString에 안넣어주는 것이 좋음. (무한루프)
public class Member {

	@Id // 식별자 맵핑
	@GeneratedValue // PK값을 알아서 시퀀스를 따줌.
	@Column(name="member_id") // 관례상 테이블명_id 로 컬럼을 사용
	private Long id;
	
	private String username;

	private int age;
	
	// 실무에서는 ManyToOne관계예서는 FetchType.LAZY로 세팅해야함.
	@ManyToOne(fetch = FetchType.LAZY)
	// ManyToOne, OneToMany 각 Entity에 대해서 해야함.
	@JoinColumn(name="team_id")
	private Team team;
	
	// 기본 생성자는 protected
	// proxy 기술 등을 쓸 때 막힐 수 있으니, private는 안됨. protected까지만 열자.
	/*
	protected Member() {
		
	}
	*/
	
	public Member(String username) {
		this.username = username;
	}

	// 데이터 변경 시, 아래처럼 메서드 만들어서 호출.
	public void changeUsername(String username) {
		this.username = username;
	}
	
	public void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}

	public Member(String username, int age, Team team) {
	    this.username = username;
		this.age = age;
		if(team != null) {
			changeTeam(team);
		}
	}
	
}
