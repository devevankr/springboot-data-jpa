
### Spring Boot - data JPA

---

#### 2021.03.27 saturday

create data-jpa project (spring boot)

아래는 기본 Repository를 생성하는 방식이다.
```java
@Repository
public class MemberJpaRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Member save(Member member) {
		em.persist(member);
		return member;
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
	
}
```
위처럼, 매번 Repository를 새로 작성하여 생성하는 것 보다는, JpaRepository를 상속받는 Interface를 작성하여 사용하는 것이 편하다.

아래는 JpaRepository를 상속받는 Interface이다.
```java
public interface MemberRepository extends JpaRepository<Member, Long>{

}

```
직접 구현한 Repository와 상속을 받아 등록한 JpaRepository를 비교하는 JUnit Test를 작성하여 비교하였다.


```java
/*
	직접 구현 후 Repository 등록
*/
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import study.datajpa.entity.Member;

// @Runwith... -> Junit5부터는 안써도됨. 
@SpringBootTest
@Transactional
@Rollback(false)// -> 이경우에는 롤백을 하지 않음.
// springboot는 Transactional이 있으면 끝날 때 rollback을 시켜버림.
// import org.springframework.transaction.annotation.Transactional; 
public class MemberJpaRepositoryTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;
	
	@Test
	public void testMember() {
		Member member = new Member("memberA");
		Member savedMember = memberJpaRepository.save(member);
		
		Member findMember = memberJpaRepository.find(savedMember.getId());
		
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
		
		// jpa에서 데이터를 가져오면, savedMember과 findMember는 같은 인스턴스임을 보장함.
	}
	
	
}

```
```java
/*
	JpaRepository를 상속받아 구현
*/
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void testMember() {
		
		Member member = new Member("memberA");
		
		Member savedMember = memberRepository.save(member);
		
		Member findMember = memberRepository.findById(savedMember.getId()).get();
		
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
		
	}
	
}

```

추가로, 오늘 강의를 들으며 알게 된 내용들에 대해 아래와 같이 정리를 했다.

- Entity의 기본 생성자는 protected로 지정 - proxy 과정에서 private로 지정된 생성자에 대한 핸들링에 제한이 있을 수 있고, 아무 곳에서나 생성자 오/남용을 방지하기 위해 protected로 지정.
- Entity는 Setter사용을 최대한 하지 않는 것이 중요. 만약 데이터 변경이 필요한 경우, Builder 패턴, modelmapper, 또는 별도의 메서드를 만들어서 호출
```java
	public void changeUsername(String username) {
		this.username = username;
	} // 이런식으로.
 ```
 
 ---
 
 
 
