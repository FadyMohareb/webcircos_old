function ImportFileStructure() {
    this.fileName = undefined;
    this.oldProjectName = undefined;
    this.newProjectName = undefined;

    this.reset = function (newProjectName) {
        this.fileName = newProjectName;
        this.oldProjectName = undefined;
        this.newProjectName = undefined;
    };

    this.validateValues = function () {
        if (this.fileName === '---' || this.fileName === undefined || this.fileName === "")
            this.fileName = undefined;
        else
            this.fileName = this.fileName.replace(/\s+/g, '');

        if (this.oldProjectName === '---' || this.oldProjectName === undefined || this.oldProjectName === "")
            this.oldProjectName = undefined;
        else
            this.oldProjectName = this.oldProjectName.replace(/\s+/g, '');

        if (this.newProjectName === '---' || this.newProjectName === undefined || this.newProjectName === "")
            this.newProjectName = undefined;
        else
            this.newProjectName = this.newProjectName.replace(/\s+/g, '');
    };
}
;
