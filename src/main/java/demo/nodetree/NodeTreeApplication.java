package demo.nodetree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import demo.nodetree.domain.repos.NodeRepo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class NodeTreeApplication implements CommandLineRunner {

    @Autowired
    private NodeRepo repo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public static void main(String[] args) {
        SpringApplication.run(NodeTreeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        int height = 2;
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].contains("node.height")) {
//                height = Integer.parseInt(args[i].split("=")[1]);
//            }
//        }
//
//        logger.info("Inserting dummy data nodes with height " + height);
//        Node root = new Node("root");
//        repo.save(root);
//        root.setRoot_id(root.getId());
//
//        Node a = new Node("a", 1, root.getRoot_id());
//        Node b = new Node("b", 1, root.getRoot_id());
//        a.setParent(root);
//        b.setParent(root);
//
//        repo.save(root);
//        repo.save(a);
//        repo.save(b);
//        createNewChildTree(a, height);
//        createNewChildTree(b, height);
//        List<Node> nodeList = repo.findAll();
//        logger.info("Total nodes " + nodeList.size());
//
//    }
//
//    private void createNewChildTree(Node parent, int height) {
//        if (parent.getHeight() == height) return;
//        List<Node> list = new ArrayList<Node>();
//
//        IntStream.range(1, 9).forEach(
//                nbr -> {
//                    list.add(new Node(parent.getName() + nbr, (parent.getHeight() + 1), parent.getRoot_id()));
//                }
//        );
//        list.forEach(e -> {
//            e.setParent(parent);
//            repo.save(e);
//        });
//
//        list.forEach(e -> {
//            createNewChildTree(e, height);
//        });

    }
}
