function RemoveFileStructure() {
    this.fileName = undefined;
    this.projectName = undefined;

    this.reset = function (newProjectName) {
        this.fileName = newProjectName;
        this.projectName = undefined;
    };

    this.validateValues = function () {
        if (this.fileName === '---' || this.fileName === undefined || this.fileName === "")
            this.fileName = undefined;
        else
            this.fileName = this.fileName.replace(/\s+/g, '');
        
        if (this.projectName === '---' || this.projectName === undefined || this.projectName === "")
            this.projectName = undefined;
        else
            this.projectName = this.projectName.replace(/\s+/g, '');
    };
}
;
