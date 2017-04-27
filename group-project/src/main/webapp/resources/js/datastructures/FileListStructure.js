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
        if(this.referenceSequence === '---' || this.referenceSequence === undefined || this.referenceSequence === "")
            this.referenceSequence = undefined;
        else
            this.referenceSequence = this.referenceSequence.replace(/\s+/g, '');
//       alert(this.referenceSequence);
        
        if(this.genomicCoverage === '---' || this.genomicCoverage === undefined || this.genomicCoverage === "")
            this.genomicCoverage = undefined;
        else
            this.genomicCoverage = this.genomicCoverage.replace(/\s+/g, '');
        
        if(this.snpdensity === '---' || this.snpdensity === undefined || this.snpdensity === "")
            this.snpdensity = undefined;
        else
            this.snpdensity = this.snpdensity.replace(/\s+/g, '');
        
        if(this.transcriptiomicCoverage === '---' || this.transcriptiomicCoverage === undefined || this.transcriptiomicCoverage === "")
            this.transcriptiomicCoverage = undefined;
        else
            this.transcriptiomicCoverage = this.transcriptiomicCoverage.replace(/\s+/g, '');
        
//        alert(this.genesExpresion);
        if(this.genesExpresion === '---' || this.genesExpresion === undefined || this.genesExpresion === "")
            this.genesExpresion = undefined;
        else
            this.genesExpresion = this.genesExpresion.replace(/\s+/g, '');
       
        if(this.differentialExpression === '---' || this.differentialExpression === undefined || this.differentialExpression === "")
            this.differentialExpression= undefined;
//        console.log('I am in IF');}
        
        else
//            alert('Structure ' + this.differentialExpression);
            this.differentialExpression = this.differentialExpression.replace(/\s+/g, '');
//            console.log('I am in ELSE ' + this.differentialExpression);
        
        
        if(this.annotation === '---' || this.annotation === undefined || this.annotation === "")
            this.annotation = undefined;
        else
            this.annotation = this.annotation.replace(/\s+/g, '');
        
    };
};