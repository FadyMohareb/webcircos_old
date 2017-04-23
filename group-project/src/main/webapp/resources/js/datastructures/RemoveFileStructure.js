function RemoveFileStructure(){
    this.fileName = undefined;
    this.projectName = undefined;
    
    this.reset = function(newProjectName){
        this.fileName = newProjectName;
        this.projectName = undefined;
    };
    
    this.validateValues = function(){
        if(this.fileName === '---')
            this.fileName = undefined;
        if(this.projectName === '---')
            this.projectName = undefined;
    };
};
