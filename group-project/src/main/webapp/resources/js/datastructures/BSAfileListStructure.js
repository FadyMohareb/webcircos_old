function BSAfileListStructure(){
    this.projectName = undefined;
    this.parent1 = undefined;
    this.parent2 = undefined;
    this.pool1 = undefined;
    this.pool2 = undefined;

    
    this.reset = function(newProjectName){
        this.projectName = newProjectName;
        this.parent1 = undefined;
        this.parent2 = undefined;
        this.pool1 = undefined;
        this.pool2 = undefined;
 
    };
    
    this.validateValues = function(){
        if(this.parent1 === '---')
            this.parent1 = undefined;
        if(this.parent2 === '---')
            this.parent2 = undefined;
        if(this.pool1 === '---')
            this.pool1 = undefined;
        if(this.pool2 === '---')
            this.pool2 = undefined;

    };
};