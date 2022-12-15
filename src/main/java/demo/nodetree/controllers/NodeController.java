package demo.nodetree.controllers;

import demo.nodetree.domain.entites.Node;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import demo.nodetree.domain.repos.NodeRepo;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("/node")
@AllArgsConstructor
public class NodeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NodeRepo repo;

    @GetMapping(value = "/all/children/{id}", produces = "application/json")
    @ResponseBody
    @Description("Get All Children Under Given Node (Only Next Children Set)")
    public Map<Node, List<Node>> getChildNodes(@PathVariable long id) {
        Date startTime = new Date();
        List<Node> result = repo.getAllNodes();

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

        logger.info("size of child list " + opList.size());
        Date endTime = new Date();
        logger.info(" Execution time " + (startTime.getTime() - endTime.getTime()) / 1000);
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

    private void processToJson(long id, Map<Node, List<Node>> tree, List<Node> opList) {
        int count = 1;
        for (Map.Entry<Node, List<Node>> elem : tree.entrySet()) {

            if (elem.getKey().getId() == id || opList.contains(elem.getKey())) {
                elem.getValue().forEach(node -> {
                    System.out.println(node + "  ::::::::::::::::::::::::::::::::::::::::::");
                    System.out.println(elem.getKey() + " %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                });
                System.out.println("---------------------------------------------------\n");
                opList.addAll(elem.getValue());
            }
            count++;
        }
        System.out.println(opList + "=========================================");

    }


    @Transactional
    @PutMapping(value = "/update/{nodeId}/parent/{parentId}", produces = "application/json")
    @ResponseBody
    public Node replaceParent(@PathVariable("nodeId") long nodeId, @PathVariable("parentId") long parentId) {
        Node currNode = repo.findById(nodeId).get();
        Node parNode = repo.findById(parentId).get();
        currNode.setParent(parNode);
        repo.save(currNode);
        return currNode;
    }

    @Transactional
    @PostMapping(value = "/save", produces = "application/json")
    @ResponseBody
    public Node saveAll(@RequestBody Node node) {
        return repo.save(node);
    }
}