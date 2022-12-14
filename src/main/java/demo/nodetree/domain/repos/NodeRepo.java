package demo.nodetree.domain.repos;

import demo.nodetree.domain.entites.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NodeRepo extends JpaRepository<Node, Long> {
    List<Node> getAllNodes();
}
