function FileListStructure(){
    this.projectName = undefined;
    this.referenceSequence = undefined;
    this.genomicCoverage = undefined;
    this.SNPDensity = undefined;
    this.transcriptiomicCoverage = undefined;
    this.genesExpresion = undefined;
    this.differentialExpression = undefined;
    this.annotation = undefined;
    
    this.reset = function(newProjectName){
        this.projectName = newProjectName;
        this.referenceSequence = undefined;
        this.genomicCoverage = undefined;
        this.SNPDensity = undefined;
        this.transcriptiomicCoverage = undefined;
        this.genesExpresion = undefined;
        this.differentialExpression = undefined;
        this.annotation = undefined;
    };
    
    this.validateValues = function(){
        if(this.referenceSequence === '---')
            this.referenceSequence = undefined;
        //TODO: Others
    };
};