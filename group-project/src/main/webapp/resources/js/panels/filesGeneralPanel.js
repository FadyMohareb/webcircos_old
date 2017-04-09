/* global React, Showdown, SecurityContextHolder, RequestContextHolder, UploadModal */

var converter = new Showdown.converter();

var ProperFilesListRender = React.createClass({displayName: "ProperFilesListRender",
    
    render: function() {
      return (React.createElement("div", null,
            this.props.list.map(function(listValue)
            {console.log('FILES NAMES: ' + listValue)
                return (React.createElement("h5",null," "+listValue+"\n"));
                //checkbox instead of line
//                return (React.createElement("input", { type: 'checkbox' }, " "+listValue+"\n"));
            })
        )
      );
    }
  });
  
var FilesGeneralPanel = React.createClass({className: "FilesGeneralPanel",

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
            success: function(data) 
            {
                var filesList = data.errors;
                if (filesList !== "")
                {
                    var substring0=filesList.substring(0,1);
                    if (substring0==="[")
                    {
                        filesList = filesList.substring(1,filesList.length-1);
                        var filesSplited = filesList.split(",");
                        var n = filesSplited.length;
                        var list = [];
                        for (var i = 0; i < n; i++)
                        {
                            list[i]=filesSplited[i];
                        }
                    }
                    else
                    {
                        var filesSplited = filesList.split("\t");
                        var list = [];
                        for (var i = 0; i < filesSplited.length; i++)
                        {
                            if (filesSplited[i]!=="")
                            {
                                var filesSplited2 = filesSplited[i].split("/");
                                var fileName = filesSplited2[filesSplited2.length-1];
                                list[i]=fileName;
                            }
                        }
                        
                        
                    }
                    
                    if (list.length >0)
                        return React.render(React.createElement(ProperFilesListRender, {list: list}), document.getElementById(type));
                }
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }});
    },
    render: function () {
        return (React.createElement('div', {className: "container"},
        
                        React.createElement('div', {className: "panel panel-success"},
                                React.createElement('div', {className: "panel-heading"}, "Sequence"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'sequence'},
                                            React.createElement(this.contentUpdateProject, {panelType: "sequence"})))),
                        React.createElement('div', {className: "panel panel-info"},
                                React.createElement('div', {className: "panel-heading"}, "Alignment"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement('div', {className: 'container', id: 'alignment'},
                                            React.createElement(this.contentUpdateProject, {panelType: "alignment"})))),
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
                                            React.createElement(this.contentUpdateProject, {panelType: "difExpression"})))))
                );
    }
});

var renderFilesGeneralPanel = function () {
    React.render(React.createElement(FilesGeneralPanel), document.getElementById('filesContainer'));
};

