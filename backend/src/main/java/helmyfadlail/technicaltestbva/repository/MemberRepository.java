package helmyfadlail.technicaltestbva.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import helmyfadlail.technicaltestbva.entity.Member;
import helmyfadlail.technicaltestbva.entity.User;

public interface MemberRepository extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {
    Optional<Member> findFirstByUserAndId(User user, String id);
}
