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
@RequestMapping("/get/all")
public class NodeControllerForGetAll {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NodeRepo repo;

    @GetMapping("/")
    public void hello() {
        System.out.println("Hi ");
    }


    @GetMapping(value = "/children/{id}", produces = "application/json")
    @ResponseBody
    public Map<Node, List<Node>> getChildNodes(@PathVariable long id) {
        Date startTime = new Date();
        List<Node> result = repo.getAllNodes();
        System.out.println();
        Map<Node, List<Node>> tree = new TreeMap<Node, List<Node>>();
        List<Node> opList = new ArrayList<Node>();
        Node node = new Node();
        for (Node n : result) {
            System.out.println(n + "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            if (n.getParent() == null) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
                node.setParent(n);
                continue;
            }
            if (n.getChilds() == null) {
                System.out.println("WWWWWWWWWWWWWWWWWWWW");
            }
            if (n.getChilds() != null) {
//                node.setChilds(n.getChilds());
                System.out.println(n.getChilds() +" ??????????????????????????????????????????????????????????????\n");
            }

            if (!tree.containsKey(n.getParent())) tree.put(n.getParent(), new ArrayList<Node>());
            tree.get(n.getParent()).add(n);

        }
        System.out.println(tree + "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");

        for (Map.Entry<Node, List<Node>> elem : tree.entrySet()) {
            if (elem.getKey().getId() == id || opList.contains(elem.getKey())) {
                opList.addAll(elem.getValue());
            }

        }
        logger.info("size of child list " + opList.size());
        Date endTime = new Date();
        logger.info(" Execution time " + (startTime.getTime() - endTime.getTime()) / 1000);
        return processToTree(id, opList);
    }

    private Map<Node, List<Node>> processToTree(long id, List<Node> opList1) {
        Map<Node, List<Node>> tree = new TreeMap<Node, List<Node>>();
        List<Node> opList = new ArrayList<Node>();
        Node node = new Node();
        for (Node n : opList1) {
            System.out.println(n + "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            if (n.getParent() == null) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
                node.setParent(n);
                continue;
            }
            if (n.getChilds() == null) {
                System.out.println("WWWWWWWWWWWWWWWWWWWW");
            }
            if (n.getChilds() != null) {
//                node.setChilds(n.getChilds());
                System.out.println(n.getChilds() +" ??????????????????????????????????????????????????????????????\n");
            }

            if (!tree.containsKey(n.getParent())) tree.put(n.getParent(), new ArrayList<Node>());
            tree.get(n.getParent()).add(n);

        }
        return tree;
    }

    private void processToJson(long id, Map<Node, List<Node>> tree, List<Node> opList) {
        int count = 1;
        for (Map.Entry<Node, List<Node>> elem : tree.entrySet()) {

            if (elem.getKey().getId() == id || opList.contains(elem.getKey())) {
                for (Node node : elem.getValue()) {
                    System.out.println(node + "  ::::::::::::::::::::::::::::::::::::::::::");
                    System.out.println(elem.getKey() + " %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
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