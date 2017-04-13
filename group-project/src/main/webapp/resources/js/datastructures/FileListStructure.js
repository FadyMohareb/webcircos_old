function FileListStructure(){
    this.projectName = undefined;
    this.referenceSequence = undefined;
    this.genomicCoverage = undefined;
    this.snpdensity = undefined;
    this.transcriptiomicCoverage = undefined;
    this.genesExpresion = undefined;
    this.differentialExpression = undefined;
    this.annotation = undefined;
    
    this.reset = function(newProjectName){
        this.projectName = newProjectName;
        this.referenceSequence = undefined;
        this.genomicCoverage = undefined;
        this.snpdensity = undefined;
        this.transcriptiomicCoverage = undefined;
        this.genesExpresion = undefined;
        this.differentialExpression = undefined;
        this.annotation = undefined;
    };
    
    this.validateValues = function(){
        if(this.referenceSequence === '---')
            this.referenceSequence = undefined;
        if(this.genomicCoverage === '---')
            this.genomicCoverage = undefined;
        if(this.snpdensity === '---')
            this.snpdensity = undefined;
        if(this.transcriptiomicCoverage === '---')
            this.transcriptiomicCoverage = undefined;
        if(this.genesExpresion === '---')
            this.genesExpresion = undefined;
        if(this.differentialExpression === '---')
            this.differentialExpression = undefined;
        if(this.annotation === '---')
            this.annotation = undefined;
        //TODO: Others
    };
};