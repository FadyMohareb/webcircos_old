function ImportFileStructure(){
    this.fileName = undefined;
    this.oldProjectName = undefined;
    this.newProjectName = undefined;
    
    this.reset = function(newProjectName){
        this.fileName = newProjectName;
        this.oldProjectName = undefined;
        this.newProjectName = undefined;
    };
    
    this.validateValues = function(){
        if(this.fileName === '---')
            this.fileName = undefined;
        if(this.oldProjectName === '---')
            this.oldProjectName = undefined;
        if(this.newProjectName === '---')
            this.newProjectName = undefined;
    };
};
