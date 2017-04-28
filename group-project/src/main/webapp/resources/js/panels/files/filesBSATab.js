var FilesDropdownSequenceBSA = React.createClass({displayName: "FilesDropdownSequenceBSA",
    getInitialState: function () {
        return {activeFileSequenceBSA: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileParent1 = newProperties.filesList[0];
        $('#sequenceBsaBtn').children().first().text(this.state.activeFileSequenceBSA + ' ');
    },
    renderFilesBlockSequenceBSA: function renderBlockSequenceBSA(filesList, parent) {

        return filesList.map(function (fileName)
        {

            handleFileChangeSequenceBSA: function handleFileChangeSequenceBSA(event) {

                event.preventDefault();
                parent.state.activeFileSequenceBSA = event.target.id;
                BSAstructure.sequence = event.target.id;
                console.log('SequenceBSA: ' + BSAstructure.sequence);
                $('#sequenceBsaBtn').children().first().text(parent.state.activeFileSequenceBSA + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChangeSequenceBSA, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'sequenceBsaBtn'},
                        this.state.activeFileSequenceBSA + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockSequenceBSA(this.props.filesList, this))
                )
    }



});

var FilesDropdownParent1 = React.createClass({displayName: "FilesDropdownParent1",
    getInitialState: function () {
        return {activeFileParent1: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileParent1 = newProperties.filesList[0];
        $('#parent1Btn').children().first().text(this.state.activeFileParent1 + ' ');
    },
    renderFilesBlockParent1: function renderBlockParent1(filesList, parent) {

        return filesList.map(function (fileName)
        {

            handleFileChangeParent1: function handleFileChangeParent1(event) {

                event.preventDefault();
                var tempSolution = event.target.id.replace(/\s+/g, '');
                parent.state.activeFileParent1 = tempSolution 
                BSAstructure.parent1 = tempSolution;
                console.log('Parent 1: ' + BSAstructure.parent1);
                $('#parent1Btn').children().first().text(parent.state.activeFileParent1 + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChangeParent1, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'parent1Btn'},
                        this.state.activeFileParent1 + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockParent1(this.props.filesList, this))
                )
    }



});

var FilesDropdownParent2 = React.createClass({displayName: "FilesDropdownParent2",
    getInitialState: function () {
        return {activeFileParent2: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileParent2 = newProperties.filesList[0];
        $('#parent2Btn').children().first().text(this.state.activeFileParent2 + ' ');
    },
    renderFilesBlockParent2: function renderBlockParent2(filesList, parent) {

        return filesList.map(function (fileName)
        {

            handleFileChangeParent2: function handleFileChangeParent2(event) {

                event.preventDefault();
                var tempSolution = event.target.id.replace(/\s+/g, '');
                parent.state.activeFileParent2 = tempSolution;
                BSAstructure.parent2 = tempSolution;
                console.log('Parent 2: ' + BSAstructure.parent2);
                $('#parent2Btn').children().first().text(parent.state.activeFileParent2 + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChangeParent2, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'parent2Btn'},
                        this.state.activeFileParent2 + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockParent2(this.props.filesList, this))
                )
    }



});

var FilesDropdownPool1 = React.createClass({displayName: "FilesDropdownPool1",
    getInitialState: function () {
        return {activeFilePool1: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFilePool1 = newProperties.filesList[0];
        $('#pool1Btn').children().first().text(this.state.activeFilePool1 + ' ');
    },
    renderFilesBlockPool1: function renderBlockPool1(filesList, parent) {

        return filesList.map(function (fileName)
        {

            handleFileChangePool1: function handleFileChangePool1(event) {

                event.preventDefault();
                var tempSolution = event.target.id.replace(/\s+/g, '');
                parent.state.activeFilePool1 = tempSolution;
                BSAstructure.pool1 = tempSolution;
                console.log('Pool 1: ' + BSAstructure.pool1);
                $('#pool1Btn').children().first().text(parent.state.activeFilePool1 + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChangePool1, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'pool1Btn'},
                        this.state.activeFilePool1 + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockPool1(this.props.filesList, this))
                )
    }

});

var FilesDropdownPool2 = React.createClass({displayName: "FilesDropdownPool2",
    getInitialState: function () {
        return {activeFilePool2: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFilePool2 = newProperties.filesList[0];
        $('#pool2Btn').children().first().text(this.state.activeFilePool2 + ' ');
    },
    renderFilesBlockPool2: function renderBlockPool2(filesList, parent) {

        return filesList.map(function (fileName)
        {

            handleFileChangePool2: function handleFileChangePool2(event) {

                event.preventDefault();
                var tempSolution = event.target.id.replace(/\s+/g, '');
                parent.state.activeFilePool2 = tempSolution;
                BSAstructure.pool2 = tempSolution;
                console.log('Pool 2: ' + BSAstructure.pool2);
                $('#pool2Btn').children().first().text(parent.state.activeFilePool2 + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChangePool2, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'pool2Btn'},
                        this.state.activeFilePool2 + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockPool2(this.props.filesList, this))
                )
    }



});

var FilesBSATab = React.createClass({className: "FilesBSATab",
    contentUpdate: function (panelType)
    {
        var projectName = this.props.projectName;
        var fd = new FormData();
        var type = panelType.panelType;
        fd.append('panelType', type);
        fd.append('projectName', projectName);
        $.ajax({
            url: "/refresh",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: function (data)
            {
                var filesList = data.errors;
                if (filesList !== "" && filesList !== null)
                {
                    var substring0 = filesList.substring(0, 1);
                    if (substring0 === "[")
                    {
                        filesList = filesList.substring(1, filesList.length - 1);
                        var filesSplited = filesList.split(",");
                        var n = filesSplited.length;
                        var list = [];
                        list[0] = '---';
                        for (var i = 0; i < n; i++)
                        {
                            list[i + 1] = filesSplited[i];
                        }
                    } else
                    {
                        var filesSplited = filesList.split("\t");
                        var list = [];
                        list[0] = '---';
                        for (var i = 0; i < filesSplited.length; i++)
                        {
                            if (filesSplited[i] !== "")
                            {
                                var filesSplited2 = filesSplited[i].split("/");
                                var fileName = filesSplited2[filesSplited2.length - 1];
                                list[i + 1] = fileName;
                            }
                        }


                    }

                    if (type === "variants") {
                        React.render(React.createElement(FilesDropdownParent1, {filesList: list, fileType: type}),
                                document.getElementById('parent1'));
                        React.render(React.createElement(FilesDropdownParent2, {filesList: list, fileType: type}),
                                document.getElementById('parent2'));
                        React.render(React.createElement(FilesDropdownPool1, {filesList: list, fileType: type}),
                                document.getElementById('pool1'));
                        React.render(React.createElement(FilesDropdownPool2, {filesList: list, fileType: type}),
                                document.getElementById('pool2'));
                    } else if (type === "sequence") {
                        React.render(React.createElement(FilesDropdownSequenceBSA, {filesList: list, fileType: type}),
                                document.getElementById('sequenceBSA'));
                    }

                }
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }});
    },
    sendData: function () {
        BSAstructure.validateValues();
        $("#bsaCircos").html("");

        $.ajax({
            url: "/bsa.data",
            dataType: 'json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(BSAstructure),
            success: function (data) {
                var LINK01;
                if (data.link !== null) {
                    LINK01 = [data.link.linkId, data.link.properties, data.link.linkData];
                } else {
                    LINK01 = [];
                }
                
                renderBSA(data.genomes, LINK01);
            },
            error: function () {
                alert("Wrong data");
                console.error(status, err.toString());
            }

        });

    },
    render: function () {

        return (React.createElement('div', {className: 'container', style: {width: "inherit"}},
                React.createElement('div', {className: "panel panel-danger"},
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null, 'Reference sequence: ')),
                        React.createElement('div', {className: 'panel-body', id: 'sequenceBSA'},
                        React.createElement(this.contentUpdate, {panelType: "sequence"}))),
                React.createElement('div', {className: "panel panel-success"},
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Parent 1 (WT): ')),
                        React.createElement('div', {className: 'panel-body', id: 'parent1'},
                        React.createElement(this.contentUpdate, {panelType: "variants"}))),
                React.createElement('div', {className: "panel panel-info"},
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Parent 2 (MT): ')),
                        React.createElement('div', {className: 'panel-body', id: 'parent2'},
                        React.createElement(this.contentUpdate, {panelType: "variants"}))),
                React.createElement('div', {className: "panel panel-warning"},
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Pool 1 (WT): ')),
                        React.createElement('div', {className: 'panel-body', id: 'pool1'},
                        React.createElement(this.contentUpdate, {panelType: "variants"}))),
                React.createElement('div', {className: "panel panel-default"},
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Pool 2 (MT): ')),
                        React.createElement('div', {className: 'panel-body', id: 'pool2'},
                        React.createElement(this.contentUpdate, {panelType: "variants"}))),
                React.createElement('br'),
                React.createElement('button', {className: 'btn btn-primary', onClick: this.sendData}, React.createElement('strong', null, 'Display BSA circos'))));
    }
})
