/* global React, Showdown, SecurityContextHolder, RequestContextHolder, UploadModal, Structure */

var converter = new Showdown.converter();

var FilesDropdownSequence = React.createClass({displayName: "FilesDropdownSequence",
    getInitialState: function getInitialState()
    {
        return {activeFileSequence: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {
        this.state.activeFileSequence = newProperties.filesList[0];
        $('#sequenceBtn').children().first().text(this.state.activeFileSequence + ' ');
    },
    renderFilesBlockSequence: function renderBlockSequence(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChangeSequence: function handleFileChangeSequence(event)
            {
                event.preventDefault();
                parent.state.activeFileSequence = event.target.id;
                Structure.referenceSequence = event.target.id;
                $('#sequenceBtn').children().first().text(parent.state.activeFileSequence + ' ');
            }
            ;
            return (React.createElement("li", {onClick: handleFileChangeSequence, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'sequenceBtn'},
                        this.state.activeFileSequence + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu', style: {width: 'inherit'}},
                        this.renderFilesBlockSequence(this.props.filesList, this)));
    }
});

var FilesDropdownGenCoverage = React.createClass({displayName: "FilesDropdownGenCoverage",
    getInitialState: function ()
    {
        return {activeFileGenCoverage: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {
        this.state.activeFileGenCoverage = newProperties.filesList[0];
        $('#genCoverageBtn').children().first().text(this.state.activeFileGenCoverage + ' ');
    },
    renderFilesBlock: function renderBlock(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event)
            {
                event.preventDefault();
                Structure.genomicCoverage = event.target.id;
                parent.setState({activeFileGenCoverage: event.target.id});
                $('#genCoverageBtn').children().first().text(parent.state.activeFileGenCoverage);
            }
            ;
            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'genCoverageBtn'},
                        this.state.activeFileGenCoverage + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                );
    }
});

var FilesDropdownVariants = React.createClass({displayName: "FilesDropdownVariants",
    getInitialState: function ()
    {
        return {activeFileVariants: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {
        this.state.activeFileVariants = newProperties.filesList[0];
        $('#variantsBtn').children().first().text(this.state.activeFileVariants + ' ');
    },
    renderFilesBlockVariants: function renderBlockVariants(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChangeVariants: function handleFileChangeVariants(event)
            {
                event.preventDefault();
                parent.state.activeFileVariants = event.target.id;
                Structure.snpdensity = event.target.id;
                $('#variantsBtn').children().first().text(parent.state.activeFileVariants + ' ');
            }
            ;
            return (React.createElement("li", {onClick: handleFileChangeVariants, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'variantsBtn'},
                        this.state.activeFileVariants + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockVariants(this.props.filesList, this))
                );
    }
});

var FilesDropdownAnnotation = React.createClass({displayName: "FilesDropdownAnnotation",
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('h5', {id: 'annotationBtn'},
                        this.props.filesList[1]));
    }
});

var FilesDropdownTransCoverage = React.createClass({displayName: "FilesDropdownTransCoverage",
    getInitialState: function ()
    {
        return {activeFileTransCoverage: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {
        this.state.activeFileTransCoverage = newProperties.filesList[0];
        $('#transCoverageBtn').children().first().text(this.state.activeFileTransCoverage);
    },
    renderFilesBlock: function renderBlock(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event)
            {
                event.preventDefault();
                parent.setState({activeFileTransCoverage: event.target.id});
                Structure.transcriptiomicCoverage = event.target.id;
                $('#transCoverageBtn').children().first().text(parent.state.activeFileTransCoverage);
            }
            ;
            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'transCoverageBtn'},
                        this.state.activeFileTransCoverage + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                );
    }
});

var FilesDropdownTransCoverage = React.createClass({displayName: "FilesDropdownTransCoverage",
    getInitialState: function () {
        return {activeFileTransCoverage: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileTransCoverage = newProperties.filesList[0];
        $('#transCoverageBtn').children().first().text(this.state.activeFileTransCoverage);
    },
    renderFilesBlock: function renderBlock(filesList, parent) {

        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {

                event.preventDefault();
                parent.setState({activeFileTransCoverage: event.target.id});
                Structure.transcriptiomicCoverage = event.target.id;
                $('#transCoverageBtn').children().first().text(parent.state.activeFileTransCoverage);

            }
            ;

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'transCoverageBtn'},
                        this.state.activeFileTransCoverage + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }


});

var FilesDropdownExpression = React.createClass({displayName: "FilesDropdownExpression",
    getInitialState: function ()
    {
        return {activeFileExpression: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {
        this.state.activeFileExpression = newProperties.filesList[0];
        $('#expressionBtn').children().first().text(this.state.activeFileExpression + ' ');
    },
    renderFilesBlock: function renderBlock(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event)
            {
                event.preventDefault();
                parent.state.activeFileExpression = event.target.id;
                Structure.genesExpresion = event.target.id;
                console.log('Active file: ' + parent.state.activeFileExpression);
                $('#expressionBtn').children().first().text(parent.state.activeFileExpression + ' ');
            }
            ;
            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'expressionBtn'},
                        this.state.activeFileExpression + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                );
    }
});

var FilesDropdownDifExpression = React.createClass({displayName: "FilesDropdownDifExpression",
    getInitialState: function ()
    {
        return {activeFileDifExpression: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {
        this.state.activeFileDifExpression = newProperties.filesList[0];
        $('#difExpressionBtn').children().first().text(this.state.activeFileDifExpression + ' ');
    },
    renderFilesBlock: function renderBlock(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event)
            {
                event.preventDefault();
                parent.state.activeFileDifExpression = event.target.id;
                console.log('Active file: ' + parent.state.activeFileDifExpression);
                Structure.differentialExpression = event.target.id;
                $('#difExpressionBtn').children().first().text(parent.state.activeFileDifExpression + ' ');
            }
            ;
            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'difExpressionBtn'},
                        this.state.activeFileDifExpression + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                );
    }
});

var FilesGeneralPanel = React.createClass({className: "FilesGeneralPanel",
    getInitialState: function getInitialState()
    {
        return {view: {showUploadModal: false}};
    },
//    handleShowUploadModal: function handleShowUploadModal() 
//    {
//        this.setState({view: {showUploadModal: true}});
//    },
//    handleHideUploadModal: function handleHideUploadModal() 
//    {
//        this.setState({view: {showUploadModal: false}});
//        $(".modal-backdrop.in").remove();
//    },
    contentUpdateProject: function (panelType)
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
                var list = [];
                list[0] = '---';

                if (filesList !== "" && filesList !== null)
                {
                    var substring0 = filesList.substring(0, 1);
                    if (substring0 === "[")
                    {
                        filesList = filesList.substring(1, filesList.length - 1);
                        var filesSplited = filesList.split(",");
                        var n = filesSplited.length;

                        for (var i = 0; i < n; i++)
                        {
                            list[i + 1] = filesSplited[i];
                        }
                    } else
                    {
                        var filesSplited = filesList.split("\t");
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

                    if (type === "sequence")
                        return React.render(React.createElement('div', {className: 'container'},
                                React.createElement(FilesDropdownSequence, {filesList: list, fileType: type})),
                                document.getElementById('sequence'));

                    if (type === "variants")
                        return (React.createElement('div', null,
                                React.render(React.createElement('div', {className: 'container'},
                                        React.createElement(FilesDropdownGenCoverage, {filesList: list, fileType: type})),
                                        document.getElementById('genCoverage')),
                                React.render(React.createElement('div', {className: 'container'},
                                        React.createElement(FilesDropdownVariants, {filesList: list, fileType: type})),
                                        document.getElementById('variants'))));

                    if (type === "bedcov")
                        return React.render(React.createElement('div', {className: 'container'},
                                React.createElement(FilesDropdownTransCoverage, {filesList: list, fileType: type})),
                                document.getElementById('transCoverage'));

                    if (type === "expression")
                        return React.render(React.createElement('div', {className: 'container'},
                                React.createElement(FilesDropdownExpression, {filesList: list, fileType: type})),
                                document.getElementById('expression'));

                    if (type === "difExpression")
                        return React.render(React.createElement('div', {className: 'container'},
                                React.createElement(FilesDropdownDifExpression, {filesList: list, fileType: type})),
                                document.getElementById('difExpression'));

                    if (type === "annotation") {
                        Structure.annotation = list[1];
                        return React.render(React.createElement('div', {className: 'container'},
                                React.createElement(FilesDropdownAnnotation, {filesList: list, fileType: type})),
                                document.getElementById('annotation'));
                    }

                }
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }});
    },
    sendData: function () {
        Structure.validateValues();
        var height = $('#bioCircos').height();
        var width = $('#bioCircos').width();
        $("#bioCircos").html("");
        console.log('Structure: ' + JSON.stringify(Structure));

        $.ajax({
            url: "/circos.data",
            dataType: 'json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(Structure),
            success: function (data) {
                console.log(data);
                var ARC_01;
                var HISTOGRAM01;
                var LINE01;
                var LINE02;
                var HEATMAP01;
                var HEATMAP02;
                var BACKGROUND01;
                var BACKGROUND02;

                console.log(data.genomes + ' ' + ARC_01 + ' ' + HISTOGRAM01 + ' ' + LINE01 + ' ' + LINE02 + ' ' + HEATMAP01 + ' ' + HEATMAP02 + ' ' + BACKGROUND01 + ' ' + BACKGROUND02)


                if (data.histo !== null) {
                    HISTOGRAM01 = [data.histo.histId, data.histo.properties, data.histo.histDataPoint];
                } else {
                    HISTOGRAM01 = [];
                }

                if (data.arc !== null) {
                    ARC_01 = [data.arc.indGffid, data.arc.properties, data.arc.gffDataPoint];
                } else {
                    ARC_01 = [];
                }

                if (data.genomicCoverage !== null) {
                    LINE01 = [data.genomicCoverage.lineId, data.genomicCoverage.properties, data.genomicCoverage.linePoints];
                } else {
                    LINE01 = [];
                }

                if (data.transcriptomicCoverage !== null) {

                    LINE02 = [data.transcriptomicCoverage.lineId, data.transcriptomicCoverage.properties, data.transcriptomicCoverage.linePoints];
                } else {
                    LINE02 = [];
                }

                if (data.dEHeatMap !== null) {
                    HEATMAP01 = [data.dEHeatMap.heatMapId, data.dEHeatMap.properties, data.dEHeatMap.heatMapDataPoint];
                } else {
                    HEATMAP01 = [];
                }

                if (data.geneExpressionHeatMap !== null) {
                    HEATMAP02 = [data.geneExpressionHeatMap.heatMapId, data.geneExpressionHeatMap.properties, data.geneExpressionHeatMap.heatMapDataPoint];
                } else {
                    HEATMAP02 = [];
                }
                if (data.backgroundGenoCov !== null) {
                    BACKGROUND01 = [data.backgroundGenoCov.backId, data.backgroundGenoCov.properties];
                } else {
                    BACKGROUND01 = [];
                }
                if (data.backgroundTranCov !== null) {
                    BACKGROUND02 = [data.backgroundTranCov.backId, data.backgroundTranCov.properties];
                } else {
                    BACKGROUND02 = [];
                }
                
                
                console.log('Size: ' + height + ' ' + width);
//                console.log(data.genomes + ' ' + ARC_01 + ' ' + HISTOGRAM01 + ' ' + LINE01 + ' ' + LINE02 + ' ' + HEATMAP01 + ' ' + HEATMAP02 + ' ' + BACKGROUND01 + ' ' + BACKGROUND02)
                renderCircos(height, width, data.genomes, ARC_01, HISTOGRAM01, LINE01, LINE02, HEATMAP01, HEATMAP02, BACKGROUND01, BACKGROUND02);
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
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Annotation: ')),
                        React.createElement('div', {className: 'panel-body', id: 'annotation'},
                                React.createElement(this.contentUpdateProject, {panelType: "annotation"}))),
                React.createElement('div', {className: "panel panel-danger"},
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null, 'Reference sequence: ')),
                        React.createElement('div', {className: 'panel-body', id: 'sequence'},
                                React.createElement(this.contentUpdateProject, {panelType: "sequence"}))),
                React.createElement('div', {className: "panel panel-success"},
                        React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Genomic coverage: ')),
                React.createElement('div', {className: 'panel-body', id: 'genCoverage'},
                        React.createElement(this.contentUpdateProject, {panelType: "variants"}))),
                        React.createElement('div', {className: "panel panel-success"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'SNP density: ')),
                React.createElement('div', {className: 'panel-body', id: 'variants'},
                React.createElement(this.contentUpdateProject, {panelType: "variants"}))),
                React.createElement('div', {className: "panel panel-info"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Transcriptiomic coverage: ')),
                React.createElement('div', {className: 'panel-body', id: 'transCoverage'},
                        React.createElement(this.contentUpdateProject, {panelType: "bedcov"}))),
                React.createElement('div', {className: "panel panel-warning"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Genes expression: ')),
                React.createElement('div', {className: 'panel-body', id: 'expression'},
                        React.createElement(this.contentUpdateProject, {panelType: "expression"}))),
                React.createElement('div', {className: "panel panel-default"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null,'Differential expression: ')),
                React.createElement('div', {className: 'panel-body', id: 'difExpression'},
                        React.createElement(this.contentUpdateProject, {panelType: "difExpression"}))),
                React.createElement('br'),
                React.createElement('button', {className: 'btn btn-primary', style: {'text-align': 'center'}, onClick: this.sendData},
                        React.createElement('strong', null, 'Display circos')))
                );
    }
});

var renderFilesGeneralPanel = function ()
{
    React.render(React.createElement(FilesGeneralPanel), document.getElementById('filesContainer'));
};
