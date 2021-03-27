package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import study.datajpa.entity.Member;

// Member, Long에서 Long은 Entity의 Id Type을 넣어주면 됨.
public interface MemberRepository extends JpaRepository<Member, Long>{

}
