// import the necessary libraries
import java.util.*;

// class that represets the algorithm approach
public class BranchAndBound {
    
    // node class (represents a state in the search tree)
    public static class Node {
        public int level;
        public double profit;
        public double bound;
        public double weight;
        public boolean[] selected;
        
        // node constructor
        public Node(int level, double profit, double weight, boolean[] selected) {
            this.level = level;
            this.profit = profit;
            this.weight = weight;
            this.selected = new boolean[selected.length];
            System.arraycopy(selected, 0, this.selected, 0, selected.length);
        }
    }
    
    // solution class
    public static class Solution {
        public ArrayList<Project> selectedProjects;
        public double totalCost;
        public double totalBenefit;
        public double efficiency;
        
        // solution constructor
        public Solution() {
            selectedProjects = new ArrayList<Project>();
            totalCost = 0;
            totalBenefit = 0;
            efficiency = 0;
        }
    }
    
    // comparator classes used for sorting projects
    public static class ProjectComparator implements Comparator<Project> {
        public int compare(Project a, Project b) {
            if (a.getBenefitCostRatio() > b.getBenefitCostRatio()) {
                return -1;
            } else if (a.getBenefitCostRatio() < b.getBenefitCostRatio()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    // comparator class used for sorting nodes in the priority queue
    public static class NodeComparator implements Comparator<Node> {
        public int compare(Node a, Node b) {
            if (a.bound > b.bound) {
                return -1;
            } else if (a.bound < b.bound) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    // use branch and bound to solve the knapsack problem
    public static Solution solveKnapsack(ArrayList<Project> projects, double budget) {

        // sort projects by benefit-cost ratio in descending order
        ArrayList<Project> sortedProjects = new ArrayList<Project>(projects);
        Collections.sort(sortedProjects, new ProjectComparator());
        
        int n = sortedProjects.size();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new NodeComparator());
        
        boolean[] initialSelected = new boolean[n];
        Node root = new Node(-1, 0, 0, initialSelected);
        root.bound = calculateBound(root, sortedProjects, budget);
        
        pq.offer(root);
        
        double maxProfit = 0;
        boolean[] bestSelection = new boolean[n];
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            
            if (current.bound <= maxProfit) {
                continue; // Prune this branch
            }
            
            int nextLevel = current.level + 1;
            if (nextLevel >= n) {
                continue;
            }
            
            // include the next project
            if (current.weight + sortedProjects.get(nextLevel).getCost() <= budget) {
                boolean[] includeSelected = new boolean[current.selected.length];
                System.arraycopy(current.selected, 0, includeSelected, 0, current.selected.length);
                includeSelected[nextLevel] = true;
                
                Node includeNode = new Node(
                    nextLevel,
                    current.profit + sortedProjects.get(nextLevel).getBenefit(),
                    current.weight + sortedProjects.get(nextLevel).getCost(),
                    includeSelected
                );
                
                if (includeNode.profit > maxProfit) {
                    maxProfit = includeNode.profit;
                    bestSelection = new boolean[includeNode.selected.length];
                    System.arraycopy(includeNode.selected, 0, bestSelection, 0, includeNode.selected.length);
                }
                
                includeNode.bound = calculateBound(includeNode, sortedProjects, budget);
                if (includeNode.bound > maxProfit) {
                    pq.offer(includeNode);
                }
            }
            
            // exclude the next project
            boolean[] excludeSelected = new boolean[current.selected.length];
            System.arraycopy(current.selected, 0, excludeSelected, 0, current.selected.length);
            Node excludeNode = new Node(nextLevel, current.profit, current.weight, excludeSelected);
            excludeNode.bound = calculateBound(excludeNode, sortedProjects, budget);
            
            if (excludeNode.bound > maxProfit) {
                pq.offer(excludeNode);
            }
        }
        
        // build the solution
        Solution solution = new Solution();
        for (int i = 0; i < n; i++) {
            if (bestSelection[i]) {
                solution.selectedProjects.add(sortedProjects.get(i));
                solution.totalCost += sortedProjects.get(i).getCost();
                solution.totalBenefit += sortedProjects.get(i).getBenefit();
            }
        }
        
        solution.efficiency = solution.totalCost > 0 ? solution.totalBenefit / solution.totalCost : 0;
        return solution;
    }
    
    // calculate the upper bound
    private static double calculateBound(Node node, ArrayList<Project> projects, double budget) {
        if (node.weight >= budget) {
            return 0;
        }
        
        double bound = node.profit;
        int level = node.level + 1;
        double remainingWeight = budget - node.weight;
        
        // add projects until we reach the budget limit
        while (level < projects.size() && projects.get(level).getCost() <= remainingWeight) {
            bound += projects.get(level).getBenefit();
            remainingWeight -= projects.get(level).getCost();
            level++;
        }
        
        // add a fraction of the next project if it fits partially
        if (level < projects.size()) {
            bound += (remainingWeight / projects.get(level).getCost()) * projects.get(level).getBenefit();
        }
        
        return bound;
    }
}