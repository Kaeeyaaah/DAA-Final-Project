// class that represents a single project with attributes
public class Project {
    private String name;
    private double cost;
    private double benefit;
    private String category;
    private String description;
    private double benefitCostRatio;
    
    public Project(String name, double cost, double benefit, String category, String description) {
        this.name = name;
        this.cost = cost;
        this.benefit = benefit;
        this.category = category;
        this.description = description;
        this.benefitCostRatio = cost > 0 ? benefit / cost : 0;
    }
    
    // get the project name
    public String getName() { 
        return name; 
    }
    
    // set the name of the project
    public void setName(String name) { 
        this.name = name; 
    }
    
    // get the project cost
    public double getCost() { 
        return cost; 
    }
    
    // set the project cost and update the benefit-cost ratio
    public void setCost(double cost) { 
        this.cost = cost; 
        this.benefitCostRatio = cost > 0 ? benefit / cost : 0;
    }
    
    // get the project benefit
    public double getBenefit() { 
        return benefit; 
    }
    
    // set and update the benefit-cost ratio
    public void setBenefit(double benefit) { 
        this.benefit = benefit; 
        this.benefitCostRatio = cost > 0 ? benefit / cost : 0;
    }
    
    // get the project category
    public String getCategory() { 
        return category; 
    }
    
    // set the project category
    public void setCategory(String category) { 
        this.category = category; 
    }
    
    // get and set the project description
    public String getDescription() { 
        return description; 
    }
    
    // set the project description
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    // get the benefit-cost ratio
    public double getBenefitCostRatio() { 
        return benefitCostRatio; 
    }
    
    // set the benefit-cost ratio to string
    public String toString() {
        return String.format("Project: %s | Cost: ₱%.2f | Benefit: %.2f | Ratio: %.3f | Category: %s", 
                           name, cost, benefit, benefitCostRatio, category);
    }
}