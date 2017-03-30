/* global React, Showdown, SecurityContextHolder, RequestContextHolder */

var converter = new Showdown.converter();

var FilesPanel = React.createClass({className: "filesPanel",
    getInitialState: function getInitialState() {
        return {view: {showUploadModal: false}};
    },
    handleShowUploadModal: function handleShowUploadModal() {
        this.setState({view: {showUploadModal: true}});
    },
    handleHideUploadModal: function handleHideUploadModal() {
        this.setState({view: {showUploadModal: false}});
        $(".modal-backdrop.in").remove();
    },
    contentUpdate: function(panelType)
    {
        var flag=false;
        var type = panelType.panelType;
        var filelist;
        console.log(panelType);
        $.ajax({
            url: "/refresh",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(type),
            success: function (data) {
                console.log(data.errors);
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }
//            ,
//            callback: function(data){
//                fileList=data.errors;
//                callback(fileList);
//            }
        });
        console.log(filelist);
        return (React.createElement('input', { type: 'checkbox' }, " "+type));
            
    },
    // Need methods returning list of files in project
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Files  ",
                        React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowUploadModal},
                                React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'}))),
                React.createElement('div', {className: "panel-body"},
                        React.createElement('div', {className: "panel panel-success"},
                                React.createElement('div', {className: "panel-heading"}, "Sequence"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement(this.contentUpdate, {panelType: "sequence"}))),
                        React.createElement('div', {className: "panel panel-info"},
                                React.createElement('div', {className: "panel-heading"}, "Alignment"),
                                    React.createElement('div', {className: "panel-body"}, 
                                        React.createElement(this.contentUpdate, {panelType: "alignment"}))),
                        React.createElement('div', {className: "panel panel-warning"},
                                React.createElement('div', {className: "panel-heading"}, "Variants"),
                                    React.createElement('div', {className: "panel-body"}, 
                                        React.createElement(this.contentUpdate, {panelType: "variants"}))),
                        React.createElement('div', {className: "panel panel-danger"},
                                React.createElement('div', {className: "panel-heading"}, "Expression"),
                                    React.createElement('div', {className: "panel-body"},
                                        React.createElement(this.contentUpdate, {panelType: "expression"})))
                        ),
                this.state.view.showUploadModal ? React.createElement(UploadModal, {handleHideUploadModal: this.handleHideUploadModal}) : null
                ));
    }
})

var renderFilesPanel = function () {
    React.render(React.createElement(FilesPanel), document.getElementById('filesContainer'));
};

