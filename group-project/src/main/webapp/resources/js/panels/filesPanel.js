/* global React, Showdown, SecurityContextHolder, RequestContextHolder, UploadModal */

var converter = new Showdown.converter();
var ProperFilesListRender = React.createClass({displayName: "ProperFilesListRender",
    render: function() {
      return (React.createElement("div", null,
            this.props.list.map(function(listValue)
            {
                return (React.createElement("h5",null," "+listValue+"\n"));
                //checkbox instead of line
//                return (React.createElement("input", { type: 'checkbox' }, " "+listValue+"\n"));
            })
        )
      );
    }
  });
  
var FilesPanel = React.createClass({className: "filesPanel",

    getInitialState: function getInitialState() {
        return {view: {showUploadModal: false} };
    },
    handleShowUploadModal: function handleShowUploadModal() {
        this.setState({view: {showUploadModal: true}});
    },
    handleHideUploadModal: function handleHideUploadModal() {
        this.setState({view: {showUploadModal: false}});
        $(".modal-backdrop.in").remove();
    },
    contentUpdateProject: function(panelType)
    {
        var projectName = "blablabla";
        var fd = new FormData();
        var type = panelType.panelType;
        fd.append('panelType', type);
        fd.append('projectName', projectName);
        $.ajax({
            url: "/refresh/logged",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: function (data) 
            {
                var filesList = data.errors.split("\t");
                if (filesList[0].length !== 0)
                {
                    var n = filesList.length;
                    var list = [];
                    for (var i = 0; i < n; i++)
                    {
                        var parts= filesList[i].split("/");
                        if (parts[0].length!==0)
                        {
                            var fileName = parts[parts.length-1];
                            list[i]=fileName;
                        }
                    }
                    return React.render(React.createElement(ProperFilesListRender, {list: list}), document.getElementById(type));
                }
//                else
//                    return React.render(React.createElement("h6",null,"No files had been uploaded"), document.getElementById(type));
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }});
    },
    contentUpdate: function(panelType)
    {
        var type = panelType.panelType;
        $.ajax({
            url: "/refresh/notLogged",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(type),
            success: function (data) 
            {
                var filesList = data.errors.split("\t");
                if (filesList[0].length !== 0)
                {
                    var n = filesList.length;
                    var list = [];
                    for (var i = 0; i < n; i++)
                    {
                        var parts= filesList[i].split("/");
                        if (parts[0].length!==0)
                        {
                            var fileName = parts[parts.length-1];
                            list[i]=fileName;
                        }
                    }
                    return React.render(React.createElement(ProperFilesListRender, {list: list}), document.getElementById(type));
                }
//                else
//                    return React.render(React.createElement("h6",null,"No files had been uploaded"), document.getElementById(type));
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }});
    },
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Files  ",
                        React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowUploadModal},
                                React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'}))),
                React.createElement('div', {className: "panel-body"},
                        React.createElement('div', {className: "panel panel-success"},
                                React.createElement('div', {className: "panel-heading"}, "Sequence"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'sequence'},
                                            React.createElement(this.contentUpdateProject, {panelType: "sequence"})))),
                        React.createElement('div', {className: "panel panel-info"},
                                React.createElement('div', {className: "panel-heading"}, "Coverage"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'coverage'},
                                            React.createElement(this.contentUpdateProject, {panelType: "coverage"})))),
                        React.createElement('div', {className: "panel panel-warning"},
                                React.createElement('div', {className: "panel-heading"}, "Variants"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'variants'},
                                            React.createElement(this.contentUpdateProject, {panelType: "variants"})))),
                        React.createElement('div', {className: "panel panel-danger"},
                                React.createElement('div', {className: "panel-heading"}, "Annotation"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'annotation'},
                                            React.createElement(this.contentUpdateProject, {panelType: "annotation"})))),
                        React.createElement('div', {className: "panel panel-success"},
                                React.createElement('div', {className: "panel-heading"}, "Expression"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'expression'},
                                            React.createElement(this.contentUpdateProject, {panelType: "expression"})))),
                        React.createElement('div', {className: "panel panel-info"},
                                React.createElement('div', {className: "panel-heading"}, "Diferential expression"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'difExpression'},
                                            React.createElement(this.contentUpdateProject, {panelType: "difExpression"}))))
                        ),
                this.state.view.showUploadModal ? React.createElement(UploadModal, {handleHideUploadModal: this.handleHideUploadModal}) : null
                ));
    }
});

var renderFilesPanel = function () {
    React.render(React.createElement(FilesPanel), document.getElementById('filesContainer'));
};

