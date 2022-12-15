package demo.nodetree.controllers;

import demo.nodetree.domain.entites.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import demo.nodetree.domain.repos.NodeRepo;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("/get/all/1")
public class NodeControllerForGetAll {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NodeRepo repo;

    @GetMapping(value = "/children/{id}", produces = "application/json")
    @ResponseBody
    public Map<Node, List<Node>> getChildNodes(@PathVariable long id) {
        List<Node> result = repo.getAllNodes();
        System.out.println(result.get(0));

        Map<Node, List<Node>> tree = new TreeMap<Node, List<Node>>();
        List<Node> opList = new ArrayList<Node>();

        result.stream().filter(n -> n.getParent() != null).forEach(n -> {
            if (!tree.containsKey(n.getParent())) tree.put(n.getParent(), new ArrayList<>());
            tree.get(n.getParent()).add(n);
        });

        tree.forEach((key, value) -> {
            if (key.getId() == id || opList.contains(key)) {
                opList.addAll(value);
            }
        });
        System.out.println(result.get(0));
        return processToTree( opList);
    }
    private Map<Node, List<Node>> processToTree( List<Node> opList1) {
        Map<Node, List<Node>> tree = new TreeMap<Node, List<Node>>();
        opList1.stream().filter(n -> n.getParent() != null).forEachOrdered(n -> {
            if (!tree.containsKey(n.getParent())) tree.put(n.getParent(), new ArrayList<>());
            tree.get(n.getParent()).add(n);
        });
        return tree;
    }

}