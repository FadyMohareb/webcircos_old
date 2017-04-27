function BSAfileListStructure() {
    this.projectName = undefined;
    this.sequence = undefined;
    this.parent1 = undefined;
    this.parent2 = undefined;
    this.pool1 = undefined;
    this.pool2 = undefined;


    this.reset = function (newProjectName) {
        this.projectName = newProjectName;
        this.sequence = undefined;
        this.parent1 = undefined;
        this.parent2 = undefined;
        this.pool1 = undefined;
        this.pool2 = undefined;

    };

    this.validateValues = function () {
        if (this.projectName === '---' || this.projectName === undefined || this.projectName === "")
            this.projectName = undefined;
        else
            this.projectName = this.projectName.replace(/\s+/g, '');

        if (this.sequence === '---' || this.sequence === undefined || this.sequence === "")
            this.sequence = undefined;
        else
            this.sequence = this.sequence.replace(/\s+/g, '');

        if (this.parent1 === '---' || this.parent1 === undefined || this.parent1 === "")
            this.parent1 = undefined;
        else
            this.parent1 = this.parent1.replace(/\s+/g, '');

        if (this.parent2 === '---' || this.parent2 === undefined || this.parent2 === "")
            this.parent2 = undefined;
        else
            this.parent2 = this.parent2.replace(/\s+/g, '');

        if (this.pool1 === '---' || this.pool1 === undefined || this.pool1 === "")
            this.pool1 = undefined;
        else
            this.pool1 = this.pool1.replace(/\s+/g, '');

        if (this.pool2 === '---' || this.pool2 === undefined || this.pool2 === "")
            this.pool2 = undefined;
        else
            this.pool2 = this.pool2.replace(/\s+/g, '');

    };
}
;