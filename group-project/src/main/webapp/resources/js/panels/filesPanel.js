var converter = new Showdown.converter();

var FilesPanel = React.createClass({displayName: "FilesPanel",
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
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Files  ",
                        React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowUploadModal},
                                React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'}))),
                React.createElement('div', {className: "panel-body", id: 'filesTabsContainer'},
                        React.createElement(FilesTabsComponent, {projectName: this.props.projectName})),
                        
                this.state.view.showUploadModal ? React.createElement(UploadModal, {handleHideUploadModal: this.handleHideUploadModal, projectName: this.props.projectName}) : null)
                );
    }
});

var renderFilesPanel = function () {
    React.render(
            React.createElement(FilesPanel),
            document.getElementById("filesContainer")
            );
};

