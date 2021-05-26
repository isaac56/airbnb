package team14.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team14.airbnb.domain.aggregate.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
