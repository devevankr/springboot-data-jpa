package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
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
